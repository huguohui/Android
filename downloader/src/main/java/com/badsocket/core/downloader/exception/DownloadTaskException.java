package com.badsocket.core.downloader.exception;

/**
 * Created by skyrim on 2017/10/6.
 */
public class DownloadTaskException extends Exception {
	public DownloadTaskException(String msg, Throwable e) {
		super(msg, e);
	}

	public DownloadTaskException(Throwable e) {
		super(e);
	}
}
