package com.downloader.manager;

import com.downloader.base.DownloadTask;
import com.downloader.base.Protocol;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager extends AbstractDownloadTaskManager {
	/** Download queue. */
	private List<DownloadTask> mQueue = new LinkedList<>();
	
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = null;
	
	
	/**
	 * Private constructor for singleton pattern.
	 */
	private DownloadTaskManager() {

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
	 * Get a managed object by index.
	 *
	 * @param idx Index of object.
	 * @return Managed object.
	 */
	@Override
	public DownloadTask get(int idx) {
		return null;
	}

	/**
	 * Add a object for management.
	 *
	 * @param obj Object what will to managing.
	 */
	@Override
	public boolean add(DownloadTask obj) {
		return false;
	}

	/**
	 * Remove a managed object by index.
	 *
	 * @param idx Index of managed object.
	 * @return Removed object or null on remove failed.
	 */
	@Override
	public DownloadTask remove(int idx) {
		return null;
	}

	/**
	 * Search a object.
	 *
	 * @param sf A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	@Override
	public List<DownloadTask> search(SearchFilter sf) {
		return null;
	}


	/**
	 * Get a list that inArray all managed objects.
	 *
	 * @return A list that inArray all managed objects.
	 */
	@Override
	public List<DownloadTask> getList() {
		return mQueue;
	}


	/**
	 * To create something for managing.
	 *
	 * @param descriptor Data for creating.
	 * @return true for success, false for fail.
	 */
	@Override
	public DownloadTask create(AbstractDescriptor descriptor) throws Throwable {
		DownloadTaskDescriptor dtd = (DownloadTaskDescriptor) descriptor;

		return null;
	}


	@Override
	public Iterator<DownloadTask> iterator() {
		return mQueue.iterator();
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {

	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {

	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {

	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {

	}


	/**
	 * Create a download task by task descriptor.
	 *
	 * @param desc Task descriptor.
	 * @return Download task instance.
	 * @throws Throwable When exception occured.
	 */
	@Override
	public DownloadTask create(DownloadTaskDescriptor desc) throws Throwable {

		return null;
	}
}
