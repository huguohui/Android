package com.tankwar.net.http;

import com.tankwar.net.Downloader;
import com.tankwar.net.Requester;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URL;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpDownloader implements Downloader {
	/**
	 * Construct a http downloader object.
	 * @param url Special URL.
	 */
	public HttpDownloader(URL url) {
	}


	/**
	 * Construct a http downloader object.
	 * @param requester A {@link com.tankwar.net.Requester}.
	 */
	public HttpDownloader(Requester requester) {

	}



	/**
	 * Start download data.
	 */
	@Override
	public void download() throws IOException, ConnectException {

	}


	/**
	 * Start download from stream and save data to file.
	 *
	 * @param file Save to file.
	 */
	@Override
	public void download(File file) throws IOException, ConnectException {

	}


	/**
	 * Start download from stream and save data to file.
	 *
	 * @param file Save to file.
	 */
	@Override
	public void download(String file) throws IOException, ConnectException {

	}


	/**
	 * Get length of data.
	 *
	 * @return Length of data.
	 */
	@Override
	public long getLength() {
		return 0;
	}


	/**
	 * Get length of received data.
	 *
	 * @return Length received data.
	 */
	@Override
	public long getReceivedLength() {
		return 0;
	}


	/**
	 * Receiving data.
	 *
	 * @throws IOException      When I/O exception.
	 * @throws ConnectException When connection exception.
	 */
	@Override
	public void receive() {

	}


	/**
	 * To receiving data from source, and save data to somewhere.
	 *
	 * @param source Data source.
	 */
	@Override
	public void receive(InputStream source) throws IOException {

	}


	/**
	 * To receiving data from source.
	 *
	 * @param source Data source.
	 */
	@Override
	public void receive(Reader source) throws IOException {

	}
}
