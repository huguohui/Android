package com.downloader.client.downloader;

import com.downloader.client.Context;
import com.downloader.engine.AsyncWorker;
import com.downloader.engine.ControlableWorker;
import com.downloader.engine.Workable;
import com.downloader.io.ConcurrentFileWriter;
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
import java.io.IOException;
import java.net.URL;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader implements Workable, Receiver.OnFinishedListener,
		AbstractRequest.OnConnectedListener, AbstractRequest.OnSendListener, AbstractRequest.OnResponseListener {
	protected URL url;

	protected String fileName;

	protected String contentType;

	protected HttpRequest[] httpRequests;

	protected HttpResponse[] httpResponses;

	protected HttpReceiver[] httpReceivers;

	protected ConcurrentFileWriter fileWriter;

	protected ThreadManager threadManager;

	protected int allocThreads = 1;

	protected int finished = 0;

	protected long blockSize;

	protected ControlableWorker worker;

	protected boolean isChunked;

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


	public HttpDownloader(Context context, URL url) {
		super();
		this.url = url;
		init();
	}


	public HttpDownloader(Context context, HttpResponse hr) {
		this(context, hr.getURL());
	}


	protected void init() {
		threadManager = ThreadManager.getInstance();
		worker = new AsyncWorker(threadManager);
		worker.add(this);
	}


	/**
	 * Fetch info from given url.
	 * @throws IOException
	 */
	protected void fetchInfo() throws IOException {
		HttpRequest httpRequest;
		HttpResponse httpResponse;

		httpRequest = buildHttpRequest(url, Http.Method.HEAD, true);
		httpRequest.setHeader(Http.RANGE, new AbstractRequest.Range(0).toString());
		httpRequest.send();
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


	protected void prepare(HttpResponse res) throws IOException {
		mLength = res.getContentLength();
		fileName = res.getFileName();
		contentType = res.getContentType();
		isChunked = res.isChunked();

		for (int i = 0; i < SIZE_LEVELS.length; i++) {
			if (SIZE_LEVELS[i] > mLength) {
				allocThreads = ALLOW_THREAD_LEVELS[i];
				break;
			}
		}

		blockSize = mLength / allocThreads;
		httpRequests = new HttpRequest[allocThreads];
		httpResponses = new HttpResponse[allocThreads];
		httpReceivers = new HttpReceiver[allocThreads];
		fileWriter = new ConcurrentFileWriter(new File(fileName), mLength);
		setState(State.prepared);
	}


	protected void download() throws Exception {
		super.start();
		for (int i = 0; i < allocThreads; i++) {
			httpRequests[i] = buildHttpRequest(url, Http.Method.GET, true);
			if (!isChunked) {
				httpRequests[i].setHeader(Http.RANGE,
						new AbstractRequest.Range(i * blockSize,
								-~i == allocThreads ? mLength : ~-(-~i * blockSize)).toString());
			}

			httpRequests[i].send();
			httpReceivers[i] = new HttpReceiver(httpRequests[i].response(), fileWriter, worker, i * blockSize);
			httpReceivers[i].setOnFinishedListener(this);
			httpReceivers[i].receive();
		}
	}


	protected void finishDownload() throws Exception {
		setIsFinished(true);
		fileWriter.close();
		worker.stop();
	}


	protected boolean checkFinished() {
		return ++finished == allocThreads && (mLength == 0 || mLength == mDownloadedLength);
	}


	public void start() throws IOException {
		try {
			worker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void pause() throws Exception {
		super.pause();
		synchronized (this) {
			try { wait(); } catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void resume() {
		synchronized (this) {
			notify();
			super.resume();
		}
	}


	public void stop() {
		super.stop();
	}


	@Override
	public void onFinished(Receiver r) {
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
		download();
	}


	@Override
	public void onResponse(Response r) {
		HttpResponse httpResponse = (HttpResponse) r;
		switch(getState()) {
			case init:
				try { prepare(httpResponse); } catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}


	@Override
	public void onConnected(AbstractRequest r) {
	}


	@Override
	public void onSend(AbstractRequest r) {
	}
}
