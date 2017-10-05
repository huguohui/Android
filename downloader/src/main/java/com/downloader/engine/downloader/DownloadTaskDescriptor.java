package com.downloader.engine.downloader;

import java.net.URL;

/**
 * Descriptor for downloading task.
 */
public class DownloadTaskDescriptor extends Descriptor {
	/** URL for downloading. */
	protected URL mUrl;

	/** Task priority. */
	protected int mPriority;

	/** Number of max used for threads. */
	protected int mMaxThread;

	protected String path;


	public String getPath() {
		return path;
	}


	public DownloadTaskDescriptor setPath(String path) {
		this.path = path;
		return this;
	}


	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL mUrl) {
		this.mUrl = mUrl;
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


		public Builder setUrl(URL mUrl) {
			descriptor.mUrl = mUrl;
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


		public DownloadTaskDescriptor build() {
			return descriptor;
		}
	}
}
