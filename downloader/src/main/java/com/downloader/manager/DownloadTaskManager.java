package com.downloader.manager;

import com.downloader.client.downloader.DownloadTask;
import com.downloader.net.SupportedProtocol;

import java.net.URL;
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
	
	
	public void addTask(DownloadTask task) {
		if (task == null)
			throw new RuntimeException("The task is null!");
		
		mList.add(task);
	}
	
	
	public void startTask(int id) throws Exception {
		DownloadTask dt = mList.get(id);
		if (dt == null)
			throw new RuntimeException("The specail task not exists!");
		
	//	dt.start();
	}
	
	/**
	 * To monitoring download task.
	 */
	public void monitoring() {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (DownloadTask dt : mList) {
//					int pg = dt.progress();
//					System.out.println(pg + "%");
//					if (pg == 100)
//						timer.cancel();
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
	public DownloadTask create(AbstractDescriptor descriptor) throws Throwable {
		URL url = ((DownloadTaskDescriptor) descriptor).getUrl();
		String protocol = url.getProtocol();
		DownloadTask adt = null;

		switch(SupportedProtocol.valueOf(protocol)) {
			case HTTP:
//				adt = new HttpDownloadTask(url.getFile());
//				AbstractTaskInfo ti = adt.info();
//				ti.setName("abc");

				break;
		}

		return adt;
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
	public void resume()  {

	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() {

	}
}
