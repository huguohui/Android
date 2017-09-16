package com.downloader.http;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import com.downloader.base.AbstractDownloader;
import com.downloader.base.AbstractReceiver;
import com.downloader.base.AbstractRequest;
import com.downloader.base.Receiver;
import com.downloader.manager.ThreadManager;
import com.downloader.util.ConcurrentFileWriter;
import com.downloader.util.Log;

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

	protected Thread[] threads;

	protected ConcurrentFileWriter fileWriter;

	protected int allowThreads = 1;

	protected int finished = 0;

	final public static int MAX_THREAD = 10;

	final public static long[] SIZE_LEVELS = {
			1024 * 1024,
			1024 * 1024 * 1024,
			1024 * 1024 * 1024,
	};

	final public static int[] ALLOW_THREAD_LEVELS = {
		3, 4, 10
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
		long blockSize = 0;

		for (int i = 0; i < SIZE_LEVELS.length; i++) {
			if (SIZE_LEVELS[i] > mLength) {
				allowThreads = ALLOW_THREAD_LEVELS[i];
				break;
			}
		}

		blockSize = mLength / allowThreads;
		httpRequests = new HttpRequest[allowThreads];
		httpResponses = new HttpResponse[allowThreads];
		httpReceivers = new HttpReceiver[allowThreads];
		threads = new Thread[allowThreads];
		fileWriter = new ConcurrentFileWriter(
							new File(URLDecoder.decode(httpResponse.getFileName(), "UTF-8")),
								httpResponse.getContentLength());

		for (int i = 0; i < allowThreads; i++) {
			httpRequests[i] = buildHttpRequest(url, Http.Method.GET);
			httpRequests[i].getHeader().add(Http.RANGE,
					new AbstractRequest.Range(i * blockSize,
							-~i == allowThreads ? mLength : ~-(-~i * blockSize)).toString());
			httpResponses[i] = httpRequests[i].response();
			httpReceivers[i] = new HttpReceiver(httpResponses[i], fileWriter, i * blockSize);
			httpReceivers[i].setOnFinishedListener(this);
			threads[i] = assocThread(i);
		}
	}


	protected Thread assocThread(int id) throws IOException {
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
		for (Thread t : threads) {
			t.start();
		}
	}


	public void start() throws IOException {
		download();
	}


	public void pause() throws IOException {

	}


	public void stop() throws IOException {

	}


	@Override
	public void onFinished(Receiver r) {
		mDownloadedLength += ((HttpReceiver) r).getReceivedLength();
		Log.println("downloaded:" + mDownloadedLength);
		Log.println("total length:" + mLength);

		if (++finished == allowThreads) {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
