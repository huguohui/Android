package com.badsocket;


import com.badsocket.core.DownloadComponentFactory;
import com.badsocket.core.ThreadExecutor;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.net.AsyncReceiver;
import com.badsocket.net.Receiver;
import com.badsocket.net.ReceiverGroup;
import com.badsocket.net.Request;
import com.badsocket.net.RequestGroup;
import com.badsocket.net.Response;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.http.HttpResponse;
import com.badsocket.worker.AbstractWorker;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;

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
	}


	void downloadTest() throws Exception {


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


	public static void println(Object args) {

	}
}
