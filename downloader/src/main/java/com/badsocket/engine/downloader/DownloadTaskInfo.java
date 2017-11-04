package com.badsocket.engine.downloader;

import com.badsocket.engine.TaskInfo;
import com.badsocket.net.SocketReceiver;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.SocketResponse;
import com.badsocket.net.WebAddress;
import com.badsocket.util.TimeUtil;
import com.badsocket.util.UrlUtil;

import java.io.IOException;

/**
 * Information for download task.
 */
public abstract class DownloadTaskInfo extends TaskInfo {

	protected final static byte[] FILE_FORMAT_INFO = {'D', 'T', 'I'};

	public final static int MAX_NAME_LEN = 0xff;

	/** Url of downloading. */
	protected WebAddress address;

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


	public DownloadTaskInfo(DownloadDescriptor d) {
		address = d.getAddress();
		path = d.getPath();
		priority = d.getPriority();
		totalThreads = d.getMaxThread();
		startTime = TimeUtil.millisTime();
		name = UrlUtil.filename(d.getAddress().getUrl());
	}


	public DownloadTaskInfo() { }


	public abstract void update(SocketResponse r);


	public abstract void update(SocketReceiver[] rs);


	public abstract void update(SocketRequest[] rs);


	public abstract Storage storage();


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


	public WebAddress getAddress() {
		return address;
	}


	public void setAddress(WebAddress mUrl) {
		address = mUrl;
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


	public interface Storage {


		void read(byte[] data) throws IOException;


		byte[] save() throws IOException;

	}
}
