package com.downloader;

import com.downloader.base.Receiver;
import com.downloader.client.TaskWorker;
import com.downloader.http.Http;
import com.downloader.http.HttpReceiver;
import com.downloader.http.HttpRequest;
import com.downloader.http.HttpResponse;
import com.downloader.util.FileWriter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Main {
	static long length = 0;

	public HttpReceiver mRec = null;
	public TaskWorker t;
	FileWriter fw;


	public static void main(String[] args) throws Exception {
//		SimpleDateFormat s = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
//		s.parse("Mon, 16 Jul 2007 22:23:00");

		new SimpleDateFormat(Http.GMT_DATE_FORMAT[0], Locale.ENGLISH)
				.parse("Mon, 16 Jul 2007 22:23:00 GMT");

		new Main().test();
	}


	public void test() throws Exception {
		String[] urls = {
				"http://www.baidu.com",
				"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe",
				"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe"
		};

		HttpRequest hr = new HttpRequest(new URL(urls[0]));
		HttpResponse hs = hr.response();
		fw = new FileWriter(new File("e:\\"+ hs.getFileName()), hs.getContentLength());
		t = new TaskWorker();
		t.start();

		System.out.println("Content-Length: " + hs.getContentLength());

		mRec = new HttpReceiver(hs, fw);

		mRec.setOnFinishedListener(new Receiver.OnFinishedListener() {
			@Override
			public void onFinished(Receiver r) {

				try {
					t.add(fw);
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




		//Thread.sleep(2000);
		//mRec.stop();
		//t.stop();
	}


	void fileTest() {
		try {
			// create a new RandomAccessFile with filename test
			RandomAccessFile raf = new RandomAccessFile("c:/test.txt", "rw");

			// write something in the file
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
