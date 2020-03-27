package com.badsocket;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.Protocols;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.HttpProtocolHandler;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.Http;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.http.HttpRequest;
import com.badsocket.net.newidea.URI;
import com.badsocket.worker.AbstractWorker;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Test {
	static long length = 0;

	static ThreadLocal<Date> date = new ThreadLocal<>();

	public HttpReceiver mRec = null;
	public AbstractWorker t;
	long time;
	ConcurrentFileWriter fw;
	static String[] urls = {
			"http://a10.pc6.com/lhy9/banliyunshsij.apk"
	};

	public static void main(String[] args) throws Exception {
		System.out.println(2 >> 1);
		System.out.println(1 << 2);
		long a = ~1;
		System.out.println(a + ", " + Long.toBinaryString(a));

	}

	static void debugTest() throws IOException {
		BaseHttpRequest request = new BaseHttpRequest(new URI(urls[0]));
		request.open();
		//request.setHeader(Http.RANGE, new HttpRequest.Range().toString());
	}

	static void test2() throws Exception {
		Downloader d = new InternetDownloader(null);
		d.addProtocolHandler(Protocols.HTTP, new HttpProtocolHandler());

		d.setParallelTaskNum(3);
		d.newTask(new DownloadTaskDescriptor.Builder()
				.setURI(new URI(urls[1]))
				.setPath("d:/")
				.build()
		);

		d.newTask(new DownloadTaskDescriptor.Builder()
				.setURI(new URI(urls[2]))
				.setPath("d:/")
				.build()
		);

		d.start();

		d.addWatcher(new MonitorWatcher() {
			@Override
			public void watch(Object o) {
				Downloader d = (Downloader) o;
				List<DownloadTask> dts = d.taskList();
				for (int i = 0; i < dts.size(); i++) {
					DownloadTask dt = dts.get(i);
					//Log.println(dt.info().getName() + "\t" + dt.info().getProgress() + "\t" + dt.getState());
				}
			}
		});

	}


	class A {
		int val = 0;
	}

	void lockTest() {
		final A a = new A();
		new Thread(() -> {
			synchronized (a) {
				a.val = 100;
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "," + a.val);
			}
		}).start();

		new Thread(() -> {
			synchronized (a) {
				a.val = 200;
				System.out.println(Thread.currentThread().getName() + "," + a.val);
			}
		}).start();
	}

	public static void println(Object args) {

	}
}
