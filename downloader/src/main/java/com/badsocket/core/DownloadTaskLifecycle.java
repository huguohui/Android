package com.badsocket.core;

import java.io.IOException;

/**
 * Created by skyrim on 2017/12/28.
 */

public interface DownloadTaskLifecycle extends TaskLifecycle {


	void onPause() throws IOException, Exception;


	void onResume() throws Exception;


	void onStore();


	void onRestore();


}
