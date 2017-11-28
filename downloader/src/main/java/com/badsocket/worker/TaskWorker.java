package com.badsocket.worker;

import com.badsocket.manager.ThreadManager;

/**
 * Created by skyrim on 2017/11/26.
 */

public class TaskWorker extends AsyncWorker {

	public TaskWorker(ThreadManager tm) {
		super(tm);
	}

	public TaskWorker(ThreadManager tm, long interval) {
		super(tm, interval);
	}



}
