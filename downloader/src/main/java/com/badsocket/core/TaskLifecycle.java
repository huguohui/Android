package com.badsocket.core;

/**
 * Created by skyrim on 2017/12/28.
 */

interface TaskLifecycle {

	void onCreate(Task.TaskExtraInfo info) throws Exception;

	void onStart() throws Exception;

	void onStop() throws Exception;

}