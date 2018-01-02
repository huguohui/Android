package com.badsocket;


import com.badsocket.core.DownloadTask;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.worker.AbstractWorker;

import java.net.URL;
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
			//"http://www.baidu.com/s?",
			"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe",
			"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe",
			"http://file.douyucdn.cn/download/client/douyu_pc_client_v1.0.zip"
	};


	public static void main(String[] args) throws Exception {
		int i = 0;
		System.out.println(i += 10);
//		new Test().lockTest();
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
				} catch (InterruptedException e) {
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


	void test2() throws Exception {
		Downloader d = new InternetDownloader(null);
	/*	d.addProtocolHandler(Protocol.HTTP, new ProtocolHandler() {
			@Override
			public DownloadComponentFactory socketFamilyFactory() {
				return new HttpFamilyFactory();
			}
		});*/

		d.setParallelTaskNum(3);
		d.newTask(new DownloadTaskDescriptor.Builder()
				.setAddress(new DownloadAddress(new URL(urls[1])))
				.setPath("d:/")
				.build()
		);

		d.newTask(new DownloadTaskDescriptor.Builder()
				.setAddress(new DownloadAddress(new URL(urls[2])))
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
//					Log.println(dt.taskExtraInfo().getName() + "\t" + dt.taskExtraInfo().getProgress() + "\t" + dt.getState());
				}
			}
		});

	}

	public static void println(Object args) {

	}
}
