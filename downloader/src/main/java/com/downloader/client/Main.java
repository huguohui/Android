package com.downloader.client;

import com.downloader.engine.MonitorWatcher;
import com.downloader.engine.Protocols;
import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTask;
import com.downloader.engine.downloader.Downloader;
import com.downloader.engine.downloader.InternetDownloader;
import com.downloader.engine.downloader.ProtocolHandler;
import com.downloader.engine.downloader.factory.DownloadTaskInfoFactory;
import com.downloader.engine.downloader.factory.HttpDownloadTaskInfoFactory;
import com.downloader.engine.worker.AbstractWorker;
import com.downloader.io.writer.ConcurrentFileWriter;
import com.downloader.net.SocketFamilyFactory;
import com.downloader.net.WebAddress;
import com.downloader.engine.downloader.factory.HttpFamilyFactory;
import com.downloader.net.http.HttpReceiver;
import com.downloader.util.Log;

import java.net.URL;
import java.util.List;

public class Main {
	static long length = 0;

	public HttpReceiver mRec = null;
	public AbstractWorker t;
	ConcurrentFileWriter fw;
	long time;
	static String[] urls = {
			//"http://www.baidu.com/s?",
			"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe",
			"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe"
	};


	public static void main(String[] args) throws Exception {


		new Main().test2();

//		new Main().test1();
	}


	void test2() throws Exception {
		Downloader d = new InternetDownloader(null);
		d.addProtocolHandler(Protocols.HTTP, new ProtocolHandler() {
			@Override
			public SocketFamilyFactory socketFamilyFactory() {
				return new HttpFamilyFactory();
			}


			@Override
			public DownloadTaskInfoFactory downloadTaskInfoFactory() {
				return new HttpDownloadTaskInfoFactory();
			}
		});

		d.newTask(new DownloadDescriptor.Builder()
				.setAddress(new WebAddress(new URL(urls[1])))
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
					Log.println(dt.info().getName() + "\t" + dt.info().getProgress());
				}
			}
		});


	}



	public static void println(Object args) {

	}
}
