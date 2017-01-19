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
	
	
}
