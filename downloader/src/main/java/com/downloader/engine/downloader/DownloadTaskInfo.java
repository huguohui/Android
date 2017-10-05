package com.downloader.engine.downloader;

import com.downloader.engine.TaskInfo;
import com.downloader.io.exceptions.FileFormatException;
import com.downloader.io.writer.DataReader;
import com.downloader.io.writer.DataWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * Information for download task.
 */
public class DownloadTaskInfo extends TaskInfo {
	protected final static byte[] FILE_FORMAT_INFO = {'D', 'T', 'I'};

	public final static int MAX_NAME_LEN = 0xff;

	/** Url of downloading. */
	protected URL url;

	protected String path;

	/** Start time of task in millisecond. */
	protected long startTime;

	/** Finish time of task in millisecond. */
	protected long finishTime;

	/** Used time of downloading in millisecond. */
	protected long usedTime;

	protected int priority;

	/** Length of downloading task. */
	protected long length;

	protected long downloadLength;

	protected int totalThreads;

	protected long[] partOffsetStart;

	protected long[] partLength;

	protected long[] partDownloadLength;


	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long mStartTime) {
		startTime = mStartTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long mFinishTime) {
		finishTime = mFinishTime;
	}

	public long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(long mUsedTime) {
		usedTime = mUsedTime;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long mLength) {
		length = mLength;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL mUrl) {
		url = mUrl;
	}


	public long getDownloadLength() {
		return downloadLength;
	}


	public DownloadTaskInfo setDownloadLength(long downloadLength) {
		this.downloadLength = downloadLength;
		return this;
	}


	public int getTotalThreads() {
		return totalThreads;
	}


	public DownloadTaskInfo setTotalThreads(int totalThreads) {
		this.totalThreads = totalThreads;
		return this;
	}


	public long[] getPartOffsetStart() {
		return partOffsetStart;
	}


	public DownloadTaskInfo setPartOffsetStart(long[] partOffsetStart) {
		this.partOffsetStart = partOffsetStart;
		return this;
	}


	public long[] getPartLength() {
		return partLength;
	}


	public DownloadTaskInfo setPartLength(long[] partLength) {
		this.partLength = partLength;
		return this;
	}


	public long[] getPartDownloadLength() {
		return partDownloadLength;
	}


	public DownloadTaskInfo setPartDownloadLength(long[] partDownloadLength) {
		this.partDownloadLength = partDownloadLength;
		return this;
	}


	public int getPriority() {
		return priority;
	}


	public DownloadTaskInfo setPriority(int priority) {
		this.priority = priority;
		return this;
	}


	public String getPath() {
		return path;
	}


	public DownloadTaskInfo setPath(String path) {
		this.path = path;
		return this;
	}


	public static abstract class Factory {

		public final static String DOWNLOAD_INFO_EXT = ".dti";

		protected static DownloadTaskInfo info;

		protected static DataWriter dataWriter;

		protected static DataReader dataReader;


		public static void save(DownloadTaskInfo _info) throws IOException {
			info = _info;
			dataWriter = new DataWriter(new File(info.getPath(), info.getName()));
			dataWriter.write(DownloadTaskInfo.FILE_FORMAT_INFO);
			dataWriter.writeLong(info.getLength());
			dataWriter.writeLong(info.getDownloadLength());
			dataWriter.writeLong(info.getStartTime());
			dataWriter.writeLong(info.getUsedTime());
			dataWriter.writeInt(info.getTotalThreads());

			int parts = info.getTotalThreads();
			for (int i = 0; i < parts; i++) {
				dataWriter.writeLong(info.getPartOffsetStart()[i]);
				dataWriter.writeLong(info.getPartLength()[i]);
				dataWriter.writeLong(info.getPartDownloadLength()[i]);
			}

			close();
		}


		public static DownloadTaskInfo from(File file) throws IOException {
			dataReader = new DataReader(new FileInputStream(file));
			info.setPath(file.getPath());
			info = new DownloadTaskInfo();
			if (!Arrays.equals(dataReader.read(DownloadTaskInfo.FILE_FORMAT_INFO.length), DownloadTaskInfo.FILE_FORMAT_INFO)) {
				throw new FileFormatException();
			}

			info.setLength(dataReader.readLong());
			info.setDownloadLength(dataReader.readLong());
			info.setStartTime(dataReader.readLong());
			info.setUsedTime(dataReader.readLong());
			info.setTotalThreads(dataReader.readInt());

			int parts = info.getTotalThreads();
			long[]  partOffsetStarts = new long[parts],
					partLengths = new long[parts],
					partDownloadLengths = new long[parts];

			for (int i = 0; i < parts; i++) {
				partOffsetStarts[i] = dataReader.readLong();
				partLengths[i] = dataReader.readLong();
				partDownloadLengths[i] = dataReader.readLong();
			}

			info.setPartOffsetStart(partOffsetStarts);
			info.setPartLength(partLengths);
			info.setPartDownloadLength(partDownloadLengths);

			close();
			return info;
		}


		private static void close() throws IOException {
			if (dataWriter != null) {
				dataWriter.close();
				dataWriter = null;
			}

			if (dataReader != null) {
				dataReader.close();
				dataReader = null;
			}
		}
	}
}
