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
package com.solab.alarms.channels;

import ie.omk.smpp.Address;
import ie.omk.smpp.Connection;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.net.TcpLink;
import ie.omk.smpp.version.SMPPVersion;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import com.solab.alarms.AbstractAlarmChannel;

/** An AlarmChannel that sends its messages via SMPP (Short Message Peer-to-peer Protocol).
 * To use this channel, you need access to a SMSC (Short Message Service Central) with an account
 * that can send messages. Any incoming messages are ignored.
 * Only a Transmitter connection of SMPP 3.3 will be created.
 * 
 * You can define a different list of phones for each alarm source, using the phonesBySource property;
 * simply set a map where the keys are the alarm sources and the values are lists of phones. The point
 * of this is to be able to send different alarms to different phones, depending on the alarm source.
 * 
 * @author Enrique Zamudio
 */
public class SmppChannel extends AbstractAlarmChannel {

	private TcpLink link;
	private Connection conn;
	private String host;
	private String uname;
	private String pass;
	private String sysType;
	private Address src;
	private List<String> phones;
	private Map<String, List<String>> sourcePhones;
	private int port;

	/** Sets the message source (the number from which the message is sent; usually the SMSC sets it) */
	@Resource
	public void setSource(String value) {
		src = new Address(0, 0, value);
	}
	/** Sets the SMSC host. */
	@Resource
	public void setHost(String value) {
		host = value;
	}
	/** Sets the SMSC port. */
	@Resource
	public void setPort(int value) {
		port = value;
	}
	/** Sets the System ID property (the SMSC admin will provide this to you). */
	@Resource
	public void setSystemID(String value) {
		uname = value;
	}
	/** Sets the system type property (the SMSC admin will provide this to you, although it's optional). */
	public void setSystemType(String value) {
		sysType = value;
	}
	@Resource
	public void setPassword(String value) {
		pass = value;
	}
	/** Specifies the list of mobile numbers to send the alarms to. */
	@Resource
	public void setPhones(List<String> value) {
		phones = value;
	}

	/** Sets different lists of phones, one for each alarm source. */
	public void setPhonesBySource(Map<String, List<String>> value) {
		sourcePhones = value;
	}

	/** Connects to the SMSC, using a SMPP 3.3 Transmitter connection. */
	@PostConstruct
	public void init() {
		//Connect to the SMSC
		try {
			link = new TcpLink(host, port);
			link.setTimeout(30000);
			link.open();
			link.setTimeout(0);
			conn = new Connection(link, true);
			conn.setVersion(SMPPVersion.V33);
			conn.autoAckLink(true);
			conn.autoAckMessages(true);
			conn.bind(Connection.TRANSMITTER, uname, pass, sysType);
			conn.setInterfaceVersion(SMPPVersion.V33);
		} catch (IOException ex) {
			log.error("Connecting to SMSC {}:{} as {}; SMPP alarms will not be sent.",
					new Object[]{host, port, uname});
			conn = null;
		}
	}

	@Override
	protected Runnable createSendTask(final String msg, final String src) {
		return new SmsTask(msg, src);
	}

	@Override
	protected boolean hasSource(final String alarmSource) {
		return sourcePhones != null && sourcePhones.containsKey(alarmSource);
	}

	@Override
	@PreDestroy
	public void shutdown() {
		super.shutdown();
		try {
			conn.unbind();
			conn.closeLink();
		} catch (IOException ex) {
			log.error("Closing SMPP connection", ex);
		}
	}

	/** This task send a SMS to the numbers defined in the phones property of the SmppChannel.
	 * 
	 * @author Enrique Zamudio
	 */
	private class SmsTask implements Runnable {
		private final String msg;
		private final String asrc;
		private SmsTask(String m, String source) {
			msg = m;
			asrc = source;
		}
		public void run() {
			//Send through SMSC to every phone on the list
			List<String> dsts = phones;
			if (asrc != null && sourcePhones.containsKey(asrc)) {
				dsts = sourcePhones.get(asrc);
			}
			for (String p : dsts) {
				SubmitSM req = new SubmitSM();
				req.setSource(src);
				req.setMessageText(msg);
				req.setDestination(new Address(0, 0, p));
				try {
					conn.sendRequest(req);
				} catch (IOException ex) {
					log.error("Sending SMPP alarm to {}", p);
				}
			}
		}

	}

}
