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
package com.solab.alarms.channels.twitter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.solab.alarms.AbstractAlarmChannel;
import com.solab.util.Base64;

/** A channel that uses Twitter to publish alarms. The way this works is like this:
 * You set up a Twitter account for your application, and follow it from your own account.
 * You must run the TwitterAuth class in this app to generate a Token associating the
 * jAlarms app with the account you'll use for the app (you only need to do this once).
 * Then you set the PIN you get in the twitterPin property of this component and you're ready
 * to go; any alarms published here will be posted as the status of the account you set up for
 * the app, and since you're following it, you'll know when something bad happens.
 * 
 * Twitter ignores repeated status updates, which is kinda necessary here... so this channel
 * prepends the time to the alarm messages, which solves the problem for at least a day
 * (but it seems like Twitter allows repeated status updates after a day, so it's OK).
 * The time is displayed as HHmmss, using no separators to save space.
 * Alarm messages longer than 133 chars will be split into several status messages.
 * 
 * Since this channel only updates the status of a Twitter account, alarm sources are ignored.
 * 
 * @author Enrique Zamudio
 */
public class TwitterChannel extends AbstractAlarmChannel {

	private static final byte[] CONS_SCT = "eejZvP5EuneHtja1YuO6ANj1cYsjBaRGxgWL5U".getBytes();

	private Mac hmac;
	private URL statUrl;
	private String acctoken;
	private String prefix;
	private String apiUrl = "http://api.twitter.com/1/statuses/update.xml";
	private Set<String> sources;
	private byte[] tsecret;

	/** Sets the Twitter API URL to update the user's status. The default value is
	http://api.twitter.com/1/statues/update.xml */
	public void setApiUrl(String value) throws MalformedURLException {
		apiUrl = value;
		statUrl = new URL(apiUrl);
	}
	public String getApiUrl() {
		return apiUrl;
	}

	/** This is an optional property; you can set a prefix to be used in all the status updates;
	 * this is useful in cases where you need to use the same Twitter account for more than one
	 * application, so you can tell which one sent the alarm. If you set a value here, remember
	 * to use a short string; it will be placed at the beginning of the status update
	 * (after the time), in square brackets. */
	public void setPrefix(String value) {
		prefix = value;
	}

	/** You can set a list of sources in this property so that only alarm messages matching any of the sources
	 * are be posted as status updates. If you don't set a value here, all alarm messages are posted, with the
	 * usual rules about min time interval. This is useful if you want to use a Twitter account to only post
	 * alarm messages from certain sources. */
	public void setAlarmSource(Set<String> value) {
		sources = value;
	}

	/** Sets the access token obtained with the TwitterAuth program; this allows jAlarms to post
	 * status updates to the twitter account set up for the alarms. */
	@Resource
	public void setAccessToken(String value) {
		acctoken = value;
	}
	/** Sets the token secret obtained with the TwitterAuth program; this allows jAlarms to post
	 * status updates to the twitter account set up for the alarms. */
	@Resource
	public void setTokenSecret(String value) {
		tsecret = new byte[CONS_SCT.length + value.length() + 1];
		System.arraycopy(CONS_SCT, 0, tsecret, 0, CONS_SCT.length);
		tsecret[CONS_SCT.length] = '&';
		if (value.length() > 0) {
			System.arraycopy(value.getBytes(), 0, tsecret, CONS_SCT.length + 1, value.length());
		}
	}

	/** Initializes the component. Internally it creates a Mac instance with the HmacSHA1 algorithm.
	 * If you configure this component with Spring, this method can be called automatically.
	 * 
	 * @throws NoSuchAlgorithmException if the HmacSHA1 algorithm is not available. */
	@PostConstruct
	public void init() throws NoSuchAlgorithmException, MalformedURLException {
		hmac = Mac.getInstance("HmacSHA1");
		statUrl = new URL(apiUrl);
	}

	@Override
	protected Runnable createSendTask(final String msg, final String src) {
		if (sources != null && !sources.contains(src)) {
			return null;
		}
		return new TwitterTask(msg);
	}

	/** Creates the HMAC-SHA1 signature for the specified message. This method is not thread-safe
	 * so it should be used with caution. */
	String sign(String value) throws InvalidKeyException {
		hmac.init(new SecretKeySpec(tsecret, "HmacSHA1"));
		hmac.update(value.getBytes());
		byte[] sign = hmac.doFinal();
		return Base64.base64Encode(sign, 0, sign.length);
	}

	@Override
	protected boolean hasSource(final String alarmSource) {
		return sources != null && sources.contains(alarmSource);
	}

	/** This is the class that does all the work of posting the alarm as a status update
	 * on the account that has been set up for this.
	 * 
	 * @author Enrique Zamudio
	 */
	private final class TwitterTask implements Runnable {
		private String msg;
		private final Date ts = new Date();
		private TwitterTask(final String mensaje) {
			msg = mensaje;
		}

		private void send(String m) {
			try {
				//Encode the alarm  message. For some stupid reason, we have to URL encode
				//using %20 instead of "+", and then URL encode that AGAIN for the signature.
				StringBuilder __mens = new StringBuilder(URLEncoder.encode(m, "UTF8"));
				int maspos = __mens.indexOf("+");
				while (maspos >= 0) {
					__mens.replace(maspos, maspos+1, "%20");
					maspos = __mens.indexOf("+");
				}
				String _mens = __mens.toString();
				long now = System.currentTimeMillis();
				//Construir firma
				StringBuilder sign = new StringBuilder("POST&").append(URLEncoder.encode(apiUrl, "UTF8"));
				sign.append("&oauth_consumer_key%3DYour1AdO6GZUxDGE8sMQdw");
				sign.append("%26oauth_nonce%3D").append(now);
				sign.append("%26oauth_signature_method%3DHMAC-SHA1");
				sign.append("%26oauth_timestamp%3D").append(now / 1000l);
				sign.append("%26oauth_token%3D").append(URLEncoder.encode(acctoken, "UTF8"));
				sign.append("%26status%3D").append(URLEncoder.encode(_mens, "UTF8"));
				StringBuilder post_data = new StringBuilder("oauth_consumer_key=");
				post_data.append("Your1AdO6GZUxDGE8sMQdw&oauth_nonce=");
				post_data.append(now).append("&oauth_signature_method=HMAC-SHA1");
				post_data.append("&oauth_timestamp=").append(now / 1000l);
				post_data.append("&oauth_token=").append(URLEncoder.encode(acctoken, "UTF8"));
				String _firma = sign(sign.toString());
				post_data.append("&oauth_signature=").append(URLEncoder.encode(_firma, "UTF8"));
				post_data.append("&status=").append(_mens);

				//Enviar datos
				HttpURLConnection conn = (HttpURLConnection)statUrl.openConnection();
				conn.setDoOutput(true);
				conn.setConnectTimeout(8000);
				conn.setReadTimeout(9000);
				conn.setDefaultUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", Integer.toString(post_data.length()));
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.connect();
				conn.getOutputStream().write(post_data.toString().getBytes());
				conn.getOutputStream().flush();
				conn.getInputStream().read();
				conn.disconnect();
			} catch (UnsupportedEncodingException ex) {
				log.error("TwitterChannel encoding access token (or alarm message)", ex);
			} catch (SocketTimeoutException ex) {
				log.error("TwitterChannel not sure if alarm was sent (timeout reading response from twitter.com)");
			} catch (IOException ex) {
				if (ex.getMessage() != null && ex.getMessage().startsWith("Server returned HTTP response code: 403 for URL")) {
					log.error("Twitter REST API {}", ex.getMessage());
				} else {
					log.error("Problems calling the Twitter REST API", ex);
				}
			} catch (InvalidKeyException ex) {
				log.error("OAuth problems signing the request for Twitter", ex);
			}
		}

		public void run() {
			int lim = 133;
			if (prefix == null) {
				msg = String.format("%1$TH%1$TM%1$TS:%2$s", ts, msg);
			} else {
				msg = String.format("%1$TH%1$TM%1$TS[%2$s]%3$s", ts, prefix, msg);
				lim -= prefix.length() + 1;
			}
			while (msg.length() > lim) {
				//Split the message
				//Find the first whitespace before limit
				int pos = lim - 1;
				while (pos > 0 && !Character.isWhitespace(msg.charAt(pos))) {
					pos--;
				}
				if (pos <= 0) {
					//If there's no whitespace, just trunc at lim-3
					pos = lim-1;
				}
				String sub = String.format("%s-", msg.substring(0, pos));
				if (prefix == null) {
					//sub = String.format("%1$TH%1$TM%1$TS:-%2$s", ts, msg.substring(0, pos));
					msg = String.format("%1$TH%1$TM%1$TS:-%2$s", ts, msg.substring(pos+1));
				} else {
					//sub = String.format("%1$TH%1$TM%1$TS[%2$s]-%3$s", ts, prefix, msg.substring(0, pos));
					msg = String.format("%1$TH%1$TM%1$TS[%2$s]-%3$s", ts, prefix, msg.substring(pos+1));
				}
				send(sub);
				try {
					Thread.sleep(1200);
				} catch (InterruptedException ex) {
					//nothing we can do really...
				}
			}
			send(msg);
		}

	}

}
