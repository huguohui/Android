package com.badsocket.core;

/**
 * Created by skyrim on 2017/12/15.
 */

public interface DownloadTaskExecutor extends TaskExecutor {


	void pause(Task t);


	void resume(Task t);



}
