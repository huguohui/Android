package com.downloader.http;

import com.downloader.base.AbstractDownloader;
import com.downloader.base.AbstractRequest;
import com.downloader.base.Receiver;
import com.downloader.manager.ThreadManager;
import com.downloader.util.ConcurrentFileWriter;
import com.downloader.util.DateUtil;
import com.downloader.util.Log;
import com.downloader.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader implements HttpReceiver.OnFinishedListener {
	protected URL url;

	protected HttpResponse httpResponse;

	protected String fileName;

	protected String contentType;

	protected HttpRequest[] httpRequests;

	protected HttpResponse[] httpResponses;

	protected HttpReceiver[] httpReceivers;

	protected Thread[] recThreads;

	protected ConcurrentFileWriter fileWriter;

	protected int allocThreads = 1;

	protected int finished = 0;

	protected long blockSize;

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


	public HttpDownloader(URL url) throws NullPointerException, IOException {
		this.url = url;
		fetchInfo();
	}


	public HttpDownloader(HttpResponse hr) throws NullPointerException, IOException {
		httpResponse = hr;
		fetchInfo();
	}


	/**
	 * Fetch info from given url.
	 * @throws IOException
	 */
	protected void fetchInfo() throws IOException {
		if (httpResponse == null) {
			HttpRequest hr = buildHttpRequest(url, Http.Method.HEAD);
			hr.getHeader().add(Http.RANGE, new AbstractRequest.Range(0).toString());
			httpResponse = hr.response();
		}

		mLength = httpResponse.getContentLength();
		fileName = URLDecoder.decode(httpResponse.getFileName(), "UTF-8");
		contentType = httpResponse.getContentType();
		prepare();
	}


	protected HttpRequest buildHttpRequest(URL url, Http.Method method) throws IOException {
		HttpRequest hr = new HttpRequest(url, method);
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
		recThreads = new Thread[allocThreads];
		fileWriter = new ConcurrentFileWriter(
							new File(URLDecoder.decode(httpResponse.getFileName(), "UTF-8")),
									httpResponse.getContentLength());
	}


	protected Thread allocThread(int id) throws IOException {
		ThreadManager.ThreadDescriptor desc = new ThreadManager.ThreadDescriptor(new Runnable() {
			@Override
			public void run() {
				try {
					httpReceivers[Integer.parseInt(Thread.currentThread().getName())].receive();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, id + "");

		return ThreadManager.getInstance().create(desc);
	}


	protected void download() throws IOException {
		for (int i = 0; i < allocThreads; i++) {
			httpRequests[i] = buildHttpRequest(url, Http.Method.GET);
			httpRequests[i].getHeader().add(Http.RANGE,
					new AbstractRequest.Range(i * blockSize,
							-~i == allocThreads ? mLength : ~-(-~i * blockSize)).toString());
			httpResponses[i] = httpRequests[i].response();
			httpReceivers[i] = new HttpReceiver(httpResponses[i], fileWriter, i * blockSize);
			httpReceivers[i].setOnFinishedListener(this);
			recThreads[i] = allocThread(i);
		}
		for (Thread t : recThreads) {
			t.start();
		}
	}


	public void start() throws IOException {
		super.start();
		download();
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

			Log.println("Download time: " + mFinishedTime + "," + mStartTime + "," +
					StringUtil.decimal2Str((double)DateUtil.getMillisTimeDiffInSec(mStartTime, mFinishedTime), 2));
		}
	}
}
