package com.downloader.net.http;

import com.downloader.net.Response;
import com.downloader.util.StringUtil;
import com.downloader.util.UrlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
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

	private Socket mSocket;

	private HttpHeader mHeader;

	private long mContentLength;

	private String mTransferEncoding;

	private String mFileName;

	private String mContentType;

	private Date mDate;

	private HttpCookie[] mCookies;

	private boolean isKeepAlive;

	private boolean isSupportRange;

	private Float mHttpVersion;


	/**
	 * Get response from request.
	 *
	 * @param r Request object.
	 */
	public HttpResponse(HttpRequest r) throws IOException {
		super(r);
		mHttpRequest = r;

		if (r.isConnect()) {
			if (!r.isSend())
				r.send();
			
			mSocket = r.getSocket();
			mInputStream = mSocket.getInputStream();
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
		mHttpVersion = Float.parseFloat(mHeader.getVersion());
		isKeepAlive = Http.KEEP_ALIVE.equalsIgnoreCase(mHeader.get(Http.CONNECTION));
		isSupportRange = mHeader.get(Http.CONTENT_RANGE) != null;

		int off = 0;
		String contentRange = mHeader.get(Http.CONTENT_RANGE);
		String fileName = UrlUtil.getFilename(mHttpRequest.getUrl()), disp = "";
		if ((disp = getHeader().get(Http.CONTENT_DISPOSITION)) != null && disp.length() != 0) {
			if ((off = disp.indexOf("filename")) != -1)
				fileName = disp.substring(off + 9);
		}

		mFileName = fileName;
		String cookieStr = mHeader.get(Http.SET_COOKIE);
		if (cookieStr != null) {
			String[] cookieArr = cookieStr.split(Http.CRLF);
			mCookies = new HttpCookie[cookieArr.length];
			for (int i = 0; i < cookieArr.length; i++) {
				try {
					mCookies[i] = HttpCookie.formString(cookieArr[i]);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			mDate = new SimpleDateFormat(Http.GMT_DATE_FORMAT[0], Locale.ENGLISH)
							.parse(mHeader.get(Http.DATE));
		}
		catch(ParseException pe) {
			pe.printStackTrace();
		}
	}


	@Override
	public void close() throws IOException {
		mInputStream.close();
		mHttpRequest.close();
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

}
