package com.solab.alarms.channels;

import com.solab.alarms.AbstractAlarmChannel;

/** A very simple alarm channel that only prints the alarm messages to STDOUT.
 * A source can optinally be defined so that only messages matching the source for this channel
 * will be printed out.
 * 
 * @author Enrique Zamudio
 */
public class TestChannel extends AbstractAlarmChannel {

	private String src;

	/** Sets a value that will be matched against the sources received in {@link #createSendTask(String, String)};
	 * by default it's null so that all alarms are printed out, but you can set a value here to print only
	 * the messages from the specified source. */
	public void setAlarmSource(String value) {
		src = value;
	}

	@Override
	protected Runnable createSendTask(String msg, String source) {
		if (src == null || (src != null && src.equals(source))) {
			return new DummyTask(msg);
		}
		return null;
	}

	@Override
	protected boolean hasSource(String alarmSource) {
		return src != null && src.equals(alarmSource);
	}

	private class DummyTask implements Runnable {
		private final String msg;
		private DummyTask(String alarm) {
			msg = alarm;
		}
		public void run() {
			if (src == null) {
				System.out.println(String.format("ALARM: %s", msg));
			} else {
				System.out.println(String.format("ALARM: [%s] %s", src, msg));
			}
		}
	}
}
