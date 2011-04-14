package com.solab.alarms;

/** The AlarmSender uses a cache to avoid resending the same message very frequently.
 * This interface allows for different implementations of the caching mechanism.
 * 
 * @author Enrique Zamudio
 */
public interface AlarmCache {

	/** Stores the current date for the specified message and optional source, for the given channel. */
	public void store(AlarmChannel channel, String source, String message);

	/** This method returns true if the specified message with optional source has not been sent through
	 * the specified channel very recently (meaning the resend interval for the channel has elapsed). */
	public boolean shouldResend(AlarmChannel channel, String source, String message);

	/** Subclasses must override this, closing and freeing up any resources they use (connections, etc). */
	public void shutdown();

}
