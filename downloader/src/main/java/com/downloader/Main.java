package com.downloader;

import com.downloader.base.Receiver;
import com.downloader.client.AbstractWorker;
import com.downloader.http.Http;
import com.downloader.http.HttpDownloader;
import com.downloader.http.HttpReceiver;
import com.downloader.http.HttpRequest;
import com.downloader.http.HttpResponse;
import com.downloader.util.ConcurrentFileWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Main {
	static long length = 0;

	public HttpReceiver mRec = null;
	public AbstractWorker t;
	ConcurrentFileWriter fw;
	long time;


	public static void main(String[] args) throws Exception {
//		SimpleDateFormat s = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
//		s.parse("Mon, 16 Jul 2007 22:23:00");

		new SimpleDateFormat(Http.GMT_DATE_FORMAT[0], Locale.ENGLISH)
				.parse("Mon, 16 Jul 2007 22:23:00 GMT");

		new ByteArrayOutputStream(1).write("ssa".getBytes());

//		new Main().test2();
	}

	public void test2() throws Exception {
		String[] urls = {
				"http://www.baidu.com",
				"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe",
				"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe"
		};


		new File("E:\\MyCodes\\Android").list(new FilenameFilter() {
			@Override
			public boolean accept(File file, String s) {
				if (s.contains(".exe"))
					new File(file, s).delete();

				return false;
			}
		});
		time = System.currentTimeMillis() / 1000;
		HttpDownloader hd = new HttpDownloader(new URL(urls[2]));
		hd.start();
	}


	public static void println(Object args) {
	}


	public void test() throws Exception {
		String[] urls = {
				"http://www.baidu.com",
				"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe",
				"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe"
		};

		time = System.currentTimeMillis() / 1000;
		HttpRequest hr = new HttpRequest(new URL(urls[1]));
		HttpResponse hs = hr.response();
		fw = new ConcurrentFileWriter(new File("e:\\"+ hs.getFileName()), hs.getContentLength());

		System.out.println(URLDecoder.decode(hs.getFileName(), "utf-8"));
		System.out.println("Content-Length: " + hs.getContentLength());
		println(fw instanceof ConcurrentFileWriter);


		//mRec = new HttpReceiver(hs, fw, 0);
		mRec.setOnFinishedListener(new Receiver.OnFinishedListener() {
			@Override
			public void onFinished(Receiver r) {
				try {
					println(System.currentTimeMillis() / 1000 - time);
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("\n" + ((HttpReceiver)r).getReceivedLength());
			}
		});

		mRec.setOnStopListener(new Receiver.OnStopListener() {
			@Override
			public void onStop(Receiver r) {
				System.out.println("\n" + ((HttpReceiver)r).getReceivedLength());
			}
		});

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mRec.receive();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	void fileTest() {
		try {
			// create a new RandomAccessFile with filename test
			RandomAccessFile raf = new RandomAccessFile("c:/test.txt", "rw");

			// writeToFile something in the file
			raf.writeUTF("Hello World");

			// set the file pointer at 0 position
			raf.seek(0);

			// print the string
			System.out.println("" + raf.readUTF());

			// print current length
			System.out.println("" + raf.length());

			// set the file length to 30
			raf.setLength(30);

			// print the new length
			System.out.println("" + raf.length());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
