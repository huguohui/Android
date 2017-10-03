package com.downloader.engine.downloader;

import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.AsyncWorker;
import com.downloader.engine.ControlableWorker;
import com.downloader.engine.Workable;
import com.downloader.io.AbstractFileWriter;
import com.downloader.io.ConcurrentFileWriter;
import com.downloader.io.FileWritable;
import com.downloader.manager.ThreadManager;
import com.downloader.net.AbstractRequest;
import com.downloader.net.Receiver;
import com.downloader.net.http.Http;
import com.downloader.net.http.HttpReceiver;
import com.downloader.net.http.HttpRequest;
import com.downloader.net.http.HttpResponse;
import com.downloader.util.Log;
import com.downloader.util.StringUtil;
import com.downloader.util.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static android.R.attr.data;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader implements Workable, Receiver.OnFinishedListener,
		Receiver.OnReceiveListener {
	protected URL url;

	protected String fileName;

	protected String contentType;

	protected HttpResponse httpResponse;

	protected HttpRequest[] httpRequests;

	protected HttpResponse[] httpResponses;

	protected HttpReceiver[] httpReceivers;

	protected AbstractFileWriter fileWriter;

	protected ThreadManager threadManager;

	protected int finished = 0;

	protected long blockSize;

	protected int specialThreads = 0;

	protected ControlableWorker worker;

	protected boolean isChunked;

	protected boolean isResumeFromInfo;

	protected int perThreadExecInterval = 500;

	protected DownloadTaskInfo info = new DownloadTaskInfo();

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


	public HttpDownloader(URL url) {
		super();
		init(url);
	}


	public HttpDownloader(HttpResponse hr) {
		this(hr.getURL());
		httpResponse = hr;
	}


	public HttpDownloader(DownloadTaskInfo info) {
		this(info.getUrl());
		initWithInfo(info);
	}


	public HttpDownloader(DownloadTaskDescriptor desc) {
		this(desc.getUrl());
		initWithDescriptor(desc);
	}


	protected void initWithDescriptor(DownloadTaskDescriptor desc) {
		specialThreads = desc.getMaxThread();
		path = desc.getPath();

		if (specialThreads != 0) {
			downloadThreads = specialThreads;
		}
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


	protected void init(URL url) {
		this.url = url;
		threadManager = ThreadManager.getInstance();
		worker = new AsyncWorker(threadManager, perThreadExecInterval);
		worker.add(this);
	}


	protected HttpRequest buildHttpRequest(URL url, Http.Method method, boolean hasLis) throws IOException {
		HttpRequest hr = new HttpRequest();
		hr.setUrl(url);
		hr.setMethod(method);
		hr.open();
		return hr;
	}


	protected void fetchInfo() throws IOException {
		if (isResumeFromInfo) {
			return;
		}

		if (httpResponse == null) {
			HttpRequest hr = buildHttpRequest(url, Http.Method.HEAD, false);
			hr.send();
			httpResponse = hr.response();
		}

		url = httpResponse.getURL();
		mLength = httpResponse.getContentLength();
		fileName = httpResponse.getFileName();
		contentType = httpResponse.getContentType();
		isChunked = httpResponse.isChunked();

		info.setLength(mLength);
		info.setName(fileName);
		info.setUrl(url);
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
		httpRequests = new HttpRequest[downloadThreads];
		httpResponses = new HttpResponse[downloadThreads];
		httpReceivers = new HttpReceiver[downloadThreads];
		fileWriter = new ConcurrentFileWriter(new File(path, fileName), mLength);
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
			httpRequests[i] = buildHttpRequest(url, Http.Method.GET, true);
			if (!isChunked) {
				httpRequests[i].setHeader(Http.RANGE, new AbstractRequest.Range(info.getPartOffsetStart()[i],
						info.getPartOffsetStart()[i] + info.getPartLength()[i] - info.getPartDownloadLength()[i]).toString());
			}

			httpRequests[i].send();
			httpReceivers[i] = new HttpReceiver(httpRequests[i].response(), fileWriter, worker,
										info.getPartOffsetStart()[i] + info.getPartDownloadLength()[i]);
			httpReceivers[i].setOnFinishedListener(this);
			httpReceivers[i].receive();
		}

		setState(State.downloading);
	}


	protected void finishDownload() throws Exception {
		setIsFinished(true);
		fileWriter.close();
		worker.stop();
	}


	protected boolean checkFinished() {
		return ++finished == downloadThreads && (mLength == 0 || mLength == mDownloadedLength);
	}


	public void start() throws IOException {
		try {
			if (!isResumeFromInfo) {
				info.setStartTime(TimeUtil.getMillisTime());
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


	public void resume() throws IOException {
		super.resume();
		info = DownloadTaskInfo.Factory.from(new File(path, getFileName()));
		start();
	}


	public void stop() throws IOException {
		DownloadTaskInfo.Factory.save((DownloadTaskInfo) getInfo());
		super.stop();
	}


	@Override
	public synchronized void onFinished(Receiver r) {
		mDownloadedLength += ((HttpReceiver) r).getReceivedLength();
		if (checkFinished()) {
			mFinishedTime = System.currentTimeMillis();
			mDownloadTime = mFinishedTime - mStartTime;
			Log.println("Download time: " + StringUtil.decimal2Str((double) mDownloadTime / 1000, 2));
			if (isChunked) {
				info.setProgress(1.0f);
			}

			try {
				finishDownload();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		fetchInfo();
		prepare();
		download();
	}


	public AbstractTaskInfo getInfo() {
		long length = 0;
		long[] receivedLength = new long[downloadThreads];
		if (State.downloading.equals(mState)) {
			for (int i = 0; i < downloadThreads; i++) {
				receivedLength[i] = httpReceivers[i].getReceivedLength();
				length += receivedLength[i];
			}
			info.setPartDownloadLength(receivedLength);
			info.setDownloadLength(length);
			if (!isChunked) {
				info.setProgress(length == 0 || mLength == 0 ? 0 : (float) length / mLength);
			}
		}

		return info;
	}


	@Override
	public synchronized void onReceive(Receiver r, byte[] data) {
		Log.println(data.length);
	}


	public URL getUrl() {
		return url;
	}


	public String getFileName() {
		return fileName;
	}


	public String getContentType() {
		return contentType;
	}


	public HttpResponse getHttpResponse() {
		return httpResponse;
	}


	public HttpRequest[] getHttpRequests() {
		return httpRequests;
	}


	public HttpResponse[] getHttpResponses() {
		return httpResponses;
	}


	public HttpReceiver[] getHttpReceivers() {
		return httpReceivers;
	}


	public FileWritable getFileWriter() {
		return fileWriter;
	}


	public int getFinished() {
		return finished;
	}


	public long getBlockSize() {
		return blockSize;
	}


	public ControlableWorker getWorker() {
		return worker;
	}
}
