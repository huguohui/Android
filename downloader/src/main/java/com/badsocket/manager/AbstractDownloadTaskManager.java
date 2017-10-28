package com.badsocket.manager;

import com.badsocket.engine.downloader.DownloadTask;

/**
 * Abstracts for manager of download task.
 */
public abstract class AbstractDownloadTaskManager extends AbstractManager<DownloadTask>  {


	public abstract void start(int i) throws Exception;


	public abstract void pause(int i) throws Exception;


	public abstract void resume(int i) throws Exception;


	public abstract void stop(int i) throws Exception;


	public abstract void startAll() throws Exception;


	public abstract void pauseAll() throws Exception;


	public abstract void resumeAll() throws Exception;


	public abstract void stopAll() throws Exception;


	public abstract boolean isAutoStart();


	public abstract void setAutoStart(boolean is);

}
