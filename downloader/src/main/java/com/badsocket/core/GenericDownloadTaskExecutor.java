package com.badsocket.core;

import com.badsocket.core.executor.AbstractDownloadTaskExecutor;

import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by skyrim on 2017/12/15.
 */
public class GenericDownloadTaskExecutor extends AbstractDownloadTaskExecutor {


	public GenericDownloadTaskExecutor(int corePoolSize) {
		super(corePoolSize);
	}


	public GenericDownloadTaskExecutor(int corePoolSize, ThreadFactory factory) {
		super(corePoolSize, factory);
	}


	@Override
	public void pause(Task t) throws Exception {

	}


	@Override
	public void resume(Task t) throws Exception {

	}


	@Override
	public void stop(Task t) throws Exception {

	}


	@Override
	public void start(Task task) throws Exception {

	}


	@Override
	public void start(Task task, long delay) throws Exception {

	}


	@Override
	public boolean isDone(Task t) {
		return false;
	}


	@Override
	public void cancel(Task t) {

	}


	@Override
	public List<Task> tasks() {
		return null;
	}


	@Override
	public boolean remove(Task t) {
		return false;
	}
}
