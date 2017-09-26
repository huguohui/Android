package com.downloader.engine;

/**
 * Information for task.
 */
public abstract class AbstractTaskInfo {
	/** Name of task. */
	protected String mName;

	/** Start time of task in millisecond. */
	protected long mStartTime;

	/** Time of task execution time. **/
	protected long mExectionTime;

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}


	public long getStartTime() {
		return mStartTime;
	}

	public void setStartTime(long mStartTime) {
		this.mStartTime = mStartTime;
	}

	public long getExectionTime() {
		return mExectionTime;
	}

	public void setExectionTime(long mExectionTime) {
		this.mExectionTime = mExectionTime;
	}
}
