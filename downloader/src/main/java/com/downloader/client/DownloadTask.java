package com.downloader.client;

import java.io.IOException;
import java.net.URL;

import com.downloader.net.AbsReceiver;
import com.downloader.net.Protocol;
import com.downloader.net.http.HttpReceiver;
import com.downloader.net.http.HttpRequest;
import com.downloader.util.StringUtil;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public class DownloadTask extends Task {
	/** Supports protocols. */
	private final String[] protocols = {
		Protocol.HTTP,
		Protocol.HTTPS
	};
	
	/** Receiver instance. */
	private AbsReceiver mReceiver = null;
	
	/** Url for download task. */
	private URL mUrl = null;
	
	
	/**
	 * Constructor for creating task.
	 * @param d The downloader.
	 */
	public DownloadTask(URL url) {
		if (url == null)
			throw new NullPointerException("url is null!");

		setUrl(url);
	}
	
	@Override
	public void start() throws IOException {
		try{
			switch(StringUtil.contains(protocols, mUrl.getProtocol())) {
				case 0:
					mReceiver = new HttpReceiver(new HttpRequest(mUrl));
					break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		mReceiver.start();
	}

	@Override
	public void pause() throws IOException {
		mReceiver.start();
	}

	@Override
	public void resume() throws IOException {
		mReceiver.start();
	}

	@Override
	public void stop() throws IOException {
		mReceiver.start();
	}

	public AbsReceiver getReceiver() {
		return mReceiver;
	}

	public void setReceiver(AbsReceiver receiver) {
		mReceiver = receiver;
	}

	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL url) {
		mUrl = url;
	}
}
