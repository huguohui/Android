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
	 * Keep receving data from stream.
	 * @throws IOException If exception.
	 */
//	private void receiveAndSave() {
//		byte[] buff;
//		int remianLen = (int) (getLength() - getReceivedLength()),
//				receiveLen = BUFFER_SIZE;
//
//		try {
//			if (!isChunked) {
//				if (remianLen <= 0) {
//					setState(State.finished);
//					return;
//				}
//				receiveLen = Math.min(BUFFER_SIZE, remianLen);
//			}
//
//			buff = receive(mInputStream, receiveLen);
//			if (buff == null) {
//				setState(State.finished);
//				return;
//			}
//
//			setReceivedLength(getReceivedLength() + buff.length);
//			getSaveTo().write(buff);
//		} catch(IOException e) {
//			setState(State.exceptional);
//			e.printStackTrace();
//		}
//	}


	/**
	 * Finish to receiving data.
	 */
	private void finishReceive() {
		try {
			OutputStream os = getSaveTo();
			os.flush();
			os.close();

		//	isStop = true;
			invokeListener("onFinish");
			setState(State.finished);
			setIsFinished(true);
		} catch (IOException e) {
			setState(State.exceptional);
			e.printStackTrace();
		}
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
	public AbstractTaskInfo info() {
		return null;
	}
}
