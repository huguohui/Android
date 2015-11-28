package com.tankwar.net.http;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe a Hypertext transfer protocol.
 * @author HGH
 * @since 2015/11/05
 */
public abstract class Http {
	/** A URL special host address and request path, parameter. */
	private URL mUrl;

	/** A special HTTP version.*/
	private String mVersion;

	/** HTTP header. */
	private HttpHeader mHttpHeader;
	
	/** HTTP body. */
	private HttpBody mHttpBody;

    /** Is security HTTP? */
	private boolean mIsSecurity;

	/** Cookie of http. */
	private HttpCookie mHttpCookie;

	
	/**
	 * Construct a HTTP object by pass a URL.
	 * @param url URL
	 */
	public Http(URL url) {
		mUrl = url;
	}


	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL url) throws NullPointerException {
		if (mUrl == null) throw new NullPointerException("Url can't null!");
		mUrl = url;
	}

	public String getVersion() {
		return mVersion;
	}

	public void setVersion(String version) throws IllegalArgumentException {
		Matcher matcher = Pattern.compile("(\\d)\\.(\\d)").matcher(version);
		boolean error = false;
		if (matcher.find()) {
			int ver = Integer.parseInt(matcher.group(1));
			int subVer = Integer.parseInt(matcher.group(2));
			if (ver > 2)
				error = true;
		}else{
			error = true;
		}

		if (error)
			throw new IllegalArgumentException("HTTP version is invalid!");

		mVersion = version;
	}

	public HttpHeader getHttpHeader() {
		return mHttpHeader;
	}

	public void setHttpHeader(HttpHeader httpHeader) throws NullPointerException{
		if (httpHeader == null)
			throw new NullPointerException("HTTP header can't null!");

		mHttpHeader = httpHeader;
	}

	public HttpBody getHttpBody() {
		return mHttpBody;
	}

	public void setHttpBody(HttpBody httpBody) throws NullPointerException {
		if (httpBody == null)
			throw new NullPointerException("Http body can't null!");

		mHttpBody = httpBody;
	}

	public boolean isSecurity() {
		return mIsSecurity;
	}

	public void setIsSecurity(boolean isSecurity) {
		mIsSecurity = isSecurity;
	}


}
