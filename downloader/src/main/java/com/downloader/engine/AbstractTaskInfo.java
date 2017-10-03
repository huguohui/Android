package com.downloader.engine;

/**
 * Information for task.
 */
public abstract class AbstractTaskInfo {
	/** Name of task. */
	protected String name;

	/** Start time of task in millisecond. */
	protected long startTime;

	/** Time of task execution time. **/
	protected long exectionTime;

	protected float progress;


	public float getProgress() {
		return progress;
	}


	public AbstractTaskInfo setProgress(float progress) {
		this.progress = progress;
		return this;
	}


	public String getName() {
		return name;
	}


	public void setName(String mName) {
		this.name = mName;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long mStartTime) {
		this.startTime = mStartTime;
	}


	public long getExectionTime() {
		return exectionTime;
	}


	public void setExectionTime(long mExectionTime) {
		this.exectionTime = mExectionTime;
	}
}
