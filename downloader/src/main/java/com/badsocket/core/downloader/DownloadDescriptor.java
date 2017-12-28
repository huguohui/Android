package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.net.DownloadAddress;

/**
 * Descriptor for downloading task.
 */
public class DownloadDescriptor extends Descriptor {
	/** URL for downloading. */
	protected DownloadAddress mWebAddress;

	/** Task priority. */
	protected int mPriority;

	/** Number of max used for threads. */
	protected int mMaxThread;

	protected String path;


	public String getPath() {
		return path;
	}


	public DownloadDescriptor setPath(String path) {
		this.path = path;
		return this;
	}


	public static DownloadDescriptor fromDownloadTaskInfo(DownloadTask info) {
		DownloadDescriptor desc = new DownloadDescriptor.Builder()
				.setAddress(new DownloadAddress(info.getURL()))
				.setMaxThread(info.getSectionNumber())
				.setPriority(info.getPriority())
				.setPath(info.getDownloadPath().getAbsolutePath())
				.build();
		return desc;
	}


	public DownloadAddress getAddress() {
		return mWebAddress;
	}

	public void setAddress(DownloadAddress mUrl) {
		this.mWebAddress = mUrl;
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

		private DownloadDescriptor descriptor;

		public Builder() {
			descriptor = new DownloadDescriptor();
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


		public DownloadDescriptor build() {
			return descriptor;
		}
	}
}
