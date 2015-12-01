package com.tankwar.net.http;

import com.tankwar.net.Parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class HttpHeaderParser extends Parser {
	public final static int BUFF_SIZE = 1024 * 1024;


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

		HttpHeader header = new HttpHeader(false);
		byte[] buff = new byte[1024];
		int lineOffset = 0;
		while(offset < len) {
			if (offset < len-1 && data[offset] == '\r' && data[offset + 1] == '\n') {
				int pos = 0;
				if (lineOffset == 0)
					break;

				while(pos <= lineOffset && buff[pos] != ':') pos++;
				if (pos <= lineOffset - 1) {
					header.append(new String(buff, 0, pos), new String(buff, pos + 1,
							lineOffset - pos - 1));
				}else{
					if (pos == lineOffset - 1) {
						header.append(new String(buff, 0, pos - 1), "");
					}else{
						Pattern response = Pattern.compile("^HTTP/(\\d\\.\\d)\\s(\\d+)\\s(\\w+)$");
						Pattern request = Pattern.compile(
								"^(GET|POST|PUT|HEAD)\\s([^\\s]+)\\sHTTP/(\\d\\.\\d)$");
						Matcher matcher;

						if ((matcher = response.matcher(new String(buff, 0, lineOffset))) != null
								&& matcher.matches()) {
							header.setVersion(matcher.group(1));
							header.setStatusCode(matcher.group(2));
							header.setStatusMsg(matcher.group(3));
						}else if ((matcher = request.matcher(new String(buff, 0, lineOffset))) != null
								&& matcher.matches()) {
							header.setMethod(Http.Method.valueOf(matcher.group(1)));
							header.setUrl(matcher.group(2));
							header.setVersion(matcher.group(3));
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
		if (data == null)
			throw new NullPointerException("The data can't null!");

		int len = 0;
		byte[] buff = new byte[BUFF_SIZE];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		while(-1 != (len = data.read(buff))) {
			bos.write(buff, 0, len);
		}

		buff = bos.toByteArray();
		bos.flush();
		bos.close();
		return parse(buff);
	}


	/**
	 * Parse data to a kind of format.
	 *
	 * @param data Provided data.
	 * @return Some data.
	 */
	@Override
	public HttpHeader parse(Reader data) {
		return null;
	}


}
