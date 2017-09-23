package com.downloader.net.http;

import com.downloader.net.Response;
import com.downloader.util.StringUtil;
import com.downloader.util.TimeUtil;
import com.downloader.util.UrlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Http response.
 */
public class HttpResponse extends Response {

	protected HttpRequest mHttpRequest;

	protected InputStream mInputStream;

	protected Socket mSocket;

	protected HttpHeader mHeader;

	protected long mContentLength;

	protected String mTransferEncoding;

	protected String mFileName;

	protected String mContentType;

	protected Date mDate;

	protected HttpCookie[] mCookies;

	protected boolean isKeepAlive;

	protected boolean isSupportRange;

	protected Float mHttpVersion;

	protected URL mUrl;

	protected int redirectTimes = 5;


	/**
	 * Get response from request.
	 *
	 * @param r Request object.
	 */
	public HttpResponse(HttpRequest r) throws IOException, RedirectException {
		super(r);
		mHttpRequest = r;

		if (r.isConnect()) {
			if (!r.isSend()) {
				throw new ConnectException();
			}

			mSocket = r.getSocket();
			mInputStream = mSocket.getInputStream();
			mHeader = new HttpHeader();
			mHeader.setContent(mInputStream);
			mUrl = mHttpRequest.getUrl();
			parseResponse();
			checkRedirect();
		}
	}


	public HttpHeader info() {
		return mHeader;
	}


	protected void parseResponse() throws IOException {
		mContentLength = StringUtil.str2Long(mHeader.get(Http.CONTENT_LENGTH), 0L);
		mTransferEncoding = mHeader.get(Http.TRANSFER_ENCODING);
		mFileName = UrlUtil.getFilename(mHttpRequest.getUrl());
		mContentType = mHeader.get(Http.CONTENT_TYPE);
		mHttpVersion = Float.parseFloat(mHeader.getVersion());
		mCookies = HttpCookie.formString(mHeader.get(Http.SET_COOKIE));
		mDate = TimeUtil.str2Date(mHeader.get(Http.DATE), Http.GMT_DATE_FORMAT[0], Locale.ENGLISH);
		isKeepAlive = Http.KEEP_ALIVE.equalsIgnoreCase(mHeader.get(Http.CONNECTION));
		isSupportRange = mHeader.get(Http.CONTENT_RANGE) != null;
		parseContentDisposition();
	}
	
	
	protected void parseContentDisposition() {
		int off = 0;
		String 	disp = "", type = "";
		String[] arr = null;

		if ((disp = getHeader().get(Http.CONTENT_DISPOSITION)) != null && disp.trim().length() != 0) {
			if (disp.contains(";")) {
				arr = disp.split(";");
				mContentType = arr[0];
				if ((off = arr[1].indexOf("filename")) != -1) {
					mFileName = UrlUtil.decode(arr[1].substring(off + 9), "UTF-8");
				}
			}
		} else {
			mContentType = disp;
		}
	}


	protected void checkRedirect() throws IOException, RedirectException {
		String newUrl = "";
		if ((newUrl = mHeader.get(Http.LOCATION)) != null) {
			if (redirectTimes-- <= 0) {
				throw new RedirectException();
			}
			mHttpRequest.setUrl(UrlUtil.getFullUrl(mUrl, newUrl));
			mHttpRequest.reopen();
			parseResponse();
			checkRedirect();
		}
	}
	


	@Override
	public void close() throws IOException {
		mInputStream.close();
		mHttpRequest.close();
	}


	public String getHeader(String key) {
		return mHeader.get(key);
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


	public boolean isSupportRange() {
		return isSupportRange;
	}


	public URL getURL() {
		return mUrl;
	}
}


