package com.downloader.engine.downloader;

import com.downloader.engine.AsyncWorker;
import com.downloader.engine.TaskInfo;
import com.downloader.engine.Worker;
import com.downloader.io.writer.ConcurrentFileWriter;
import com.downloader.io.writer.Writer;
import com.downloader.manager.ThreadManager;
import com.downloader.net.AbstractSocketRequest;
import com.downloader.net.SocketReceiver;
import com.downloader.net.SocketRequest;
import com.downloader.net.SocketResponse;
import com.downloader.net.WebAddress;
import com.downloader.net.http.Http;
import com.downloader.net.http.HttpReceiver;
import com.downloader.util.Log;
import com.downloader.util.StringUtil;
import com.downloader.util.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Downloads data based http protocol.
 */
public class InternetDownloader extends AbstractDownloader {

	protected WebAddress address;

	protected SocketResponse response;

	protected SocketRequest[] requests;

	protected SocketResponse[] responses;

	protected SocketReceiver[] receivers;

	protected Writer fileWriter;

	protected ThreadManager threadManager;

	protected Worker worker;

	protected boolean isResumeFromInfo;

	protected int perThreadExecInterval = 500;

	protected int specialThreads;

	protected long blockSize;

	protected DownloadTaskInfo info;

	final public static int MAX_THREAD = 10;

	final public static long[] SIZE_LEVELS = {
			1024,
			1024 * 1024,
			1024 * 1024 * 1024,
			1024 * 1024 * 1024 * 1024
	};

	final public static int[] ALLOW_THREAD_LEVELS = {
		1, 4, 7, 10
	};


	public InternetDownloader(WebAddress address) {
		super();
		init(address);
	}


	public InternetDownloader(SocketResponse hr) {
		response = hr;
	}


	public InternetDownloader(DownloadTaskInfo info) {
		initWithInfo(info);
	}


	public InternetDownloader(DownloadTaskDescriptor desc) {
		initWithDescriptor(desc);
	}


	protected void initWithDescriptor(DownloadTaskDescriptor desc) {
	}


	protected void initWithInfo(DownloadTaskInfo info) {
		this.info = info;
		isResumeFromInfo = true;
		mLength = info.getLength();
		downloadThreads = info.getTotalThreads();
		mStartTime = info.getStartTime();
		mDownloadTime = info.getUsedTime();
		mDownloadedLength = info.getDownloadLength();
		path = info.getPath();
	}


	protected void init(WebAddress address) {
		this.address = address;
		threadManager = ThreadManager.getInstance();
		worker = new AsyncWorker(threadManager, perThreadExecInterval);
	}


	protected SocketRequest buildRequest(SocketRequest.RequestBuilder b) throws IOException {
		return b.build();
	}


	protected void fetchInfo() throws Exception {
		if (isResumeFromInfo) {
			return;
		}

		if (response == null) {
			SocketRequest hr = buildRequest(null);
			hr.send();
			response = hr.response();
		}
	}


	protected void prepare() throws IOException {
		setState(State.preparing);
		if (specialThreads == 0) {
			for (int i = 0; i < SIZE_LEVELS.length; i++) {
				if (SIZE_LEVELS[i] > mLength) {
					downloadThreads = ALLOW_THREAD_LEVELS[i];
					break;
				}
			}
		}

		blockSize = mLength / downloadThreads;
//		fileWriter = new ConcurrentFileWriter(new File(path, fileName), mLength);
		info.setTotalThreads(downloadThreads);
	}


	protected void computeAndAlloc() {
		if (isResumeFromInfo) {
			return;
		}

		long[] partOffsetEnd = new long[downloadThreads],
				partOffsetStart = new long[downloadThreads],
				partLength = new long[downloadThreads],
				partDownloadLen = new long[downloadThreads];

		for (int i = 0; i < downloadThreads; i++) {
			partOffsetStart[i] = i * blockSize;
			partOffsetEnd[i] = -~i == downloadThreads ? mLength : ~-(-~i * blockSize);
			partLength[i] = partOffsetEnd[i] - partOffsetStart[i];
		}

		Arrays.fill(partDownloadLen, 0);
		info.setPartLength(partLength);
		info.setPartOffsetStart(partOffsetStart);
		info.setPartDownloadLength(partDownloadLen);
	}


	protected void download() throws Exception {
		computeAndAlloc();
		for (int i = 0; i < downloadThreads; i++) {
			requests[i] = buildRequest(null);
//			httpRequests[i].setHeader(Http.RANGE, new AbstractSocketRequest.Range(info.getPartOffsetStart()[i],
//					info.getPartOffsetStart()[i] + info.getPartLength()[i] - info.getPartDownloadLength()[i]).toString());
//
//
//			httpRequests[i].send();
//			httpReceivers[i] = new HttpReceiver(httpRequests[i].response(), fileWriter, worker,
//										info.getPartOffsetStart()[i] + info.getPartDownloadLength()[i]);
//			httpReceivers[i].setOnFinishedListener(this);
//			httpReceivers[i].receive();
		}

		setState(State.downloading);
	}


	protected void finishDownload() throws Exception {
		setIsFinished(true);
		fileWriter.close();
		worker.stop();
	}


//	protected boolean checkFinished() {
//		return ++finished == downloadThreads && (mLength == 0 || mLength == mDownloadedLength);
//	}


	public void start() throws IOException {
		try {
			if (!isResumeFromInfo) {
				info.setStartTime(TimeUtil.millisTime());
				mStartTime = info.getStartTime();
			}
			worker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void pause() throws Exception {
		super.pause();
		stop();
	}


//	public void resume() throws IOException {
//		super.resume();
//		info = DownloadTaskInfo.Factory.from(new File(path, getFileName()));
//		start();
//	}
//
//
//	public void stop() throws IOException {
//		DownloadTaskInfo.Factory.save((DownloadTaskInfo) getInfo());
//		super.stop();
//	}
//
//
//	public synchronized void onFinished(SocketReceiver r) {
//		mDownloadedLength += ((HttpReceiver) r).getReceivedLength();
//		if (checkFinished()) {
//			mFinishedTime = System.currentTimeMillis();
//			mDownloadTime = mFinishedTime - mStartTime;
//			Log.println("Download time: " + StringUtil.decimal2Str((double) mDownloadTime / 1000, 2));
//			if (isChunked) {
//				info.setProgress(1.0f);
//			}
//
//			try {
//				finishDownload();
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//
//	/**
//	 * To do some work.
//	 */
//	public void work() throws Exception {
//		fetchInfo();
//		prepare();
//		download();
//	}
//
//
//	public TaskInfo getInfo() {
//		long length = 0;
//		long[] receivedLength = new long[downloadThreads];
//		if (State.downloading.equals(mState)) {
//			for (int i = 0; i < downloadThreads; i++) {
//				receivedLength[i] = httpReceivers[i].getReceivedLength();
//				length += receivedLength[i];
//			}
//			info.setPartDownloadLength(receivedLength);
//			info.setDownloadLength(length);
//			if (!isChunked) {
//				info.setProgress(length == 0 || mLength == 0 ? 0 : (float) length / mLength);
//			}
//		}
//
//		return info;
//	}


}
