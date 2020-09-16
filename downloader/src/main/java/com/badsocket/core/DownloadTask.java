package com.badsocket.core;

import com.badsocket.core.downloader.ControlableClock;
import com.badsocket.net.newidea.URI;

import java.io.File;
import java.io.Serializable;

/**
 * Created by skyrim on 2017/11/28.
 */

public interface DownloadTask extends Task, DownloadTaskLifecycle {

	boolean isPaused();

	void setSpeedLimit(int speed);

	int getSpeedLimited();

	URI getDownloadURI();

	void setDownloadURI(URI url);

	long size();

	long downloadedSize();

	void setDownloadPath(String path);

	void setDownloadPath(File path);

	File getStorageDir();

	int getSectionNumber();

	DownloadSection[] getSections();

	void setSections(DownloadSection[] sections);

	void addOnDownloadTaskPauseListener(OnDownloadTaskPauseListener listener);

	void addOnDownloadTaskResumeListener(OnDownloadTaskResumeListener listener);

	public static class DownloadSection implements Serializable {

		protected int index;

		protected long offsetBegin;

		protected long offsetEnd;

		protected long length;

		protected long downloadedLength;

		public DownloadSection(int index) {
			this.index = index;
		}

		public DownloadSection setIndex(int index) {
			this.index = index;
			return this;
		}

		public DownloadSection setOffsetBegin(long offsetBegin) {
			this.offsetBegin = offsetBegin;
			return this;
		}

		public DownloadSection setOffsetEnd(long offsetEnd) {
			this.offsetEnd = offsetEnd;
			return this;
		}

		public DownloadSection setLength(long length) {
			this.length = length;
			return this;
		}

		public DownloadSection setDownloadedLength(long downloadedLength) {
			this.downloadedLength = downloadedLength;
			return this;
		}

		public long getOffsetBegin() {
			return offsetBegin;
		}

		public long getOffsetEnd() {
			return offsetEnd;
		}

		public long getLength() {
			return length;
		}

		public long getDownloadedLength() {
			return downloadedLength;
		}

		public int getIndex() {
			return index;
		}

		public String toString() {
			return String.format("[%d, %d, %d, %d]#%d", offsetBegin, offsetEnd, downloadedLength, length, index);
		}

	}

	interface DownloadTaskAction {
		int START = 0,
			PAUSE = 1,
			RESUME = 2,
			STOP = 3,
			STORE = 4,
			RESTORE = 5;
	}

	interface DownloadTaskState extends TaskState {
		int PAUSED = 4,
			PREPARING = 5,
			WAITING = 6,
			PAUSING = 7,
			RESUMING = 8,
			STOPPING = 9,
			STARTING = 10,
			STORED = 11,
			RESTORED = 12;
	}

	interface OnDownloadTaskPauseListener {
		void onDownloadTaskPause(DownloadTask t);
	}

	interface OnDownloadTaskResumeListener {
		void onDownloadTaskResume(DownloadTask t);
	}
}
