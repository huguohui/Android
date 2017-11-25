package com.badsocket.client;

import com.badsocket.engine.MonitorWatcher;
import com.badsocket.engine.Protocols;
import com.badsocket.engine.downloader.DownloadDescriptor;
import com.badsocket.engine.downloader.DownloadTask;
import com.badsocket.engine.downloader.Downloader;
import com.badsocket.engine.downloader.InternetDownloader;
import com.badsocket.engine.downloader.ProtocolHandler;
import com.badsocket.engine.downloader.factory.DownloadTaskInfoFactory;
import com.badsocket.engine.downloader.factory.HttpDownloadTaskInfoFactory;
import com.badsocket.engine.worker.AbstractWorker;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.net.SocketFamilyFactory;
import com.badsocket.net.WebAddress;
import com.badsocket.engine.downloader.factory.HttpFamilyFactory;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.util.ComputeUtils;
import com.badsocket.util.Log;

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
			"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe",
			"http://file.douyucdn.cn/download/client/douyu_pc_client_v1.0.zip"
	};


	public static void main(String[] args) throws Exception {
		new Main().test2();
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

		d.setParallelTaskNum(1);
/*		d.newTask(new DownloadDescriptor.Builder()
				.setAddress(new WebAddress(new URL(urls[1])))
				.setPath("d:/")
				.build()
		);*/

		d.newTask(new DownloadDescriptor.Builder()
				.setAddress(new WebAddress(new URL(urls[2])))
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
					Log.println(dt.info().getName() + "\t" + dt.info().getProgress() + "\t" + dt.getState());
				}
			}
		});

	}

	public static void println(Object args) {

	}
}