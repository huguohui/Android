package com.badsocket.net.http;


import com.badsocket.net.Entity;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;

/**
 * Describe a HTTP content body.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpEntity extends Entity {
	/**
	 * content of text.
	 */
	public final static int T_TEXT	 = 0x1;

	/**
	 * content of binary.
	 */
	public final static int T_BINARY = 0x2;

	/**
	 * Mixed content.
	 */
	public final static int T_MIXED  = 0x4;

	public final static int DEFAULT_DATA_SIZE = 1024 << 3;

	/**
	 * The content of body.
	 */
	protected byte[] content;

	/**
	 * The encoding of body.
	 */
	protected String encoding;

	/**
	 * The length of body.
	 */
	protected long length;

	/**
	 * The type of body.
	 * 0 is binary.
	 * 1 is text.
	 * 2 is mixed.
	 */
	protected int type = T_TEXT;

	protected InputStream inputStream;

	protected Reader reader;

	protected List<File> files;


	/**
	 * Construct a HTTP body from string.
	 * @param body The content of body.
	 */
	public HttpEntity(String body) {
		content = body.getBytes();
	}


	/**
	 * Construct a HTTP body from input stream.
	 * @param body The input stream of inArray content.
	 */
	public HttpEntity(InputStream body) {
		inputStream = body;
	}


	/**
	 * Construct a body from reader.
	 * @param body The content source.
	 */
	public HttpEntity(Reader body) {
		reader = body;
	}


	public void getContent(OutputStream os) {

	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public long getLength() {
		return length;
	}


	public void setLength(long length) {
		this.length = length;
	}


	public int getType() {
		return type;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public Reader getReader() {
		return reader;
	}
}
