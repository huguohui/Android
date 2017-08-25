package com.downloader.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.downloader.base.Parser;

/**
 *
 */
public class HttpHeaderParser implements Parser {
	/** Default buffer size. */
	public final static int BUFF_SIZE = 1024 * 8;


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
					header.add(new String(buff, 0, pos), new String(buff, pos + 1,
							lineOffset - pos - 1).trim());
				}else{
					if (pos == lineOffset - 1) {
						header.add(new String(buff, 0, pos - 1), "");
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
		if (data == null)
			throw new NullPointerException("The data can't null!");

		byte buff;
		byte[] searchStr = {'\r', '\n', '\r', '\n'};
		byte searchedCount = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		while(-1 != (buff = (byte)data.read())) {
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
