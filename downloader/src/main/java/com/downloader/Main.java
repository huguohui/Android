package com.downloader;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.downloader.http.Http;
import com.downloader.http.HttpReceiver;
import com.downloader.http.HttpRequest;
import com.downloader.http.HttpResponse;
import com.downloader.util.Writable;

public class Main {
	static long length = 0;
	public static void main(String[] args) throws Exception {
//		SimpleDateFormat s = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
//		s.parse("Mon, 16 Jul 2007 22:23:00");

		new SimpleDateFormat(Http.GMT_DATE_FORMAT[0], Locale.ENGLISH)
			.parse("Mon, 16 Jul 2007 22:23:00 GMT");


		HttpRequest hr = new HttpRequest(new URL("http://www.baidu.com"));
		HttpResponse hs = hr.response();

		HttpReceiver rec = new HttpReceiver(hs, new Writable() {
			@Override
			public void write(byte[] data) throws IOException {
				length += data.length;
				System.out.println(data.length);
			}

			@Override
			public void wirte(byte[] data, int start, int end) throws IOException {
				//System.out.println(new String(data, start, end));
			}
		});
		
		rec.receive();
		System.out.println("Content-Length: " + length);
	}
}
