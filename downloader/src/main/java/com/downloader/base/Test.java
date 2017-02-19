package com.downloader.base;

import java.net.URL;

import com.downloader.client.DownloadTaskManager;
import com.downloader.http.HttpDownloadTask;

public class Test implements AbsReceiver.Listener {
	
	@Override
	public void onStart(AbsReceiver downloader) {
		System.out.println("Start download.");
	}
	
	@Override
	public void onResume(AbsReceiver downloader) {
		
	}
	
	@Override
	public void onReceive(AbsReceiver downloader) {
		System.out.println(downloader.getReceivedLength());
	}
	
	@Override
	public void onPause(AbsReceiver downloader) {
		
	}
	
	@Override
	public void onFinish(AbsReceiver downloader) {
		/*try {
			//Runtime.getRuntime().exec("notepad c:\\users\\admin\\appData\\local\\temp\\0.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		System.out.println(downloader.getRequest().getHeader());
		System.out.println("Finished!.............");
		
		
		System.out.println(1000 / 3 + "," + 1000 % 3);
	}
	
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://w.x.baidu.com/alading/anquan_soft_down_all/35013");
		DownloadTaskManager m = DownloadTaskManager.getInstance();
		m.addTask(new HttpDownloadTask(url));
		m.startTask(0);
		m.monitoring();
	}

	@Override
	public void onStop(AbsReceiver absReceiver) {
		
	}

}
