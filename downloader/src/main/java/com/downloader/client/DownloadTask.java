package com.downloader.client;

import java.io.IOException;

import com.downloader.net.Controlable;
import com.downloader.net.Downloader;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public class DownloadTask implements Controlable {
	
	
	/**
	 * Constructor for creating task.
	 * @param d The downloader.
	 */
	public DownloadTask(Downloader d) {
		if (d == null)
			return;
		
		
	}
	
	@Override
	public void start() throws IOException {
		
	}

	@Override
	public void pause() throws IOException {
		
	}

	@Override
	public void resume() throws IOException {
		
	}

	@Override
	public void stop() throws IOException {
		
	}
	
	
	public static DownloadTask create() {
		return new DownloadTask(null);
	}

}
