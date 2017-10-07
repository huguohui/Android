package com.downloader.engine;

import com.downloader.engine.downloader.Downloader;

/**
 * Created by skyrim on 2017/10/7.
 */
public interface Monitor {


	void monitor(Object d);


	Object collectedData();


	interface OnMonitoredListener {

		void OnMonitored(Object obj);

	}


}
