package com.tankwar.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Describe a HTTP header. This header maybe 
 * is request header or response header.
 * 
 * @author HGH
 * @since 2015/11/05
 */
public abstract class Header {
    /** The data of header. */
    private Map<String, String> mContent = new HashMap<>();


	/**
	 * Construct a header.
	 */
	public Header() {
	}


    /**
     * Construct a header by input stream.
     * @param header A stream contains header data.
     */
	public Header(InputStream header) throws IOException {
		setContent(header);
	}


    /**
     * Construct a header by reader.
     * @param header A reader contains header data.
     */
	public Header(Reader header) throws IOException {
		setContent(header);
	}


    /**
     * Construct a header by string.
     * @param header A string contains header data.
     */
	public Header(String header) {
        setContent(header);
	}


    /**
     * Construct a header by hash map.
     * @param header A string contains header data.
     */
	public Header(Map<String, String> header) {
        setContent(header);
	}


    /**
     * Get the header content.
     * @return Header content.
     */
    public Map<String, String> getContent() {
        return mContent;
    }


	/**
	 * Set header content by hash map.
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 */
    public void setContent(Map<String, String> data) throws NullPointerException{
        mContent = data;
    }

	/**
	 * Get header data by key.
	 * @param key The key.
	 */
	public String get(String key) {
		return mContent.get(key);
	}
	
	
	/**
	 * Append all data to header.
	 * @param data The data.
	 */
	public void appendAll(Map<String, String> data) {
		mContent.putAll(data);
	}


	/**
	 * Append a line to http header.
	 * @param headerKey The key of new line.
	 * @param headerValue The value of new line.
	 */
	public Header append(String headerKey, String headerValue) {
		mContent.put(headerKey, headerValue);
		return this;
	}


	/**
	 * Remove a line from http header by a special key.
	 * @param key The header key.
	 * @return If removed return true, else false.
	 */
	public boolean remove(String key) {
		return mContent.remove(key) != null;
	}


	/**
	 * Set header content by string.
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 */
    public abstract void setContent(String data) throws NullPointerException;


	/**
	 * Set header content by reader.
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException If can't read content.
	 */
    public abstract void setContent(Reader data) throws NullPointerException, IOException;


	/**
	 * Set header content by input stream.
	 * @param data Header content.
	 * @throws IOException If content is null.
	 * @throws NullPointerException If can't read content.
	 */
    public abstract void setContent(InputStream data) throws IOException, NullPointerException;


	/**
	 * Get header content as string.
	 * @return Stringify header content.
	 */
	public abstract String toString() throws NullPointerException;
}
