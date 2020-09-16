package com.badsocket.app;

import android.os.Binder;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.Task;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.DownloadTaskInfoStorage;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.HttpProtocolHandler;
import com.badsocket.core.downloader.InternetDownloader;

import java.util.List;

/**
 * Created by skyrim on 2017/10/15.
 */

public class DownloadAdapter extends Binder implements Downloader {

	protected Downloader downloader;

	public DownloadAdapter(Downloader downloader) throws Exception {
		this.downloader = downloader;
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

	@Override
	public void init() throws Exception {
		addProtocolHandler(Protocols.HTTP, new HttpProtocolHandler());
		downloader.init();
	}

	@Override
	public boolean isTaskExists(Task task) {
		return downloader.isTaskExists(task);
	}

	/**
	 * Create a new download task.
	 *
	 * @param desc Descriptor.
	 */
	@Override
	public DownloadTask newTask(final DownloadTaskDescriptor desc) throws Exception {
		return downloader.newTask(desc);
	}

	@Override
	public DownloadTask findTask(int id) {
		return downloader.findTask(id);
	}

	@Override
	public DownloadTask findTaskById(int idx) {
		return downloader.findTaskById(idx);
	}

	@Override
	public void addTask(DownloadTask t) throws Exception {
		downloader.addTask(t);
	}

	@Override
	public void deleteTask(int id) {
		downloader.deleteTask(id);
	}

	@Override
	public void deleteTask(DownloadTask task) {
		downloader.deleteTask(task);
	}

	@Override
	public void startTask(int id) throws Exception {
		downloader.startTask(id);
	}

	@Override
	public void startTask(DownloadTask task) throws Exception {
		downloader.startTask(task);
	}

	@Override
	public void stopTask(int id) throws Exception {
		downloader.stopTask(id);
	}

	@Override
	public void stopTask(DownloadTask task) throws Exception {
		downloader.stopTask(task);
	}

	@Override
	public void pauseTask(int id) throws Exception {
		downloader.pauseTask(id);
	}

	@Override
	public void pauseTask(DownloadTask task) throws Exception {
		downloader.pauseTask(task);
	}

	@Override
	public void resumeTask(int id) throws Exception {
		downloader.resumeTask(id);
	}

	@Override
	public void resumeTask(DownloadTask task) throws Exception {
		downloader.resumeTask(task);
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
	public void addProtocolHandler(Protocols ptl, ProtocolHandler ph) {
		downloader.addProtocolHandler(ptl, ph);
	}

	@Override
	public ProtocolHandler getProtocolHandler(Protocols ptl) {
		return downloader.getProtocolHandler(ptl);
	}

	@Override
	public void setParallelTaskNum(int num) {
		downloader.setParallelTaskNum(num);
	}

	@Override
	public int getParallelTaskNum() {
		return downloader.getParallelTaskNum();
	}

	@Override
	public Context getDownloaderContext() {
		return downloader.getDownloaderContext();
	}

	@Override
	public InternetDownloader.ThreadAllocStategy getThreadAllocStategy() {
		return downloader.getThreadAllocStategy();
	}

	@Override
	public void setThreadAllocStategy(InternetDownloader.ThreadAllocStategy stategy) {
		downloader.setThreadAllocStategy(stategy);
	}

	@Override
	public DownloadTaskInfoStorage getDownloadTaskStorage() {
		return downloader.getDownloadTaskStorage();
	}

	@Override
	public void setDownloadTaskInfoStorage(DownloadTaskInfoStorage storage) {
		downloader.setDownloadTaskInfoStorage(storage);
	}

	@Override
	public void exit() throws Exception {
		downloader.exit();
	}

	@Override
	public long runtime() {
		return downloader.runtime();
	}
}
