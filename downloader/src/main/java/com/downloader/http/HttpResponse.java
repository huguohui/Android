package com.downloader.http;

import com.downloader.base.Response;
import com.downloader.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Http response.
 */
public class HttpResponse extends Response {

	private HttpRequest mHttpRequest;

	private InputStream mInputStream;

	private HttpHeader mHeader;

	private long mContentLength;

	private String mTransferEncoding;

	private String mFileName;

	private String mContentType;

	private Date mDate;

	private HttpCookie[] mCookies;

	private boolean isKeepAlive;

	private Float mHttpVersion;


	/**
	 * Get response from request.
	 *
	 * @param r Request object.
	 */
	public HttpResponse(HttpRequest r) throws IOException {
		super(r);
		mHttpRequest = r;

		if (r.isConnect() && r.isSend()) {
			mInputStream = r.getSocket().getInputStream();
			mHeader = new HttpHeader();
			mHeader.setContent(mInputStream);
			parseResponse();
		}
	}


	public HttpHeader info() {
		return mHeader;
	}


	private void parseResponse() {
		mContentLength = StringUtil.str2Long(mHeader.get(Http.CONTENT_LENGTH), 0L);
		mTransferEncoding = mHeader.get(Http.TRANSFER_ENCODING);
		mFileName = mHttpRequest.getUrl().getFile();
		mContentType = mHeader.get(Http.CONTENT_TYPE);
		isKeepAlive = mHeader.get(Http.CONNECTION).equalsIgnoreCase("Keep-Alive");
		mHttpVersion = Float.parseFloat(mHeader.getVersion());

		String cookieStr = mHeader.get(Http.SET_COOKIE);
		String[] cookieArr = cookieStr.split(Http.CRLF);
		mCookies = new HttpCookie[cookieArr.length];
		for (int i = 0; i < cookieArr.length; i++) {
			try {
				mCookies[i] = new HttpCookie(cookieArr[i]);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		try {
			mDate = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss GMT", Locale.ENGLISH)
								.parse(mHeader.get(Http.DATE));
		}
		catch(ParseException pe) {}
	}


	public InputStream getInputStream() {
		return mInputStream;
	}


	public HttpHeader getHeader() {
		return mHeader;
	}


	public long getContentLength() {
		return mContentLength;
	}


	public String getTransferEncoding() {
		return mTransferEncoding;
	}


	public String getFileName() {
		return mFileName;
	}


	public String getContentType() {
		return mContentType;
	}


	public Date getDate() {
		return mDate;
	}


	public HttpCookie[] getCookies() {
		return mCookies;
	}


	public boolean isKeepAlive() {
		return isKeepAlive;
	}


	public Float getHttpVersion() {
		return mHttpVersion;
	}
}
