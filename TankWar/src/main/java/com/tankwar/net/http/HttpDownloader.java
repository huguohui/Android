package com.tankwar.net.http;

import com.tankwar.net.Downloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URL;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpDownloader extends Http implements Downloader{
	/**
	 * Construct a http request object.
	 *
	 * @param url    Special URL.
	 * @param method
	 */
	public HttpDownloader(URL url, Method method) throws NullPointerException {
		super(url);
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


	/**
	 * Send data to somewhere.
	 *
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	@Override
	public boolean send() throws IOException {
		return false;
	}


	/**
	 * Send data to somewhere.
	 *
	 * @param data The data.
	 * @param to   Send to somewhere.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	@Override
	public boolean send(byte[] data, OutputStream to) throws IOException {
		return false;
	}
}
