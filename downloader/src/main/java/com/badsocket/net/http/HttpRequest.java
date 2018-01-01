package com.badsocket.net.http;

import com.badsocket.net.Request;
import com.badsocket.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by skyrim on 2017/11/4.
 */

public interface HttpRequest extends Request {

	void open(InetSocketAddress address, Http.Method method) throws IOException;


	class Range extends Request.Range {
		public Range(long s, long e) {
			super(s, e - 1);
		}


		public Range(Request.Range r) {
			super(r.start, r instanceof  HttpRequest.Range ? r.end : r.end - 1);
		}


		public String toString() {
			return end > 0 ? String.format("bytes=%d-%d", start, end)
					: String.format("bytes=%d-", start);
		}
	}
}
