package com.downloader;


/**
 * Defines task's behavior.
 */
public abstract class AbsTask implements Task {
	/** Time of task start. */
	private long mStartTime = 0;
	
	/** Time of task finish. */
	private long mFinishTime = 0;
	
	/** Name of task. */
	private String mName = "";


	public long getStartTime() {
		return mStartTime;
	}

	public void setStartTime(long startTime) {
		mStartTime = startTime;
	}

	public long getFinishTime() {
		return mFinishTime;
	}

	public void setFinishTime(long finishTime) {
		mFinishTime = finishTime;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}
	
	
}
