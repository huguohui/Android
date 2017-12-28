package com.badsocket.core;

/**
 * Created by skyrim on 2017/12/28.
 */


interface TaskLifecycle {


	void onCreate(Context context, Task.TaskExtraInfo info);


	void onStart();


	void onStop();


	void onDestory();


}