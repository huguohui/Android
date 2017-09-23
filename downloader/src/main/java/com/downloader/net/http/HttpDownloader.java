package com.downloader.net.http;

import com.downloader.engine.AsyncWorker;
import com.downloader.engine.ControlableWorker;
import com.downloader.engine.Workable;
import com.downloader.io.ConcurrentFileWriter;
import com.downloader.manager.ThreadManager;
import com.downloader.net.AbstractDownloader;
import com.downloader.net.AbstractRequest;
import com.downloader.net.Receiver;
import com.downloader.net.Response;
import com.downloader.util.Log;
import com.downloader.util.StringUtil;
import com.downloader.util.TimeUtil;
import com.downloader.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader implements Workable, HttpReceiver.OnFinishedListener,
		HttpRequest.OnConnectedListener, HttpRequest.OnSendListener, HttpRequest.OnResponseListener {
	protected URL url;

	protected HttpResponse httpResponse;

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
		this.url = url;

		try {
			init();
			fetchInfo();
			prepare();
		} catch(RedirectException re) {
			re.printStackTrace();
		} catch(IOException ie) {
			ie.printStackTrace();
		}
	}


	public HttpDownloader(HttpResponse hr) {
		this(hr.getURL());
	}


	protected void init() throws IOException {
		threadManager = ThreadManager.getInstance();
		worker = new AsyncWorker(threadManager);
	}


	/**
	 * Fetch info from given url.
	 * @throws IOException
	 */
	protected void fetchInfo() throws IOException {
		if (httpResponse == null) {
			HttpRequest hr = buildHttpRequest(url, Http.Method.HEAD);
			hr.setHeader(Http.RANGE, new AbstractRequest.Range(0).toString());
			hr.send();
			httpResponse = hr.response();
		}

		mLength = httpResponse.getContentLength();
		fileName = URLDecoder.decode(httpResponse.getFileName(), "UTF-8");
		contentType = httpResponse.getContentType();
	}


	protected HttpRequest buildHttpRequest(URL url, Http.Method method) throws IOException {
		HttpRequest hr = new HttpRequest();
		hr.setUrl(url);
		hr.setMethod(method);
		hr.setOnConnectedListener(this);
		hr.setOnSendListener(this);
		hr.setOnResponseListener(this);
		hr.open();
		return hr;
	}


	protected void prepare() throws IOException {
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
		fileWriter = new ConcurrentFileWriter(
				new File(UrlUtil.decode(httpResponse.getFileName(), "UTF-8")),  httpResponse.getContentLength());
	}


	protected void download() throws IOException {
		for (int i = 0; i < allocThreads; i++) {
			httpRequests[i] = buildHttpRequest(url, Http.Method.GET);
			httpRequests[i].setHeader(Http.RANGE,
					new AbstractRequest.Range(i * blockSize,
							-~i == allocThreads ? mLength : ~-(-~i * blockSize)).toString());
			httpRequests[i].send();
			httpResponses[i] = httpRequests[i].response();
			httpReceivers[i] = new HttpReceiver(httpResponses[i], fileWriter, null, i * blockSize);
			httpReceivers[i].setOnFinishedListener(this);
			worker.add(httpReceivers[i]);
		}
	}


	public void start() throws IOException {
		super.start();
		worker.add(this);

		try {
			worker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void pause() throws IOException {
		super.pause();
	}


	public void stop() throws IOException {
		super.stop();
	}


	@Override
	public void onFinished(Receiver r) {
		if (++finished == allocThreads) {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			mFinishedTime = System.currentTimeMillis();
			mDownloadTime = mFinishedTime - mStartTime;

			Log.println("Download time: " + StringUtil.decimal2Str(
					(double) TimeUtil.getMillisTimeDiffInSec(mStartTime, mFinishedTime), 2));

			try {
				worker.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		download();
	}


	@Override
	public void onConnected(AbstractRequest r) {
		HttpRequest hr = (HttpRequest) r;
	}


	@Override
	public void onSend(AbstractRequest r) {
		HttpRequest hr = (HttpRequest) r;

	}


	@Override
	public void onResponse(Response r) {
		HttpResponse hp = (HttpResponse) r;
	}
}
