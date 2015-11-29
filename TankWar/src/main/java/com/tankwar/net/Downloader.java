package com.tankwar.net;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

/**
 * Download some data form a place.
 */
public interface Downloader extends Sender, Receiver {
	/**
	 * Start download data.
	 */
	void download() throws IOException, ConnectException;


	/**
	 * Start download from stream and save data to file.
	 * @param file Save to file.
	 */
	void download(File file) throws IOException, ConnectException;


	/**
	 * Start download from stream and save data to file.
	 * @param file Save to file.
	 */
	void download(String file) throws IOException, ConnectException;
}
