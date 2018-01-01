package com.badsocket.core.downloader.exception;

import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */
public class DownloadException extends RuntimeException {
	public DownloadException(String msg) {
		super(msg);
	}
}
