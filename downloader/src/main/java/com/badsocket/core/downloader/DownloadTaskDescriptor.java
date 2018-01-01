package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.Task;
import com.badsocket.net.DownloadAddress;

/**
 * Descriptor for downloading task.
 */
public class DownloadTaskDescriptor extends Descriptor {
	/** URL for downloading. */
	protected DownloadAddress mAddress;

	/** Task priority. */
	protected int mPriority;

	/** Number of max used for threads. */
	protected int mMaxThread;

	protected String path;

	protected Task.TaskExtraInfo taskExtraInfo;

	protected String taskName;


	public DownloadTaskDescriptor() {

	}


	public DownloadTaskDescriptor(DownloadTask task) {
		this.setAddress(task.getDownloadAddress());
		this.setMaxThread(task.getSectionNumber());
		this.setPriority(task.getPriority());
		this.setPath(task.getDownloadPath().getAbsolutePath());
	}


	public String getTaskName() {
		return taskName;
	}


	public DownloadTaskDescriptor setTaskName(String taskName) {
		this.taskName = taskName;
		return this;
	}


	public String getPath() {
		return path;
	}


	public DownloadTaskDescriptor setPath(String path) {
		this.path = path;
		return this;
	}

	public Task.TaskExtraInfo getTaskExtraInfo() {
		return taskExtraInfo;
	}


	public DownloadTaskDescriptor setTaskExtraInfo(Task.TaskExtraInfo taskExtraInfo) {
		this.taskExtraInfo = taskExtraInfo;
		return this;
	}


	public DownloadAddress getAddress() {
		return mAddress;
	}

	public void setAddress(DownloadAddress mUrl) {
		this.mAddress = mUrl;
	}

	public int getPriority() {
		return mPriority;
	}

	public void setPriority(int mPriority) {
		this.mPriority = mPriority;
	}

	public int getMaxThread() {
		return mMaxThread;
	}

	public void setMaxThread(int mMaxThread) {
		this.mMaxThread = mMaxThread;
	}


	public static class Builder {

		private DownloadTaskDescriptor descriptor;

		public Builder() {
			descriptor = new DownloadTaskDescriptor();
		}


		public Builder setAddress(DownloadAddress address) {
			descriptor.setAddress(address);
			return this;
		}


		public Builder setPriority(int mPriority) {
			descriptor.setPriority(mPriority);
			return this;
		}


		public Builder setMaxThread(int mMaxThread) {
			descriptor.setMaxThread(mMaxThread);
			return this;
		}


		public Builder setPath(String path) {
			descriptor.path = path;
			return this;
		}


		public Builder setExtraInfo(Task.TaskExtraInfo info) {
			descriptor.setTaskExtraInfo(info);
			return this;
		}


		public DownloadTaskDescriptor build() {
			return descriptor;
		}
	}
}
