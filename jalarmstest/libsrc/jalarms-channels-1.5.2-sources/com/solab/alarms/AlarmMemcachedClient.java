package com.solab.alarms;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.OperationTimeoutException;


/** An alarm cache that uses memcached to store the data it needs to know if an alarm message should be
 * resent. This is useful in environments where you have several applications which can be using similar
 * alarm channels, since the DefaultAlarmCache is internal to the app and events in two or more apps
 * will cause an alarm to be sent from each app.
 * 
 * @author Enrique Zamudio
 */
public class AlarmMemcachedClient implements AlarmCache {

	private final Logger log = LoggerFactory.getLogger(getClass());
	private MemcachedClient mc;
	private InetSocketAddress[] servers;
	private int defint = 120;

	/** Sets the default resend interval, for storing alarms unrelated to a specific channel,
	 * in seconds. Default is 2 minutes. */
	public void setDefaultInterval(int value) {
		defint = value;
	}
	public int getDefaultInterval() {
		return defint;
	}

	/** Sets the list of memcached servers to be used. Each item must be the IP or hostname of the
	 * server, which can include the port separated by colon e.g. 127.0.0.1:12345 (default port is 11211). */
	public void setServers(List<String> value) {
		servers = new InetSocketAddress[value.size()];
		int pos = 0;
		for (String s : value) {
			int cp = value.indexOf(':');
			if (cp > 0) {
				servers[pos++] = new InetSocketAddress(s.substring(0, cp),
					Integer.parseInt(s.substring(cp + 1)));
			} else {
				servers[pos++] = new InetSocketAddress(s, 11211);
			}
		}
	}

	@PostConstruct
	public void init() throws IOException {
		mc = new MemcachedClient(servers);
	}

	@PreDestroy
	public void disconnect() {
		mc.shutdown();
	}

	@Override
	public void store(AlarmChannel channel, String source, String message) {
		if (mc == null) {
			synchronized (this) {
				if (mc == null) {
					try {
						init();
					} catch (IOException ex) {
						log.error("Initializing alarm memcached client", ex);
						return;
					}
				}
			}
		}
		String k = channel == null ? String.format("jalarms:ALL:%s:%s", source == null ? "" : source,
			AlarmSender.hash(message)): String.format("jalarms:chan%d:%s:%s", channel.hashCode(),
				source == null ? "" : source, AlarmSender.hash(message));
		//We don't care about the actual value, just that the key exists
		mc.set(k, channel == null ? defint : (channel.getMinResendInterval() / 1000), (byte)0);
	}

	@Override
	public boolean shouldResend(AlarmChannel channel, String source,
			String message) {
		if (mc == null) {
			return true;
		}
		String k = channel == null ? String.format("jalarms:ALL:%s:%s", source == null ? "" : source,
			AlarmSender.hash(message)): String.format("jalarms:chan%d:%s:%s", channel.hashCode(),
				source == null ? "" : source, AlarmSender.hash(message));
		//If the entry exists, don't resend
		try {
			return mc.get(k) == null;
		} catch (OperationTimeoutException ex) {
			log.error("Timeout waiting to retrieve {} from memcached", k, ex);
		} catch (RuntimeException ex) {
			//Not a big fan of catching RTE but spymemcached throws exactly this in case of disconnection
			//and other causes, instead of a more specific exception.
			log.error("Retrieving key {} from memcached", k, ex.getCause() == null ? ex : ex.getCause());
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Memcached(%s)", mc == null ? "disconnected" : mc.getAvailableServers());
	}

	@Override
	public void shutdown() {
		if (mc != null) {
			mc.shutdown();
		}
	}

}
