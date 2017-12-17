package com.badsocket.core;

import java.io.File;
import java.net.URL;

/**
 * Created by skyrim on 2017/11/28.
 */

public interface DownloadTask extends Task {

	int getSpeedPerSecond();


	int getSpeedAverage();


	int getSpeedRealTime();


	void setSpeedLimit(int speed);


	int getSpeedLimited();


	URL getURL();


	void setURL(URL url);


	long getLength();


	long getDownloadedLength();


	void setDownloadPath(String path);


	void setDownloadPath(File path);


	File getDownloadPath();


	int getSectionNumber();


	DownloadSection[] getSections();


	abstract class DownloadSection {

		protected int index;

		protected long offsetBegin;

		protected long offsetEnd;

		protected long length;

		protected long downloadedLength;


		public DownloadSection(int index) {
			this.index = index;
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

	}
}
