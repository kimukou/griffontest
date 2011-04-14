/*
jAlarms A simple Java library to enable server apps to send alarms to sysadmins.
Copyright (C) 2009 Enrique Zamudio Lopez

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/
package com.solab.alarms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** An abstract implementation of AlarmChannel, to ease the creation of custom channels.
 * Defines a minResendInterval of 60 seconds.
 * Subclasses only need to implement the createSendTask(String) method, returning a new
 * Runnable in every call; this Runnable will be queued in a thread pool to be executed
 * as soon as possible, but without interrupting normal program flow.
 * This class already handles what is needed to avoid sending repeated messages very often.
 * 
 * @author Enrique Zamudio
 */
public abstract class AbstractAlarmChannel implements AlarmChannel {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private ExecutorService sendPool = Executors.newSingleThreadExecutor();
	private boolean up = true;
	private int minResend = 60000;

	/** Sets the minimum amount of time between equal messsages. The same message will not be sent through
	 * the channel if it was last sent before this interval has elapsed, unless AlarmSender.sendAlways() is
	 * used. */
	public void setMinResendInterval(int millis) {
		minResend = millis;
	}
	/** Returns the minimum amount of time that must elapse before a previously sent alarm message can be
	 * sent again. The interval is in milliseconds. */
	public int getMinResendInterval() {
		return minResend;
	}

	/** Sends an alarm message for the specified source. If the concrete subclass returns null from the
	 * {@link #createSendTask(String, String)} method, no alarm is sent, and no record is made of the
	 * message's last time being sent. */
	public void send(String msg, final String source) {
		if (!up) return;
		Runnable task = createSendTask(msg, source);
		if (task != null) {
			try {
				//Queue to the thread pool
				sendPool.execute(task);
			} catch (RejectedExecutionException ex) {
				//Run in the calling thread
				task.run();
			}
		}
	}

	/** Shuts down the thread pool and rejects any more incoming alarms. */
	public void shutdown() {
		up = false;
		sendPool.shutdown();
	}

	/** Subclasses need to create and return a new Runnable in each call to this method. The returned
	 * Runnable will be queued in a thread pool to avoid latency in the normal program flow.
	 * If a subclass decides not to send the alarm message for some reason, it can return null so that nothing
	 * is queued.
	 * @param msg The message to be sent.
	 * @param source The alarm source. A channel can have different recipient lists depending on the alarm source.
	 */
	abstract protected Runnable createSendTask(String msg, String source);

	/** This method is used to determine if a certain alarm channel has a special condition for the specified
	 * alarmSource or not. It's used to determine if the alarm messages should be saved as regular alarms or
	 * as messages for that specific source. */
	abstract protected boolean hasSource(String alarmSource);

}
