package com.badsocket.core.downloader.exception;

import com.badsocket.core.DownloadTaskExecutor;

/**
 * Created by windy on 2018/2/16.
 */

public class StopDownloadTaskException extends DownloadTaskException {
	public StopDownloadTaskException(String message) {
		super(message);
	}
}
