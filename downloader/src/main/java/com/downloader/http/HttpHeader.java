package com.downloader.http;


import com.downloader.base.AbstractHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

/**
 * Describe a HTTP header. This header maybe 
 * is request header or response header.
 * 
 * @author HGH
 * @since 2015/11/05
 */
public class HttpHeader extends AbstractHeader {
	/** Http method of requesting. */
	private Http.Method mMethod;

	/** Http Version. */
	private String mVersion;

	/** The request url. */
	private String mUrl;

	/** The response status code. */
	private String mStatusCode;

	/** The response status message. */
	private String mStatusMsg;

	/** The parser of http header. */
	private HttpHeaderParser mParser;


	/**
	 * Default constructor.
	 */
	public HttpHeader() {}


	/**
	 * Construct a header by string.
	 *
	 * @param header A string inArray header data.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	public HttpHeader(String header) throws Exception {
		super(header);
	}


	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string inArray header data.
	 */
	public HttpHeader(Map<String, String> header) {
		super(header);
	}


	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string inArray header data.
	 */
	public HttpHeader(InputStream header) throws Exception {
		super(header);
	}
	
	
	/**
	 * Copy a header content from header.
	 * @param header Another header.
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public HttpHeader(HttpHeader header) throws Exception {
		super(header);
	}



	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string inArray header data.
	 */
	public HttpHeader(Reader header) throws Exception {
		super(header);
	}
	
	
	/**
	 * Set the parser.
	 */
	private void initParser() {
		if (mParser == null)
			mParser = new HttpHeaderParser();
	}


	/**
	 * Set header content by string.
	 *
	 * @param data AbstractHeader content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException 
	 */
	@Override
	public void setContent(String data) throws NullPointerException, IOException {
		initParser();
		setContent(mParser.parse(data.getBytes()));
	}


	/**
	 * Set header content by reader.
	 *
	 * @param data AbstractHeader content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException          If can't read content.
	 */
	@Override
	public void setContent(Reader data) throws NullPointerException, IOException {
		initParser();
		setContent(mParser.parse(data));
	}


	/**
	 * Set header content by input stream.
	 *
	 * @param data AbstractHeader content.
	 * @throws IOException          If can't read content.
	 * @throws NullPointerException If content is null.
	 */
	@Override
	public void setContent(InputStream data) throws IOException, NullPointerException {
		initParser();
		setContent(mParser.parse(data));
	}
	
	
	/**
	 * Copy content from another header.
	 */
	@Override
	public void setContent(AbstractHeader header) throws IOException, NullPointerException {
		if (header == null)
			throw new NullPointerException("The input header is null!");
		
		
		HttpHeader hr = (HttpHeader) header;
		setVersion(hr.getVersion());
		setMethod(hr.getMethod());
		setUrl(hr.getUrl());
		setStatusCode(hr.getStatusCode());
		setStatusMsg(hr.getStatusMsg());
		setContent(header.getContent());
	}


	/**
	 * Get header content as string.
	 *
	 * @return Stringify header content.
	 */
	@Override
	public String toString() throws NullPointerException {
		if (getContent() == null)
			throw new NullPointerException("AbstractHeader content is null!");

		StringBuilder sb = new StringBuilder();
		if (getMethod() != null && getUrl() != null && getVersion() != null) {
			sb.append(mMethod.name()).append(Http.SPACE).append(mUrl).append(Http.SPACE)
			  .append(Http.PROTOCOL).append("/").append(mVersion).append(Http.CRLF);
		}else{
			sb.append(Http.PROTOCOL).append("/").append(mVersion).append(Http.SPACE)
			  .append(mStatusCode).append(Http.SPACE).append(mStatusMsg).append(Http.CRLF);
		}

		for (String key : getContent().keySet()) {
			if (key.length() == 0) {
				sb.append(getContent().get(key));
				continue;
			}
			sb.append(key).append(":").append(Http.SPACE)
			  .append(getContent().get(key)).append(Http.CRLF);
		}

		sb.append(Http.CRLF);
		return sb.toString();
	}


	public Http.Method getMethod() {
		return mMethod;
	}


	public void setMethod(Http.Method method) {
		mMethod = method;
	}


	public String getVersion() {
		return mVersion;
	}


	public void setVersion(String version) {
		mVersion = version;
	}


	public String getUrl() {
		return mUrl;
	}


	public void setUrl(String url) {
		mUrl = url;
	}


	public String getStatusCode() {
		return mStatusCode;
	}


	public void setStatusCode(String statusCode) {
		mStatusCode = statusCode;
	}


	public String getStatusMsg() {
		return mStatusMsg;
	}


	public void setStatusMsg(String statusMsg) {
		mStatusMsg = statusMsg;
	}
}
