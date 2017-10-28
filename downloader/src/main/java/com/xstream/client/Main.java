package com.xstream.client;

import com.xstream.engine.MonitorWatcher;
import com.xstream.engine.Protocols;
import com.xstream.engine.downloader.DownloadDescriptor;
import com.xstream.engine.downloader.DownloadTask;
import com.xstream.engine.downloader.Downloader;
import com.xstream.engine.downloader.InternetDownloader;
import com.xstream.engine.downloader.ProtocolHandler;
import com.xstream.engine.downloader.factory.DownloadTaskInfoFactory;
import com.xstream.engine.downloader.factory.HttpDownloadTaskInfoFactory;
import com.xstream.engine.worker.AbstractWorker;
import com.xstream.io.writer.ConcurrentFileWriter;
import com.xstream.net.SocketFamilyFactory;
import com.xstream.net.WebAddress;
import com.xstream.engine.downloader.factory.HttpFamilyFactory;
import com.xstream.net.http.HttpReceiver;
import com.xstream.util.ComputeUtils;
import com.xstream.util.Log;

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

		Log.println(ComputeUtils.getFriendlyUnitOfBytes(1024*1024+1024, 2));


//		new Main().test2();

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

		d.newTask(new DownloadDescriptor.Builder()
				.setAddress(new WebAddress(new URL(urls[0])))
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
