package com.downloader.http;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.downloader.base.AbstractReceiver.Range;
import com.downloader.base.DownloadTask;
import com.downloader.http.Http.Method;
import com.downloader.util.StringUtil;

/**
 * Task for http download.
 * @since 2017/01/15
 */
public class HttpDownloader extends DownloadTask {
	/** Thread number for downlaoding task. */
	public final static int DOWNLOAD_THREADS = 3;
	
	/** HttpReceivers for downloading task. */
	private HttpReceiver[] mReceivers = new HttpReceiver[DOWNLOAD_THREADS];


	/**
	 * Constructor a http download task by url.
	 * @param url The url of download task.
	 * @throws IOException 
	 */
	public HttpDownloader(URL url) throws IOException {
		super(url);
		fetchTaskInfo();
		prepare();
	}
	
	
	/**
	 * Checks the server "resume from break-point" support.
	 * @return true for support, false for not support.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private void fetchTaskInfo() throws IOException {
		HttpRequest r = new HttpRequest(getUrl(), Method.HEAD);
		r.getHeader().add(Http.RANGE, "bytes=0-1");
		r.send();
		HttpReceiver hr = r.getReceiver();
		HttpHeader header = hr.getHeader();

		setBreakPointResume(header != null ? "206".equals(header.getStatusCode()) : false);
		setContentType(header.get(Http.CONTENT_TYPE));
		setName(hr.getFileName());
		if (isBreakPointResume())
			setLength(StringUtil.str2Long(header.get(Http.CONTENT_RANGE).split("/")[1], 0L));
		else if (header.get(Http.CONTENT_LENGTH) != null)
			setLength(StringUtil.str2Long(header.get(Http.CONTENT_LENGTH), 0L));
	}
	
	
	/**
	 * Computes how to doing download task. 
	 * @throws IOException 
	 */
	private void prepare() throws IOException {
		long len = getLength(), avgLen = len / DOWNLOAD_THREADS;
		int remain = (int) len % DOWNLOAD_THREADS;
		boolean breakPoint = isBreakPointResume();

		if (breakPoint) {
			for (int i = 0; i < DOWNLOAD_THREADS; i++) {
				Range r = new Range(avgLen * i, avgLen * (i + 1) + (i + 1 == DOWNLOAD_THREADS ? remain : 0));
				mReceivers[i] = new HttpReceiver(new HttpRequest(getUrl()), r);
				mReceivers[i].setSaveTo(new FileOutputStream(String.format("D:\\%s.td%d", getName(), i)));
			}
		}else{
			mReceivers[0] = new HttpReceiver(new HttpRequest(getUrl()));
			mReceivers[0].setSaveTo(new FileOutputStream(String.format("D:\\%s", getName())));
		}
	}
	
	
	/**
	 * To changing task state.
	 * @param state State will change to.
	 */
	public void changeState() {
		
	}

	
	/**
	 * Do http download task.
	 */
	@Override
	public void doWork() {
		for (HttpReceiver hr : mReceivers) {
			if (hr != null) {
				try { hr.start(); } catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void start() throws Exception {
		doWork();
	}

	@Override
	public void pause() throws Exception {
		for (HttpReceiver hr : mReceivers) {
			if (hr != null) {
				try { hr.pause(); } catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void resume() throws Exception {
		for (HttpReceiver hr : mReceivers) {
			if (hr != null) {
				try { hr.resume(); } catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void stop() throws Exception {
		for (HttpReceiver hr : mReceivers) {
			if (hr != null) {
				try { hr.stop(); } catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public int progress() {
		long recvLen = 0;
		for (HttpReceiver hr : mReceivers) {
			if (hr != null)
				recvLen += hr.getReceivedLength();
		}
		
		return (int) (((float)recvLen / getLength()) * 100);
	}
}
