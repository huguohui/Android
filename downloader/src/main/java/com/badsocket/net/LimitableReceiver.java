package com.badsocket.net;

public interface LimitableReceiver extends Receiver {

	/**
	 * Sets receiver's receive speed to special value in every second.
	 * @param bytes Max receiveable bytes in one second.
	 */
	void setLimit(long bytes);

	/**
	 * Get current speed limit.
	 * @return Current speed limit.
	 */
	long getLimit();

	/**
	 * Check this receiver whether speed limit supported.
	 * @return Whether limit speed supported.
	 */
	boolean limitSupported();

	boolean isLimited();
}
