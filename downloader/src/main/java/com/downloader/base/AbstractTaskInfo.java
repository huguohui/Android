package com.downloader.base;

/**
 * Information for task.
 */
public abstract class AbstractTaskInfo {
	/** Name of task. */
	private String mName;

	/** Start time of task in millisecond. */
	private long mStartTime;

	/** Time of task execution time. **/
	private long mExectionTime;


	/**
	 * Create a instance of AbstractTaskInfo by special mName.
	 * @param name Name of task.
	 */
	public AbstractTaskInfo(String name) {
		this.mName = name;
	}

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
