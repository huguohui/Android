package com.badsocket.app;

import android.os.Binder;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.factory.HttpFamilyFactory;
import com.badsocket.net.SocketComponentFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by skyrim on 2017/10/15.
 */

public class DownloadAdapter extends Binder implements Downloader {

	protected Downloader downloader;


	public DownloadAdapter(Downloader downloader) {
		this.downloader = downloader;
		addProtocolHandler(Protocols.HTTP, new ProtocolHandler() {
			@Override
			public SocketComponentFactory socketFamilyFactory() {
				return new HttpFamilyFactory();
			}

		});
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		downloader.start();
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		downloader.pause();
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {
		downloader.resume();
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {
		downloader.stop();
	}


	/**
	 * Create a new download task.
	 *
	 * @param desc Descriptor.
	 */
	@Override
	public DownloadTask newTask(final DownloadDescriptor desc) throws IOException {
		return downloader.newTask(desc);
	}


	@Override
	public DownloadTask findTask(int id) {
		return downloader.findTask(id);
	}




	@Override
	public void addTask(DownloadTask t) {
		downloader.addTask(t);
	}


	@Override
	public void deleteTask(int id) {
		downloader.deleteTask(id);
	}


	@Override
	public void startTask(int id) {
		downloader.startTask(id);
	}


	@Override
	public List<DownloadTask> taskList() {
		return downloader.taskList();
	}


	@Override
	public List<Thread> threadList() {
		return downloader.threadList();
	}


	@Override
	public boolean isStoped() {
		return downloader.isStoped();
	}


	@Override
	public boolean isRunning() {
		return downloader.isRunning();
	}


	@Override
	public boolean isPaused() {
		return downloader.isPaused();
	}


	@Override
	public void addWatcher(MonitorWatcher w) {
		downloader.addWatcher(w);
	}


	@Override
	public Monitor getMonitor() {
		return downloader.getMonitor();
	}


	@Override
	public void addProtocolHandler(Protocols ptl, ProtocolHandler ph) {
		downloader.addProtocolHandler(ptl, ph);
	}


	@Override
	public void setParallelTaskNum(int num) {
		downloader.setParallelTaskNum(num);
	}


	@Override
	public int getParallelTaskNum() {
		return downloader.getParallelTaskNum();
	}
}
