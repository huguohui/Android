package com.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Parser data from data.
 * @since 2015/12/01
 */
public interface Parser {
	/**
	 * Parse data to a kind of format.
	 * @param data Provided data.
	 * @return Some data.
	 */
	public abstract Object parse(byte[] data);


	/**
	 * Parse data to a kind of format.
	 * @param data Provided data.
	 * @return Some data.
	 */
	public abstract Object parse(InputStream data) throws IOException;


	/**
	 * Parse data to a kind of format.
	 * @param data Provided data.
	 * @return Some data.
	 */
	public abstract Object parse(Reader data) throws IOException;

}
