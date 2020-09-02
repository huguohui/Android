package com.badsocket;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.GenericDownloadTaskExecutor;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.HttpDownloadTask;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.io.writer.SimpleFileWriter;
import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.newidea.URI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
	static long length = 0;

	static ThreadLocal<Date> date = new ThreadLocal<>();

	public HttpReceiver mRec = null;
	long time;
	ConcurrentFileWriter fw;
	SimpleFileWriter sfw;
	static String[] urls = {
			"http://sta-op.douyucdn.cn/dypc-client/pkg/Douyu_Live_PC_Client/20200813212236902/DouyuLive_8.3.7.5.exe"
	};


	public static void main(String[] args) throws Exception {
//		System.out.println(2 >> 1);
//		System.out.println(1 << 2);
//		long a = ~1;
//		System.out.println(a + ", " + Long.toBinaryString(a));

//		machineTest();
		test2();
	}



	static void machineTest() throws InterruptedException, IOException {
		Machine machine = new Machine(new Engine());
		machine.run();
	}

	static void debugTest() throws IOException {
		BaseHttpRequest request = new BaseHttpRequest(new URI(urls[0]));
		request.open();
		//request.setHeader(Http.RANGE, new HttpRequest.Range().toString());
	}

	static void test2() throws Exception {

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

	public static void println(String args) {
		System.out.println(args);
	}

	static class Machine {
		SimpleFileWriter sfw;
		Engine engine;
		int writeTimeCount = 1000 * 3;
		long startTime;

		protected void prepare() throws IOException {
			sfw = new SimpleFileWriter(new File("D:\\temp\\a.txt"));
		}

		void run() throws InterruptedException {
			println(" Machine run with engine...");
			startTime = System.currentTimeMillis();
			engine.turnOn();
			engine.drive(this);
		}

		void doThings() {
			try {
				println(new String(new Date().toString() + "\r\n"));
				sfw.write(new String(new Date().toString() + "\r\n").getBytes());
				sfw.flush();
				if (System.currentTimeMillis() - startTime > writeTimeCount) {
					sfw.close();
					System.exit(0);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				stop();
			}
		}

		void stop() {
			engine.turnOff();
		}

		Machine(Engine engine) throws IOException {
			this.engine = engine;
			prepare();
		}
	}

	static class Engine {
		List<Machine> machines = new ArrayList<>();

		boolean turnOff;

		long idleTime = 1000;

		Engine() {
		}

		void turnOn() throws InterruptedException {
			rotate();
		}

		void turnOff() {
			turnOff = true;
		}

		void drive(Machine machine) {
			machines.add(machine);
		}

		void driveMachines() {
			for (Machine machine : machines) {
				machine.doThings();
			}
		}

		private void rotate() throws InterruptedException {
			while(!turnOff) {
				Thread.sleep(idleTime);
				driveMachines();
			}
		}
	}

}
