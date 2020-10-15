package com.badsocket.net;

public interface AsyncReceiver extends Receiver {

	void setOnFinishedListener(OnFinishedListener onFinishedListener) ;

	void setOnStopListener(OnStopListener onStopListener);

	interface OnFinishedListener {
		void onFinished(Receiver r);
	}

	interface OnStopListener {
		void onStop(Receiver r);
	}

}
