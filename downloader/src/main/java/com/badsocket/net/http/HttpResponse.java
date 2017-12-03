package com.badsocket.net.http;

import com.badsocket.net.SocketEntity;
import com.badsocket.net.SocketHeader;
import com.badsocket.net.SocketResponse;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.WebAddress;
import com.badsocket.util.Log;
import com.badsocket.util.StringUtil;
import com.badsocket.util.TimeUtils;
import com.badsocket.util.UrlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

/**
 * Http response.
 */
public class HttpResponse extends SocketResponse {

	protected BaseHttpRequest httpRequest;

	protected InputStream inputStream;

	protected Socket socket;

	protected SocketHeader header;

	protected long contentLength;

	protected String mTransferEncoding;

	protected String mFileName;

	protected String mContentType;

	protected Date mDate;

	protected HttpCookie[] mCookies;

	protected boolean isKeepAlive;

	protected boolean isSupportRange;

	protected boolean isChunked;

	protected Float mHttpVersion;

	protected URL mUrl;

	protected int redirectTimes = 5;


	/**
	 * Get response from request.
	 *
	 * @param r Request object.
	 */
	protected HttpResponse(SocketRequest r) throws IOException {
		super(r);
		httpRequest = (BaseHttpRequest) r;
		if (r.connected()) {
			if (!r.sent()) {
				throw new ConnectException();
			}

			socket = r.socket();
			inputStream = socket.getInputStream();
			header = new HttpHeader(inputStream);
			mUrl = httpRequest.getUrl();
			entity = new HttpEntity(inputStream);

			parseResponse();
			checkRedirect();
		}
	}


	protected void parseResponse() throws IOException {
		HttpHeader header = (HttpHeader) this.header;
		contentLength = StringUtil.str2Long(header.get(Http.CONTENT_LENGTH), 0L);
		mTransferEncoding = header.get(Http.TRANSFER_ENCODING);
		mFileName = UrlUtils.decode(UrlUtils.filename(httpRequest.getUrl()), "UTF-8");
		mContentType = header.get(Http.CONTENT_TYPE);
		mHttpVersion = Float.parseFloat(header.getVersion());
		mCookies = HttpCookie.formString(header.get(Http.SET_COOKIE));
		mDate = TimeUtils.str2Date(header.get(Http.DATE), Http.GMT_DATE_FORMAT[0], Locale.ENGLISH);
		isKeepAlive = Http.KEEP_ALIVE.equalsIgnoreCase(header.get(Http.CONNECTION));
		isSupportRange = header.get(Http.CONTENT_RANGE) != null;
		isChunked = Http.CHUNKED.equalsIgnoreCase(header.get(Http.TRANSFER_ENCODING));
		parseContentDisposition();
	}
	
	
	protected void parseContentDisposition() {
		int off = 0;
		String 	disp = "", type = "";
		String[] arr = null;

		HttpHeader header = (HttpHeader) this.header;
		if ((disp = header.get(Http.CONTENT_DISPOSITION)) != null && disp.trim().length() != 0) {
			if (disp.contains(";")) {
				arr = disp.split(";");
				mContentType = arr[0];
				if ((off = arr[1].indexOf("filename")) != -1) {
					mFileName = UrlUtils.decode(arr[1].substring(off + 9), "UTF-8");
				}
			}
		} else {
			mContentType = disp;
		}
	}


	protected void checkRedirect() throws IOException, RedirectException {
		String newUrl = "";
		HttpHeader header = (HttpHeader) this.header;
		if ((newUrl = header.get(Http.LOCATION)) != null) {
			if (redirectTimes-- <= 0) {
				throw new RedirectException();
			}

			Log.println(newUrl);
			httpRequest.setUrl(UrlUtils.fullUrl(mUrl, newUrl));
			httpRequest.setAddress(new WebAddress(UrlUtils.fullUrl(mUrl, newUrl)));
			httpRequest.reopen();
			parseResponse();
			checkRedirect();
		}
	}
	


	@Override
	public void close() throws IOException {
		inputStream.close();
	}


	public String getHeader(String key) {
		return ((HttpHeader) header).get(key);
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public SocketHeader getHeader() {
		return header;
	}


	public SocketEntity getEntity() {
		return entity;
	}


	public long getContentLength() {
		return contentLength;
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


	public boolean isChunked() {
		return isChunked;
	}
}


