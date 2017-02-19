package com.downloader.client;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.downloader.base.DownloadTask;
import com.downloader.base.Protocol;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager {
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
//		switch(StringUtil.contains(protocols, mUrl.getProtocol())) {
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
	
}
