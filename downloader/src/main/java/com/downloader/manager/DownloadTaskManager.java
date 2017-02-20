package com.downloader.manager;

import com.downloader.base.DownloadTask;
import com.downloader.base.Protocol;
import com.downloader.base.Task;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager extends AbstractManager<Task> {
	/** Download queue. */
	private List<DownloadTask> mQueue = new LinkedList<>();
	
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = null;
	

	/** Supports protocols. */
	private final String[] protocols = {
		Protocol.HTTP,
		Protocol.HTTPS
	};
	
	
	/**
	 * Private constructor for singleton pattern.
	 */
	private DownloadTaskManager() {
//		switch(StringUtil.inArray(protocols, mUrl.getProtocol())) {
//			case 0:
//				new HttpReceiver(new HttpRequest(mUrl));
//				break;
//		}
	}
	
	
	public void addTask(DownloadTask task) {
		if (task == null)
			throw new RuntimeException("The task is null!");
		
		mQueue.add(task);
	}
	
	
	public void startTask(int id) throws Exception {
		DownloadTask dt = mQueue.get(id);
		if (dt == null)
			throw new RuntimeException("The specail task not exists!");
		
		dt.start();
	}
	
	/**
	 * To monitoring download task.
	 */
	public void monitoring() {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (DownloadTask dt : mQueue) {
					int pg = dt.progress();
					System.out.println(pg + "%");
					if (pg == 100)
						timer.cancel();
				}
			}
		}, 0, 1000);
	}
	
	
	/**
	 * Gets instance of manager.
	 * @return Instance of manager.
	 */
	public final synchronized static DownloadTaskManager getInstance() {
		if (mInstance == null)
			mInstance = new DownloadTaskManager();
		
		return mInstance;
	}


	/**
	 * Search a object.
	 *
	 * @param sf A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	@Override
	public List<Task> search(SearchFilter sf) {
		return null;
	}

	/**
	 * Get a list that inArray all managed objects.
	 *
	 * @return A list that inArray all managed objects.
	 */
	@Override
	public List<Task> getList() {
		return null;
	}


	/**
	 * Delete a object.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public boolean delete(Task obj) throws IOException {
		return false;
	}

	/**
	 * Delete a object.
	 *
	 * @param idx The index of object will to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public boolean delete(int idx) throws Throwable {
		return false;
	}

	/**
	 * Delete a object.
	 *
	 * @return If deleted true else false.
	 */
	@Override
	public void deleteAll() throws Throwable {

	}

	/**
	 * To create something for managing.
	 *
	 * @param data Data for creating.
	 * @return true for success, false for fail.
	 */
	@Override
	public Task create(String data) throws Throwable {
		return null;
	}


	@Override
	public Iterator<Task> iterator() {
		return null;
	}
}
