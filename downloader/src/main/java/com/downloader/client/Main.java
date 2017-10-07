package com.downloader.client;

import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTask;
import com.downloader.engine.downloader.HttpDownloadTask;
import com.downloader.engine.downloader.HttpDownloader;
import com.downloader.engine.downloader.InternetDownloader;
import com.downloader.engine.downloader.ProtocolHandler;
import com.downloader.engine.downloader.Protocols;
import com.downloader.engine.downloader.factory.DownloadTaskInfoFactory;
import com.downloader.engine.downloader.factory.HttpDownloadTaskInfoFactory;
import com.downloader.engine.worker.AbstractWorker;
import com.downloader.io.writer.ConcurrentFileWriter;
import com.downloader.manager.DownloadTaskManager;
import com.downloader.manager.ThreadManager;
import com.downloader.manager.factory.HttpDownloadTaskFactory;
import com.downloader.net.SocketFamilyFactory;
import com.downloader.net.WebAddress;
import com.downloader.net.http.HttpFamilyFactory;
import com.downloader.net.http.HttpReceiver;
import com.downloader.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
		InternetDownloader d = new InternetDownloader(null);
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


	}


	void test1() throws Exception {
		HttpDownloadTask hdt = (HttpDownloadTask) new HttpDownloadTaskFactory().create(new DownloadDescriptor.Builder()
				.setAddress(new WebAddress(new URL(urls[1])))
				.setPath("d:/")
				.build()
		);

		HttpDownloadTask hdt2 = (HttpDownloadTask) new HttpDownloadTaskFactory().create(new DownloadDescriptor.Builder()
				.setAddress(new WebAddress(new URL(urls[0])))
				.setPath("d:/")
				.build()
		);

		final DownloadTaskManager manager = DownloadTaskManager.getInstance();
		manager.add(hdt);
		//manager.add(hdt2);
		manager.startAll();

		final ThreadManager tm = ThreadManager.getInstance();
		final List<DownloadTask> list = manager.list();
		final int tasks = list.size();

		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				int j = 0;
				for (int i = 0; i < list.size(); i++) {
					Log.println(list.get(i) .info().getProgress());
					if (list.get(i) .info().getProgress() >= 1) {
						j ++;
					}
					if (j >= tasks)
						t.cancel();
				}
			}
		}, 2000, 1000);

		t.schedule(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < tm.list().size(); i++) {
					Log.println(tm.get(i).getId() + "\t" + tm.get(i).getName() + "\t" + tm.get(i).getState().toString());
				}
			}
		}, 3000, 1000);
	}


	public void test3() throws Exception {
		new File("E:\\MyCodes\\Android").list(new FilenameFilter() {
			@Override
			public boolean accept(File file, String s) {
				if (s.contains(".exe"))
					new File(file, s).delete();

				return false;
			}
		});
		time = System.currentTimeMillis() / 1000;
		ThreadManager tm = ThreadManager.getInstance();
		HttpDownloader hd = new HttpDownloader(new URL(urls[2]));
		hd.start();

	}


	public static void println(Object args) {

	}
}
