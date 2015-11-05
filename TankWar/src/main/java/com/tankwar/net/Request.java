package com.tankwar.net;

/**
 * Describe a request.
 * @author HGH
 * @since 2015/11/04
 */
public interface Request {
	/**
	 * Request event listener.
	 */
	interface EventListener {
		/**
		 * When prepare to request call this method.
		 */
		void onPrepare();
	
		
		/**
		 * When sending a request call this method.
		 */
		void onSend();

		
		/**
		 * When send has error, call this method.
		 */
		void onError();

		
		/**
		 * When request timeout, call this method.
		 */
		void onTimeout();


		/**
		 * When complete request, call this method.
		 */
		void onComplete();
	}
	
	
	/**
	 * Send a request.
	 */
	void sendRequest();
}
