package com.downloader.manager;

import com.downloader.engine.downloader.DownloadTask;

import static android.R.attr.id;


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
	
	
	public void startTask(int id) throws Exception {

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


	@Override
	public void start(int i) throws Exception {
		synchronized (mList) {
			if (mList.isEmpty())
				return;

			DownloadTask dt = mList.get(id);
			if (dt == null) {
				throw new RuntimeException("The specail task not exists!");
			}

			dt.start();
		}
	}


	@Override
	public void pause(int i) {

	}


	@Override
	public void resume(int i) {

	}


	@Override
	public void stop(int i) {

	}
}
