package com.downloader.client;

import com.downloader.client.downloader.DownloadInfoRecoder;
import com.downloader.client.downloader.DownloadTask;
import com.downloader.client.downloader.DownloadTaskInfo;
import com.downloader.engine.AbstractWorker;
import com.downloader.io.ConcurrentFileWriter;
import com.downloader.io.DataWriter;
import com.downloader.manager.ThreadManager;
import com.downloader.net.http.Http;
import com.downloader.client.downloader.HttpDownloader;
import com.downloader.net.http.HttpReceiver;
import com.downloader.util.TimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Main {
	static long length = 0;

	public HttpReceiver mRec = null;
	public AbstractWorker t;
	ConcurrentFileWriter fw;
	long time;


	public static void main(String[] args) throws Exception {
		new SimpleDateFormat(Http.GMT_DATE_FORMAT[0], Locale.ENGLISH)
				.parse("Mon, 16 Jul 2007 22:23:00 GMT");


		DownloadTaskInfo info = new DownloadTaskInfo();
		info.setName("d:\\a.txt");
		info.setLength(10);
		info.setDownloadLength(1);
		info.setStartTime(TimeUtil.getMillisTime());
		info.setUsedTime(TimeUtil.getMillisTime());
		info.setTotalParts(1);
		info.setPartOffsetStart(new long[]{ 1 });
		info.setPartLength(new long[]{ 10 });
		info.setPartDownloadLength(new long[]{ 1 });

		new DownloadInfoRecoder(info).write();
		info = new DownloadInfoRecoder(new File("d:\\a.txt")).read();


		long i = 0;

		System.out.println(new String(new byte[] {0, 0, 0, 45, 0, 0}));

//		new Main().test2();403726925942
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
		ThreadManager tm = ThreadManager.getInstance();
		HttpDownloader hd = new HttpDownloader(new Context() {
			@Override
			protected Object clone() throws CloneNotSupportedException {
				return super.clone();
			}
		}, new URL(urls[2]));
		hd.start();

	}


	public static void println(Object args) {

	}
}
