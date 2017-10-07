package com.downloader.engine.downloader;

import com.downloader.net.WebAddress;

import java.net.URL;
import java.nio.channels.WritableByteChannel;

/**
 * Descriptor for downloading task.
 */
public class DownloadDescriptor extends Descriptor {
	/** URL for downloading. */
	protected WebAddress mWebAddress;

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


	public static DownloadDescriptor fromDownloadTaskInfo(DownloadTaskInfo info) {
		DownloadDescriptor desc = new DownloadDescriptor.Builder()
				.setAddress(info.getAddress())
				.setMaxThread(info.getTotalThreads())
				.setPriority(info.getPriority())
				.setPath(info.getPath())
				.build();
		return desc;
	}


	public WebAddress getAddress() {
		return mWebAddress;
	}

	public void setAddress(WebAddress mUrl) {
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


		public Builder setAddress(WebAddress address) {
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
