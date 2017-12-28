package com.badsocket.core;

import com.badsocket.io.writer.FileWriter;
import com.badsocket.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by skyrim on 2017/11/28.
 */

public class HTTPDownloadTask
		extends
		AbstractDownloadTask
{

	protected Context context;

	protected FileWriter fileWriter;

	protected DownloadTaskExtraInfo extraInfo;


	public HTTPDownloadTask(URL url) {
		super(url);
	}


	public HTTPDownloadTask(URL url, String name) {
		super(url, name);
	}


	@Override
	public void onCreate(Context context, TaskExtraInfo info) {
		this.context = context;
		this.extraInfo = (DownloadTaskExtraInfo) info;
	}


	@Override
	public void onStart() {
		startTime = TimeUtils.millisTime();
		isRunning = true;
		try {
			fileWriter = this.context.getFileWriter(new File(getDownloadPath(), getName()), 0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onStop() {

	}


	@Override
	public void onDestory() {

	}


	@Override
	public void onPause() {

	}


	@Override
	public void onResume() {

	}


	protected void prepare() {

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
	public void run() {
		try {
			call();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public Task call() throws Exception {
		return null;
	}


	interface DownloadTaskExtraInfo extends TaskExtraInfo {



	}
}
