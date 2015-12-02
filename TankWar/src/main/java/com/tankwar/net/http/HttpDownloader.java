package com.tankwar.net.http;


import com.tankwar.net.Downloader;
import com.tankwar.net.Requester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpDownloader extends Downloader {
	/** The input stream. */
	private InputStream mInputStream;

	/**
	 * Construct a http downloader object.
	 * @param requester A {@link Requester}.
	 */
	public HttpDownloader(Requester requester) {
		super(requester);
		setLength(Long.parseLong(getRequester().getHeader().get(Http.CONTENT_LENGTH)));
		setDownloadedLength(0);
	}


	/**
	 * Start download data.
	 */
	@Override
	public void download() throws IOException {
	}


	/**
	 * Start download from stream and save data to file.
	 *
	 * @param file Save to file.
	 */
	@Override
	public void download(File file) throws IOException {

	}


	/**
	 * Receiving data.
	 *
	 * @return Received data by byte.
	 * @throws IOException      When I/O exception.
	 */
	@Override
	public byte receive() throws IOException {
		return (byte) mInputStream.read();
	}

	/**
	 * To receiving data from source, and save data to somewhere.
	 *
	 * @param source Data source.
	 * @param size
	 */
	@Override
	public byte[] receive(InputStream source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");
		if (size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		byte[] buff = new byte[size];
		if (-1 == source.read(buff))
			return null;
		setDownloadedLength(getDownloadedLength() + size);
		return buff;
	}

	/**
	 * To receiving data from source.
	 *  @param source Data source.
	 * @param size*/
	@Override
	public char[] receive(Reader source, int size) throws IOException {

		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");
		if (size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		char[] buff = new char[size];
		if (-1 == source.read(buff))
			return null;
		setDownloadedLength(getDownloadedLength() + size);
		return buff;
	}
}
