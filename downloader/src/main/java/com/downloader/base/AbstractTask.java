package com.downloader.base;

/**
 * Interface of task.
 */
public abstract class AbstractTask implements Controlable {
	/** Information of this task. */
	private TaskInfo mInfo;


	/**
	 * Get the progress of task. value: 0 ~ 100
	 * @return Progress of task.
	 */
	public abstract int progress();


	public TaskInfo getInfo() {
		return mInfo;
	}

	public void setInfo(TaskInfo mInfo) {
		this.mInfo = mInfo;
	}
}
