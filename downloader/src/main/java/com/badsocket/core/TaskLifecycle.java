package com.badsocket.core;

import java.io.IOException;

/**
 * Created by skyrim on 2017/12/28.
 */


interface TaskLifecycle {


	void onCreate(Task.TaskExtraInfo info) throws Exception;


	void onStart();


	void onStop();


}