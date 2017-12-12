package com.badsocket.net.http;

import com.badsocket.net.SocketRequest;
import com.badsocket.util.Log;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Created by skyrim on 2017/11/4.
 */

public interface HttpRequest extends SocketRequest {

	void open(SocketAddress address, Http.Method method) throws IOException;


	class Range extends SocketRequest.Range {
		public Range(long s, long e) {
			super(s, e - 1);
			Log.println(e);
		}


		public Range(SocketRequest.Range r) {
			super(r.start, r instanceof  HttpRequest.Range ? r.end : r.end - 1);
		}


		public String toString() {
			return end > 0 ? String.format("bytes=%d-%d", start, end)
					: String.format("bytes=%d-", start);
		}
	}
}
