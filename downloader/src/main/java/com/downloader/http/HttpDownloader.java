package com.downloader.http;

import com.downloader.base.AbstractDownloader;
import com.downloader.base.AbstractTaskInfo;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader {
	/**
	 * Prepare to start receiving data.
	 * @throws IOException
	 */
	private void readResponse() throws IOException {
		HttpRequest request = (HttpRequest) null;
		if (!request.isSend())
			return;

//		getHeader().setContent(request.getSocket().getInputStream());
//		isChunked = CHUNKED.equals(getHeader().get(Http.TRANSFER_ENCODING));
//		isGzip = "Gzip".equals(getHeader().get(Http.CONTENT_ENCODING));
//		String fileName = UrlUtil.getFilename(request.getUrl()), disp = "";
//		if ((disp = getHeader().get(Http.CONTENT_DISPOSITION)) != null && disp.length() != 0) {
//			int off = 0;
//			if ((off = disp.indexOf("filename")) != -1)
//				fileName = disp.substring(off + 1);
//		}
//
//		setFileName(fileName);
//		if (getHeader().get(Http.LOCATION) != null) {
//			request.reopen(UrlUtil.getFullUrl(request.getUrl(), getHeader().get(Http.LOCATION)));
//			sendRequest();
//		}
//
//		if (getHeader().get(Http.CONTENT_LENGTH) != null)
//			setLength(Long.parseLong(getHeader().get(Http.CONTENT_LENGTH)));
//		else if (getHeader().get(Http.CONTENT_RANGE) != null) {
//			setLength(Long.parseLong(getHeader().get(Http.CONTENT_RANGE).split("/")[1]));
//		}

		setReceivedLength(0);
	}





	/**
	 * Start to receiving data.
	 */
	public synchronized void startReceive() throws Throwable {
//		ThreadManager.ThreadDescriptor td = new ThreadManager.ThreadDescriptor(this, "");
//		setThread(ThreadManager.getInstance().create(td));
//		getThread().start();
	}


	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public AbstractTaskInfo info() {
		return null;
	}
}
