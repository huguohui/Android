package com.badsocket.core;

import com.badsocket.core.downloader.DownloaderContext;
import com.badsocket.core.executor.DownloadTaskExecutor;
import com.badsocket.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	private Map<Task, Future<Integer>> futureMap = new ConcurrentHashMap<>();

	private DownloaderContext context;

	public AbstractDownloadTaskExecutor(int corePoolSize) {
		super(corePoolSize);
	}

	public AbstractDownloadTaskExecutor(int coolPoolSize, ThreadFactory factory) {
		super(coolPoolSize, factory);
	}

	public AbstractDownloadTaskExecutor(DownloaderContext context, int corePoolSize) {
		super(corePoolSize);
		this.context = context;
	}

	@Override
	public Future<Integer> execute(Task task) throws Exception {
		return execute(task, 0);
	}

	@Override
	public Future<Integer> execute(Task task, long delay) throws Exception {
		DownloadTask dt = (DownloadTask) task;
		Future<Integer> future = super.schedule(task, delay, TimeUnit.MILLISECONDS);
		futureMap.put(task, future);
		dt.onStart();
		return future;
	}

	@Override
	public void execute(Task task, long delay, long interval) throws Exception {
		throw new RuntimeException("Not support!");
	}

	@Override
	public boolean isDone(Task t) {
		Future<Integer> future = futureMap.get(t);
		return future.isDone();
	}

	@Override
	public void cancel(Task t) {
		Future<Integer> future = futureMap.get(t);
		if (future != null) {
			future.cancel(true);
		}
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
		purge();
		task.onPause();
		futureMap.put(t, submit(t));
	}

	@Override
	public void resume(Task t) throws Exception {
		DownloadTask task = (DownloadTask) t;
		cancel(t);
		remove(t);
		task.onResume();
		futureMap.put(t, submit(t));
	}

	public void remove(Task t) {
		Future<Integer> future = futureMap.get(t);
		if (!future.isDone() || !future.isCancelled()) {
			future.cancel(true);
		}
		purge();
		futureMap.remove(t);
	}

	@Override
	public void stop(Task t) throws Exception {
		DownloadTask task = (DownloadTask) t;
		cancel(t);
		remove(t);
		purge();
		task.onStop();
		futureMap.put(t, submit(t));
	}
}
