package com.downloader;

import java.io.InputStream;
import java.io.Reader;

/**
 * Describe a HTTP content body.
 * @author HGH
 * @since 2015/11/05
 */
public abstract class Body {
	/**
	 * content of binary.
	 */
	public final static int T_BINARY = 0;

	/**
	 * content of text.
	 */
	public final static int T_TEXT	 = 1;
	
	/**
	 * Mixed content.
	 */
	public final static int T_MIXED  = 2;


	/**
	 * The content of body.
	 */
	private byte[] content;

	
	/**
	 * The encoding of body.
	 */
	private String encoding;


	/**
	 * The length of body.
	 */
	private long length;


	/**
	 * The type of body.
	 * 0 is binary.
	 * 1 is text.
	 * 2 is mixed.
	 */
	private String type;


	/** 
	 * Construct a body from input stream.
	 * @param body The content source.
	 */
	public Body(InputStream body) {
        setContent(body);
	}


	/**
	 * Construct a body from reader.
	 * @param body The content source.
	 */
	public Body(Reader body) {
		setContent(body);
	}


	/**
	 * Construct a body from input stream.
	 * @param body The content.
	 */
	public Body(String body) {
        setContent(body);
	}


    /**
     * Fetch content from input stream.
     * @param source Content source
     */
    public abstract void setContent(InputStream source);


    /**
     * Fetch content from reader.
     * @param source Content source
     */
    public abstract void setContent(Reader source);


    /**
     * Fetch content from source.
     * @param source Content source
     */
    public abstract void setContent(String source);


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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
}