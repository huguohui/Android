package com.badsocket.core;

/**
 * Created by skyrim on 2017/12/28.
 */

public interface DownloadTaskLifecycle extends TaskLifecycle {


	void onPause();


	void onResume();


	void onStore();


	void onRestore();


}
