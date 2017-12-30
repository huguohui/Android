package com.badsocket;


import com.badsocket.core.DownloadTask;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.core.downloader.factory.HttpFamilyFactory;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.SocketComponentFactory;
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
//		new Test().test2();
	}


	void test2() throws Exception {
		Downloader d = new InternetDownloader(null);
		d.addProtocolHandler(Protocols.HTTP, new ProtocolHandler() {
			@Override
			public SocketComponentFactory socketFamilyFactory() {
				return new HttpFamilyFactory();
			}
		});

		d.setParallelTaskNum(3);
		d.newTask(new DownloadDescriptor.Builder()
				.setAddress(new DownloadAddress(new URL(urls[1])))
				.setPath("d:/")
				.build()
		);

		d.newTask(new DownloadDescriptor.Builder()
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
//					Log.println(dt.info().getName() + "\t" + dt.info().getProgress() + "\t" + dt.getState());
				}
			}
		});

	}

	public static void println(Object args) {

	}
}
