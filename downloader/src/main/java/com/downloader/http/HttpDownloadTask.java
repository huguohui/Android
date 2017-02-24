package com.downloader.http;

import com.downloader.base.AbstractDownloadTask;
import com.downloader.base.AbstractReceiver;
import com.downloader.base.TaskInfo;
import com.downloader.util.StringUtil;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Download task of http.
 */
public class HttpDownloadTask extends AbstractDownloadTask {
	/**
	 * Constructor for creating task with name.
	 *
	 * @param name Name of task.
	 */
	public HttpDownloadTask(String name) {
		super(name);
	}


	/**
	 * Checks the server "resume from break-point" support.
	 * @return true for support, false for not support.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void fetchTaskInfo() throws IOException {
		HttpRequest r = new HttpRequest(getUrl(), Http.Method.HEAD);
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
				AbstractReceiver.Range r = new AbstractReceiver.Range(avgLen * i, avgLen * (i + 1) + (i + 1 == DOWNLOAD_THREADS ? remain : 0));
				mReceivers[i] = new HttpReceiver(new HttpRequest(getUrl()), r);
				mReceivers[i].setSaveTo(new FileOutputStream(String.format("D:\\%s.td%d", getName(), i)));
			}
		}else{
			mReceivers[0] = new HttpReceiver(new HttpRequest(getUrl()));
			mReceivers[0].setSaveTo(new FileOutputStream(String.format("D:\\%s", getName())));
		}
	}


	/**
	 * Get the progress of task. value: 0 ~ 100
	 *
	 * @return Progress of task.
	 */
	@Override
	public int progress() {
		return 0;
	}

	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public TaskInfo info() {
		return null;
	}

	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {

	}

	/**
	 * Controls the task pause.
	 */
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

	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {

	}

	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {

	}
}
