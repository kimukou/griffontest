package com.solab.alarms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** This is the default cache, which uses a map to store the last date a message was sent for a given
 * channel/msg/source cobination.
 * 
 * @author Enrique Zamudio
 */
public class DefaultAlarmCache implements AlarmCache {

	private Map<String, Long> lastSends = new ConcurrentHashMap<String, Long>();
	private int defint = 120000;

	/** Sets the default resend interval, for storing alarms unrelated to a specific channel,
	 * in milliseconds. Default is 2 minutes. */
	public void setDefaultInterval(int value) {
		defint = value;
	}
	public int getDefaultInterval() {
		return defint;
	}

	@Override
	public void store(AlarmChannel channel, String source, String message) {
		if (channel == null || channel.getMinResendInterval() > 0) {
			String k = channel == null ? String.format("ALL:%s:%s", source == null ? "" : source,
				AlarmSender.hash(message)) : String.format("chan%s:%s:%s", channel.hashCode(), source == null ? "" : source,
				AlarmSender.hash(message));
			lastSends.put(k, System.currentTimeMillis());
		}
	}

	@Override
	public boolean shouldResend(AlarmChannel channel, String source,
			String message) {
		boolean resend = true;
		if (channel == null || channel.getMinResendInterval() > 0) {
			String k = channel == null ? String.format("ALL:%s:%s", source == null ? "" : source,
				AlarmSender.hash(message)) : String.format("chan%s:%s:%s", channel.hashCode(), source == null ? "" : source,
				AlarmSender.hash(message));
			Long then = lastSends.get(k);
			//Check the last time this same message was sent
			if (then != null) {
				//If it's too recent, don't send it through this channel
				resend = System.currentTimeMillis()-then >= (channel  == null ? defint : channel.getMinResendInterval());
			}
		}
		return resend;
	}

	@Override
	public String toString() {
		return String.format("Default(%d keys)", lastSends.size());
	}

	@Override
	public void shutdown() {
	}

}
