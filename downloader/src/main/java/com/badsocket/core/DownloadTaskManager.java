package com.badsocket.core;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.Task;

import java.util.Collection;
import java.util.List;

/**
 * Abstracts for manager of download task.
 */
public interface DownloadTaskManager extends Manager<DownloadTask> {

	public boolean hasTask(Task task);

	public boolean deleteTask(Task task);

	public DownloadTask getTask(int i);

	public boolean addTasks(Collection<? extends DownloadTask> tasks);

	public boolean addTask(DownloadTask task);

	public abstract DownloadTask getTaskById(int id);

	public abstract void start(int i) throws Exception;

	public abstract void start(DownloadTask task) throws Exception;

	public abstract void pause(int i) throws Exception;

	public abstract void pause(DownloadTask task) throws Exception;

	public abstract void resume(int i) throws Exception;

	public abstract void resume(DownloadTask task) throws Exception;

	public abstract void stop(int i) throws Exception;

	public abstract void stop(DownloadTask task) throws Exception;

	public abstract void startAll() throws Exception;

	public abstract void pauseAll() throws Exception;

	public abstract void resumeAll() throws Exception;

	public abstract void stopAll() throws Exception;

	public abstract boolean isAutoStart();

	public abstract void setAutoStart(boolean is);

	public abstract void setParallelTaskNum(int num);

	public abstract int getParallelTaskNum();

	public abstract List<DownloadTask> getUncompleteTasks();

	public abstract List<DownloadTask> getCompletedTasks();

}
