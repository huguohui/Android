package com.badsocket.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by skyrim on 2017/12/15.
 */

public abstract class AbstractDownloadTaskExecutor extends ScheduledThreadPoolExecutor implements DownloadTaskExecutor {

	private ExecutorService executorService;

	private Map<Task, Future<Task>> futureMap = new ConcurrentHashMap<>();


	public AbstractDownloadTaskExecutor(int corePoolSize) {
		super(corePoolSize);
	}


	public AbstractDownloadTaskExecutor(int coolPoolSize, ThreadFactory factory) {
		super(coolPoolSize, factory);

	}


	@Override
	public Future<Task> start(Task task) throws Exception {
		return start(task, 0);
	}


	@Override
	public Future<Task> start(Task task, long delay) throws Exception {
		DownloadTask dt = (DownloadTask) task;
		Future<Task> future = super.schedule((Callable<Task>) task, delay, TimeUnit.MILLISECONDS);
		futureMap.put(task, future);
		dt.onStart();
		return future;
	}


	@Override
	public void start(Task task, long delay, long interval) throws Exception {
		DownloadTask dt = (DownloadTask) task;
		super.scheduleWithFixedDelay(task, delay, interval, TimeUnit.MILLISECONDS);
		dt.onStart();
	}


	@Override
	public boolean isDone(Task t) {
		Future<Task> future = futureMap.get(t);
		return future.isDone();
	}


	@Override
	public void cancel(Task t) {
		Future<Task> future = futureMap.get(t);
		future.cancel(true);
	}


	@Override
	public List<Task> tasks() {
		List<Task> list = new ArrayList<>();
		list.addAll(futureMap.keySet());
		return list;
	}


	@Override
	public void pause(Task t) throws Exception {
		DownloadTask task = (DownloadTask) t;
		cancel(t);
		remove(t);
		task.onPause();
		submit((Callable<Task>) t);
	}


	@Override
	public void resume(Task t) throws Exception {
		DownloadTask task = (DownloadTask) t;
		cancel(t);
		remove(t);
		task.onResume();
		submit((Callable<Task>) task);
	}


	public boolean remove(Task t) {
		return super.remove(t);
	}


	@Override
	public void stop(Task t) throws Exception {
		DownloadTask task = (DownloadTask) t;
		cancel(t);
		remove(t);
		task.onStop();
		submit((Callable<Task>) t);
	}
}
