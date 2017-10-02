package com.downloader.manager;

import com.downloader.engine.downloader.DownloadTask;
import com.downloader.engine.Controlable;

/**
 * Abstracts for manager of download task.
 */
public abstract class AbstractDownloadTaskManager extends AbstractManager<DownloadTask> implements Controlable {

	public abstract void start(int i) throws Exception;

	public abstract void pause(int i) throws Exception;

	public abstract void resume(int i) throws Exception;

	public abstract void stop(int i) throws Exception;

}
