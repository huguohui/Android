package com.downloader.manager;

import com.downloader.base.AbstractDownloadTask;
import com.downloader.base.Protocol;
import com.downloader.base.AbstractTaskInfo;
import com.downloader.http.HttpDownloadTask;

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
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = null;
	
	
	/**
	 * Private constructor for singleton pattern.
	 */
	private DownloadTaskManager() {

	}
	
	
	public void addTask(AbstractDownloadTask task) {
		if (task == null)
			throw new RuntimeException("The task is null!");
		
		mList.add(task);
	}
	
	
	public void startTask(int id) throws Exception {
		AbstractDownloadTask dt = mList.get(id);
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
				for (AbstractDownloadTask dt : mList) {
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
	 * To create something for managing.
	 *
	 * @param descriptor Data for creating.
	 * @return true for success, false for fail.
	 */
	@Override
	public AbstractDownloadTask create(AbstractDescriptor descriptor) throws Throwable {
		return create((DownloadTaskDescriptor) descriptor);
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
	public AbstractDownloadTask create(DownloadTaskDescriptor desc) throws Throwable {
		URL url = desc.getUrl();
		String protocol = url.getProtocol();
		AbstractDownloadTask adt = null;

		switch(Protocol.valueOf(protocol)) {
			case HTTP:
				adt = new HttpDownloadTask(url.getFile());
				AbstractTaskInfo ti = adt.info();
				ti.setName("abc");

				break;
		}

		return adt;
	}
}
