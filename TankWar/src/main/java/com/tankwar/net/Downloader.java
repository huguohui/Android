package com.tankwar.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.tankwar.net.Requester.State;

/**
 * Download some data form a place.
 */
public abstract class Downloader implements Receiver {
	/** The requester object. */
	private Requester mRequester;

	/** The length of data. */
	private long mLength;

	/** The length of downloaded data. */
	private long mDownloadedLength;

	/** The file name of saving download. */
	private String mSaveTo;
	
	/** The download start time. */
	private long mStartTime;
	
	/** The download state. */
	private State mState = State.ready;
	
	/** The download finished time. */
	private long mFinishedTime;
	
	/** Download is finished? */
	private boolean mIsFinished = false;
	
	/** The download states. */
	public enum State {
		ready, downloading, paused, finished
	}

	public final static int BUFFER_SIZE = 1024 << 3;


	/**
	 * Construct a downloader by requester.
	 * @param requester A {@link Requester}.
	 */
	public Downloader(Requester requester) {
		mRequester = requester;
	}


	/**
	 * Start download data.
	 */
	public abstract void download() throws IOException;


	/**
	 * Start download from stream and save data to file.
	 * @param file Save to file.
	 */
	public void download(File file) throws IOException {
		download(file.getAbsolutePath());
	}


	/**
	 * Start download from stream and save data to file.
	 * @param file Save to file.
	 */
	public void download(String file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		InputStream is = getRequester().getSocket().getInputStream();
		byte[] buff;
		while((buff = receive(is, BUFFER_SIZE)) != null) {
			os.write(buff);
			mDownloadedLength += buff.length;
		}

		setState(State.finished);
		setIsFinished(true);
		os.flush();
		os.close();
		mRequester.close();
	}


	public Requester getRequester() {
		return mRequester;
	}

	public void setRequester(Requester requester) {
		mRequester = requester;
	}

	public long getLength() {
		return mLength;
	}

	public synchronized void setLength(long length) {
		mLength = length;
	}

	public long getDownloadedLength() {
		return mDownloadedLength;
	}

	public synchronized void setDownloadedLength(long downloadedLength) {
		mDownloadedLength = downloadedLength;
	}

	public String getSaveTo() {
		return mSaveTo;
	}

	public void setSaveTo(String saveTo) {
		mSaveTo = saveTo;
	}


	public long getStartTime() {
		return mStartTime;
	}


	public synchronized void setStartTime(long mStartTime) {
		this.mStartTime = mStartTime;
	}


	public State getState() {
		return mState;
	}


	public synchronized void setState(State mState) {
		this.mState = mState;
	}


	public long getFinishedTime() {
		return mFinishedTime;
	}


	public synchronized void setFinishedTime(long mFinishedTime) {
		this.mFinishedTime = mFinishedTime;
	}


	public boolean isFinished() {
		return mIsFinished;
	}


	public synchronized void setIsFinished(boolean mIsFinshed) {
		this.mIsFinished = mIsFinshed;
	}
}
