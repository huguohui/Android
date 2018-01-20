package com.badsocket.core;

import com.badsocket.core.downloader.Downloader;

/**
 * Created by skyrim on 2017/12/28.
 */

public interface DownloadTaskLifecycle extends TaskLifecycle {


	void onPause() throws Exception;


	void onResume() throws Exception;


	void onStore() throws Exception;


	void onRestore(Downloader downloader) throws Exception;


}
