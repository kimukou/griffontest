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

/** This interface defines the behavior of an alarm channel. Alarm channels are used by
 * the AlarmSender. An AlarmChannel can have a default user list, and it can also send
 * messages to specific users for certain cases.
 * 
 * @author Enrique Zamudio
 */
public interface AlarmChannel {

	/** Sends the alarm message to the users defined for the channel.
	 * @param msg The alarm message to be sent.
	 * @param source The alarm source. A channel can send the alarm to different recipients depending on the source.
	 * This parameter can be null, which means that it should be sent to the default recipients for the channel. */
	public void send(String msg, String source);

	/** Returns the minimum time interval to resend the same message through this channel. An interval
	 * of 0 or a negative number means that the same message will always be sent. This interval is specified in milliseconds. */
	public int getMinResendInterval();

	/** Shuts the channel down, closing any open connections it has and freeing up all its resources.
	 * Once this method is invoked on a channel, the channel should not be used again. */
	public void shutdown();

}
