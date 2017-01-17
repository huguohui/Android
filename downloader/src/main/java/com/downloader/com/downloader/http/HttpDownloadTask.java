package com.downloader.http;
import java.io.IOException;
import java.net.URL;

import com.downloader.DownloadTask;
import com.downloader.http.Http.Method;

/**
 * Task for http download.
 * @since 2017/01/15
 */
public class HttpDownloadTask extends DownloadTask {
	/**
	 * Constructor a http download task by url.
	 * @param url The url of download task.
	 * @throws IOException 
	 */
	public HttpDownloadTask(URL url) throws IOException {
		super(url);
		fetchTaskInfo();
		System.out.println("是否支持断点续传？" + isBreakPointResume());
		System.out.println("任务大小：" + getLength());
		System.out.println("内容类型" + getContentType());
		//setReceiver(new HttpReceiver(new HttpRequest(url)));
	}
	
	
	/**
	 * Checks the server "resume from break-point" support.
	 * @return true for support, false for not support.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void fetchTaskInfo() throws IOException {
		HttpRequest r = new HttpRequest(getUrl(), Method.HEAD);
		r.getHeader().add(Http.RANGE, "bytes=0-1");
		r.send();
		HttpReceiver hr = r.getReceiver();
		HttpHeader header = hr.getHeader();
		setBreakPointResume(header != null ? "206".equals(header.getStatusCode()) : false);
		setLength(Long.parseLong(header.get(Http.CONTENT_RANGE).split("/")[1]));
		setContentType(header.get(Http.CONTENT_TYPE));
	}

	
	/**
	 * Do http download task.
	 */
	@Override
	public void doWork() {
		
	}

	@Override
	public void start() throws Exception {
		
	}

	@Override
	public void pause() throws Exception {
		
	}

	@Override
	public void resume() throws Exception {
		
	}

	@Override
	public void stop() throws Exception {
		
	}


	@Override
	public int progress() {
		return 0;
	}
}
