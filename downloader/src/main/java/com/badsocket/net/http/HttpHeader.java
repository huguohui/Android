package com.badsocket.net.http;


import com.badsocket.core.Parser;
import com.badsocket.net.Header;
import com.badsocket.util.TimeCounter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
public class HttpHeader extends Header {
	/** Http method of requesting. */
	protected Http.Method mMethod;

	/** Http Version. */
	protected String mVersion;

	/** The request url. */
	protected String mUrl;

	/** The response status code. */
	protected String mStatusCode;

	/** The response status message. */
	protected String mStatusMsg;

	/** The parser of http header. */
	protected HttpHeaderParser mParser;

	/** The data of header. */
	protected Map<String, String> mContent = new HashMap<>();

	protected HttpCookie[] cookies;


	/**
	 * Default constructor.
	 */
	public HttpHeader() {}


	/**
	 * Construct a header by hash map.
	 *
	 * @param header A string inArray header data.
	 */
	public HttpHeader(InputStream header) throws IOException {
		parseContent(header);
	}
	
	
	/**
	 * Copy a header content from header.
	 * @param header Another header.
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public HttpHeader(HttpHeader header) throws IOException {
		parseContent(header);
	}


	/**
	 * Construct a header by reader.
	 * @param header A reader inArray header data.
	 */
	public HttpHeader(Reader header) throws IOException {
		parseContent(header);
	}


	/**
	 * Set the parser.
	 */
	protected void initParser() {
		if (mParser == null) {
			mParser = new HttpHeaderParser();
		}
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
	 * Remove a line from http header by a special key.
	 * @param key The header key.
	 * @return If removed return true, else false.
	 */
	public boolean remove(String key) {
		return mContent.remove(key) != null;
	}


	/**
	 * Set header content by string.
	 *
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException 
	 */
	protected void parseContent(String data) throws NullPointerException, IOException {
		initParser();
		parseContent(mParser.parse(data.getBytes()));
	}


	/**
	 * Set header content by reader.
	 *
	 * @param data Header content.
	 * @throws NullPointerException If content is null.
	 * @throws IOException          If can't readTask content.
	 */
	protected void parseContent(Reader data) throws NullPointerException, IOException {
		initParser();
		parseContent(mParser.parse(data));
	}


	/**
	 * Set header content by input stream.
	 *
	 * @param data Header content.
	 * @throws IOException          If can't readTask content.
	 * @throws NullPointerException If content is null.
	 */
	protected void parseContent(InputStream data) throws IOException, NullPointerException {
		initParser();
		parseContent(mParser.parse(data));
	}
	
	
	/**
	 * Copy content from another header.
	 */
	protected void parseContent(Header header) throws IOException{
		HttpHeader hr = (HttpHeader) header;
		setVersion(hr.getVersion());
		setMethod(hr.getMethod());
		setUrl(hr.getUrl());
		setStatusCode(hr.getStatusCode());
		setStatusMsg(hr.getStatusMsg());
		setContent(hr.getContent());

		if (hr.get(Http.SET_COOKIE) != null) {
			cookies = HttpCookie.formString(hr.get(Http.SET_COOKIE));
		}
	}


	/**
	 * Get header content as string.
	 *
	 * @return Stringify header content.
	 */
	@Override
	public String toString() {
		if (mContent == null)
			throw new NullPointerException("content is null!");

		StringBuilder sb = new StringBuilder();
		if (getMethod() != null && getUrl() != null && getVersion() != null) {
			sb.append(mMethod.name()).append(Http.SPACE).append(mUrl).append(Http.SPACE)
			  .append(Http.PROTOCOL).append("/").append(mVersion).append(Http.CRLF);
		}else{
			sb.append(Http.PROTOCOL).append("/").append(mVersion).append(Http.SPACE)
			  .append(mStatusCode).append(Http.SPACE).append(mStatusMsg).append(Http.CRLF);
		}

		for (Map.Entry<String, String> key : mContent.entrySet()) {
			if (key.getKey().length() == 0) {
				sb.append(key.getValue());
				continue;
			}

			sb.append(key.getKey()).append(":").append(Http.SPACE)
			  .append(key.getValue()).append(Http.CRLF);
		}

		return sb.append(Http.CRLF).toString();
	}


	public HttpHeader set(String key, String val) {
		if (Http.SET_COOKIE.equalsIgnoreCase(key)) {
			mContent.put(key, mContent.containsKey(key) ?
					mContent.get(key).concat(Http.CRLF).concat(val) : val);
		} else {
			mContent.put(key, val);
		}

		return this;
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


	public HttpCookie[] getCookies() {
		return cookies;
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


	public Map<String, String> getContent() {
		return mContent;
	}


	public void setContent(Map<String,String> content) {
		mContent = content;
	}


	public static class HttpHeaderParser implements Parser {
		/** Default buffer size. */
		public final static int BUFF_SIZE = 1024 * 8;


		/**
		 * Parse data to a kind of format.
		 *
		 * @param data Provided data.
		 * @return Some data.
		 */
		@Override
		public Object parse(Object data) {
			return null;
		}


		/**
		 * Parse data to a kind of format.
		 *
		 * @param data Provided data.
		 * @return Some data.
		 */
		@Override
		public HttpHeader parse(byte[] data) {
			if (data == null)
				throw new NullPointerException("The data to parsing is null!");

			int len = data.length, offset = 0;
			if (len == 0) return null;

			HttpHeader header = new HttpHeader();
			byte[] buff = new byte[BUFF_SIZE];
			int lineOffset = 0;
			while(offset < len) {
				if (offset < len-1 && data[offset] == '\r' && data[offset + 1] == '\n') {
					int pos = 0;
					if (lineOffset == 0)
						break;

					while(pos <= lineOffset && buff[pos] != ':')
						pos++;

					if (pos <= lineOffset - 1) {
						header.set(new String(buff, 0, pos), new String(buff, pos + 1,
								lineOffset - pos - 1).trim());
					}else{
						if (pos == lineOffset - 1) {
							header.set(new String(buff, 0, pos - 1), "");
						}else{
							String firstLine = new String(buff, 0, lineOffset);
							String[] arrStr = firstLine.split("\\s");
							boolean isResponse = arrStr[0].startsWith("HTTP");

							if (isResponse) {
								header.setVersion(arrStr[0].split("/")[1]);
								header.setStatusCode(arrStr[1]);
								header.setStatusMsg(arrStr[2]);
							}else{
								header.setMethod(Http.Method.valueOf(arrStr[0]));
								header.setUrl(arrStr[1]);
								header.setVersion(arrStr[2]);
							}
						}
					}

					offset += 2;
					lineOffset = 0;
					continue;
				}

				buff[lineOffset] = data[offset++];
				lineOffset++;
			}

			return header;
		}


		/**
		 * Parse data to a kind of format.
		 *
		 * @param data Provided data.
		 * @return Some data.
		 */
		@Override
		public HttpHeader parse(InputStream data) throws IOException {
			if (data == null) {
				throw new NullPointerException("The data can't null!");
			}

			byte buff;
			byte[] searchStr = {'\r', '\n', '\r', '\n'};
			byte searchedCount = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			//读取头部时，BufferedInputStream会有问题。
			while(-1 != (buff = (byte) data.read())) {
				if (buff == searchStr[searchedCount])
					searchedCount++;
				else
					searchedCount = 0;

				bos.write(buff);
				if (searchedCount == searchStr.length - 1) {
					bos.write(data.read());
					break;
				}
			}

			byte[] temp = bos.toByteArray();
			bos.flush();
			bos.close();
			return parse(temp);
		}


		/**
		 * Parse data to a kind of format.
		 *
		 * @param data Provided data.
		 * @return Some data.
		 */
		@Override
		public HttpHeader parse(Reader data) throws IOException {
			if (data == null)
				throw new NullPointerException("The data can't null!");

			BufferedReader reader = new BufferedReader(data);
			String line;
			StringBuilder sb = new StringBuilder();

			while(null != (line = reader.readLine())) {
				if (line.length() == 0)
					break;
				sb.append(line);
			}


			return parse(sb.toString().getBytes());
		}
	}
}
