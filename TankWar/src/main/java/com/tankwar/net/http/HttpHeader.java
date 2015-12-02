package com.tankwar.net.http;


import com.tankwar.net.Header;
import com.tankwar.net.Parser;

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
public class HttpHeader extends Header {
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

	/** The header is request? */
	private boolean mIsRequest = true;

	/** The parser of http header. */
	private HttpHeaderParser mParser = new HttpHeaderParser();


	/**
	 * Default constructor.
	 */
	public HttpHeader() {}


	/**
	 * Construct a header by string.
	 *
	 * @param header A string contains header data.
	 */
	public HttpHeader(String header) {
		super(header);
	}


	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string contains header data.
	 */
	public HttpHeader(Map<String, String> header) {
		super(header);
	}


	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string contains header data.
	 */
	public HttpHeader(InputStream header) throws IOException {
		super(header);
	}



	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string contains header data.
	 */
	public HttpHeader(Reader header) throws IOException {
		super(header);
	}


	/**
	 * Set header content by string.
	 *
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 */
	@Override
	public void setContent(String data) throws NullPointerException {
		setContent(mParser.parse(data.getBytes()).getContent());
	}


	/**
	 * Set header content by reader.
	 *
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException          If can't read content.
	 */
	@Override
	public void setContent(Reader data) throws NullPointerException, IOException {
		setContent(mParser.parse(data).getContent());
	}


	/**
	 * Set header content by input stream.
	 *
	 * @param data Header content.
	 * @throws IOException          If content is null.
	 * @throws NullPointerException If can't read content.
	 */
	@Override
	public void setContent(InputStream data) throws IOException, NullPointerException {
		setContent(mParser.parse(data).getContent());
	}


	/**
	 * Get header content as string.
	 *
	 * @return Stringify header content.
	 */
	@Override
	public String toString() throws NullPointerException {
		if (getContent() == null)
			throw new NullPointerException("Header content is null!");

		StringBuilder sb = new StringBuilder();
		if (mIsRequest) {
			sb.append(mMethod.name()).append(Http.SPACE).append(mUrl).append(Http.SPACE)
			  .append(Http.PROTOCOL).append("/").append(mVersion).append(Http.CRLF);
		}else{
			sb.append(Http.PROTOCOL).append("/").append(mVersion).append(Http.SPACE)
			  .append(mStatusCode).append(Http.SPACE).append(mStatusMsg).append(Http.CRLF);
		}

		for (String key : getContent().keySet()) {
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
		mUrl = url.length() == 0 ? "/" : url;
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


	public boolean isRequest() {
		return mIsRequest;
	}


	public void setIsRequest(boolean isRequest) {
		mIsRequest = isRequest;
	}
}
