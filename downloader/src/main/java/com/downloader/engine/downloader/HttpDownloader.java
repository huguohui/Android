package com.downloader.engine.downloader;

import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.AsyncWorker;
import com.downloader.engine.ControlableWorker;
import com.downloader.engine.Workable;
import com.downloader.io.ConcurrentFileWriter;
import com.downloader.io.FileWritable;
import com.downloader.manager.ThreadManager;
import com.downloader.net.AbstractRequest;
import com.downloader.net.Receiver;
import com.downloader.net.Response;
import com.downloader.net.http.Http;
import com.downloader.net.http.HttpReceiver;
import com.downloader.net.http.HttpRequest;
import com.downloader.net.http.HttpResponse;
import com.downloader.util.Log;
import com.downloader.util.StringUtil;
import com.downloader.util.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader implements Workable, Receiver.OnFinishedListener,
		AbstractRequest.OnConnectedListener, AbstractRequest.OnSendListener, AbstractRequest.OnResponseListener,
		Receiver.OnReceiveListener {
	protected URL url;

	protected String fileName;

	protected String contentType;

	protected HttpResponse httpResponse;

	protected HttpRequest[] httpRequests;

	protected HttpResponse[] httpResponses;

	protected HttpReceiver[] httpReceivers;

	protected ConcurrentFileWriter fileWriter;

	protected ThreadManager threadManager;

	protected int finished = 0;

	protected long blockSize;

	protected ControlableWorker worker;

	protected boolean isChunked;

	protected boolean isResumeFromInfo;

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
		this.url = url;
		init();
	}


	public HttpDownloader(HttpResponse hr) {
		this(hr.getURL());
		httpResponse = hr;
	}


	public HttpDownloader(DownloadTaskInfo info) {
		this(info.getUrl());
		this.info = info;
		mLength = info.getLength();
		mDownloadedLength = info.getDownloadLength();
		totalThreads = info.getTotalThreads();
		mStartTime = info.getStartTime();
		mDownloadTime = info.getUsedTime();
		isResumeFromInfo = true;
	}


	protected void init() {
		threadManager = ThreadManager.getInstance();
		worker = new AsyncWorker(threadManager);
		worker.add(this);
	}


	protected HttpRequest buildHttpRequest(URL url, Http.Method method, boolean hasLis) throws IOException {
		HttpRequest hr = new HttpRequest();
		hr.setUrl(url);
		hr.setMethod(method);
		if (hasLis) {
			hr.setOnConnectedListener(this);
			hr.setOnSendListener(this);
			hr.setOnResponseListener(this);
		}

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

		mLength = httpResponse.getContentLength();
		fileName = httpResponse.getFileName();
		contentType = httpResponse.getContentType();
		isChunked = httpResponse.isChunked();
		url = httpResponse.getURL();
		info.setLength(mLength);
		info.setName(fileName);
		info.setUrl(url);
	}


	protected void prepare() throws IOException {
		setState(State.preparing);
		for (int i = 0; i < SIZE_LEVELS.length; i++) {
			if (SIZE_LEVELS[i] > mLength) {
				totalThreads = ALLOW_THREAD_LEVELS[i];
				info.setTotalThreads(totalThreads);
				break;
			}
		}

		blockSize = mLength / totalThreads;
		httpRequests = new HttpRequest[totalThreads];
		httpResponses = new HttpResponse[totalThreads];
		httpReceivers = new HttpReceiver[totalThreads];
		fileWriter = new ConcurrentFileWriter(new File(fileName), mLength);
	}


	protected void computeAlloc() {
		if (isResumeFromInfo) {
			return;
		}

		long[] partOffsetEnd = new long[totalThreads],
				partOffsetStart = new long[totalThreads],
				partLength = new long[totalThreads];

		for (int i = 0; i < totalThreads; i++) {
			partOffsetStart[i] = i * blockSize;
			partOffsetEnd[i] = -~i == totalThreads ? mLength : ~-(-~i * blockSize);
			partLength[i] = partOffsetEnd[i] - partOffsetStart[i];
		}

		info.setPartLength(partLength);
		info.setPartOffsetStart(partOffsetStart);
		info.setPartDownloadLength(new long[] { 0, 0, 0 });
	}


	protected void download() throws Exception {
		computeAlloc();
		for (int i = 0; i < totalThreads; i++) {
			httpRequests[i] = buildHttpRequest(url, Http.Method.GET, true);
			if (!isChunked) {
				httpRequests[i].setHeader(Http.RANGE, new AbstractRequest.Range(info.getPartOffsetStart()[i],
						info.getPartLength()[i] - info.getPartDownloadLength()[i]).toString());
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
		return ++finished == totalThreads && (mLength == 0 || mLength == mDownloadedLength);
	}


	public void start() throws IOException {
		try {
			info.setStartTime(info.getStartTime() != 0 ? info.getStartTime() : TimeUtil.getMillisTime());
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
		info = new DownloadInfoRecoder(new File(path, getFileName())).read();
		start();
	}


	public void stop() throws IOException {
		new DownloadInfoRecoder((DownloadTaskInfo) getInfo()).write();
		super.stop();
	}


	@Override
	public void onReceive(byte[] data) {

	}


	@Override
	public synchronized void onFinished(Receiver r) {
		mDownloadedLength += ((HttpReceiver) r).getReceivedLength();
		if (checkFinished()) {
			mFinishedTime = System.currentTimeMillis();
			mDownloadTime = mFinishedTime - mStartTime;
			Log.println("Download time: " + StringUtil.decimal2Str(
					(double) TimeUtil.getMillisTimeDiffInSec(mStartTime, mFinishedTime), 2));

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


	@Override
	public void onResponse(Response r) {
	}


	@Override
	public void onConnected(AbstractRequest r) {
	}


	@Override
	public void onSend(AbstractRequest r) {
	}


	public AbstractTaskInfo getInfo() {
		long[] receivedLength = new long[totalThreads];
		mDownloadedLength = 0;
		for (int i = 0; i < totalThreads; i++) {
			receivedLength[i] = httpReceivers[i].getReceivedLength();
			mDownloadedLength += receivedLength[i];
		}

		info.setDownloadLength(mDownloadedLength);
		return info;
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
