package com.badsocket.core.downloader;

import com.badsocket.core.Controlable;

public interface ControlableClock extends Controlable {
	int MS_SECOND = 1000;

	long runtime();

	long interval();

	void interval(long interval);

	void addOnTickListener(OnTickListener listener);

	static interface OnTickListener {
		void onTick();
	}
}
