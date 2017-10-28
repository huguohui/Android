package com.badsocket.client;

import android.os.Handler;
import android.os.Message;

import com.badsocket.engine.MonitorWatcher;
import com.badsocket.engine.downloader.DownloadTask;
import com.badsocket.engine.downloader.Downloader;

import java.util.List;

/**
 * Created by skyrim on 2017/10/15.
 */

public class DownloadTaskWatcher implements MonitorWatcher {

	protected Handler handler;

	public DownloadTaskWatcher(Handler handler) {
		this.handler = handler;
	}


	protected Message generateMessage(Object obj) {
		Message msg = handler.obtainMessage();
		msg.obj = obj;
		return msg;
	}


	protected void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}


	@Override
	public void watch(Object o) {
		Downloader d = (Downloader) o;
		List<DownloadTask> dts = d.taskList();
		sendMessage(generateMessage(dts));
	}
}
