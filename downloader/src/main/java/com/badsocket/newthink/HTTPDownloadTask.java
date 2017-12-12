package com.badsocket.newthink;

import com.badsocket.util.TimeUtils;

import java.net.URL;

/**
 * Created by skyrim on 2017/11/28.
 */

public class HTTPDownloadTask
		extends AbstractDownloadTask
{

	public HTTPDownloadTask(URL url) {
		super(url);
	}


	public HTTPDownloadTask(URL url, String name) {
		super(url, name);
	}


	@Override
	public void onCreate() {

	}


	@Override
	public void onStart() {
		startTime = TimeUtils.millisTime();
	}


	@Override
	public void onStop() {

	}


	@Override
	public void onFinish() {

	}


	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {

	}
}
