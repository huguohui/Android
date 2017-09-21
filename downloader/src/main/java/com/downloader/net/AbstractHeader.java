package com.downloader.net;

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
public abstract class AbstractHeader {
    /** The data of header. */
    private Map<String, String> mContent = new HashMap<String, String>();


	/**
	 * Construct a header.
	 */
	public AbstractHeader() {
	}
	
	
	/**
	 * Construct a header from another header.
	 * @param header Another header.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	public AbstractHeader(AbstractHeader header) throws Exception {
		setContent(header);
	}


    /**
     * Construct a header by input stream.
     * @param header A stream inArray header data.
     */
	public AbstractHeader(InputStream header) throws Exception {
		setContent(header);
	}


    /**
     * Construct a header by reader.
     * @param header A reader inArray header data.
     */
	public AbstractHeader(Reader header) throws Exception {
		setContent(header);
	}


    /**
     * Construct a header by string.
     * @param header A string inArray header data.
     * @throws IOException 
     * @throws NullPointerException 
     */
	public AbstractHeader(String header) throws Exception {
        setContent(header);
	}


    /**
     * Construct a header by hash map.
     * @param header A string inArray header data.
     */
	public AbstractHeader(Map<String, String> header) {
        setContent(header);
	}


    /**
     * Get the header content.
     * @return AbstractHeader content.
     */
    public Map<String, String> getContent() {
        return mContent;
    }


	/**
	 * Set header content by hash map.
	 * @param data AbstractHeader content.
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
	public void addAll(Map<String, String> data) {
		mContent.putAll(data);
	}


	/**
	 * Append a line to http header.
	 * @param headerKey The key of new line.
	 * @param headerValue The value of new line.
	 */
	public AbstractHeader add(String headerKey, String headerValue) {
		mContent.containsKey(headerKey);
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
	 * @param data AbstractHeader content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException 
	 */
    public abstract void setContent(String data) throws Exception;


	/**
	 * Set header content by reader.
	 * @param data AbstractHeader content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException If can't read content.
	 */
    public abstract void setContent(Reader data) throws Exception;


	/**
	 * Set header content by input stream.
	 * @param data AbstractHeader content.
	 * @throws IOException If content is null.
	 * @throws NullPointerException If can't read content.
	 */
    public abstract void setContent(InputStream data) throws Exception;
    
    
	/**
	 * Copy content from another header.
	 * @param header AbstractHeader content.
	 * @throws IOException If content is null.
	 * @throws NullPointerException If can't read content.
	 */
    public abstract void setContent(AbstractHeader header) throws Exception;


	/**
	 * Get header content as string.
	 * @return Stringify header content.
	 */
	public abstract String toString();
}
