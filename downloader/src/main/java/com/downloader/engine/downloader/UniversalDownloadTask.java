package com.downloader.engine.downloader;

import com.downloader.engine.TaskInfo;
import com.downloader.engine.worker.AsyncWorker;
import com.downloader.engine.worker.Worker;
import com.downloader.io.writer.ConcurrentFileWriter;
import com.downloader.io.writer.FileWriter;
import com.downloader.manager.ThreadManager;
import com.downloader.net.AsyncSocketReceiver;
import com.downloader.net.SocketFamilyFactory;
import com.downloader.net.SocketReceiver;
import com.downloader.net.SocketRequest;
import com.downloader.net.SocketResponse;
import com.downloader.net.WebAddress;
import com.downloader.net.http.HttpReceiver;
import com.downloader.util.CollectionUtil;
import com.downloader.util.Log;
import com.downloader.util.StringUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */
public class UniversalDownloadTask extends DownloadTask implements SocketReceiver.OnFinishedListener {

	protected WebAddress address;

	protected SocketResponse response;

	protected SocketRequest[] requests;

	protected SocketResponse[] responses;

	protected SocketReceiver[] receivers;

	protected Protocols protocol;

	protected int threadNum;

	protected String downloadPath;

	protected int priority;

	protected DownloadTaskInfo info;

	protected SocketFamilyFactory factory;

	protected DownloadDescriptor descriptor;

	protected ProtocolHandler handler;

	protected FileWriter writer;

	protected InternetDownloader.ThreadAllocPolicy policy;

	protected Worker worker;

	protected int perThreadExecInterval = 500;


	public UniversalDownloadTask(DownloadDescriptor d, ProtocolHandler handler,
				InternetDownloader.ThreadAllocPolicy policy) throws IOException {
		super(d);
		address = d.getAddress();
		protocol = Protocols.getProtocol(address.getProtocol());
		threadNum = d.getMaxThread();
		downloadPath = d.getPath();
		priority = d.getPriority();
		descriptor = d;

		this.handler = handler;
		this.policy = policy;
		this.factory = handler.socketFamilyFactory();

		init();
	}


	protected void init() throws IOException {
		info = DownloadHelper.fetchTaskInfo(descriptor, handler);
		worker = new AsyncWorker(ThreadManager.getInstance());
		writer = new ConcurrentFileWriter(new File(info.getPath(), info.getName()));
		try {
			worker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected SocketReceiver wrapAsyncReceiver(SocketReceiver re) {
		AsyncSocketReceiver ac = new AsyncSocketReceiver(re, worker);
		ac.setOnFinishedListener(this);
		return ac;
	}


	protected void doTasks() throws IOException {
		requests = factory.createRequest(info, policy);
		receivers = new SocketReceiver[requests.length];
		info.update(requests);
		for (int i = 0; i < requests.length; i++) {
			SocketRequest req = requests[i];
			req.open();
			req.send();
			receivers[i] = factory.createReceiver(req, writer);
			wrapAsyncReceiver(receivers[i]).receive();
		}
	}


	protected boolean checkFinished() {
		return (info.getLength() == 0 || info.getLength() == info.getDownloadLength());
	}


	@Override
	public void onFinished(SocketReceiver r) {
		info.update(receivers);
		Log.println(info.getProgress());
		if (info.getProgress() >= 1) {
			try {
				worker.stop();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		doTasks();
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		worker.add(this);
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		CollectionUtil.forEach(receivers, new CollectionUtil.Action<SocketReceiver>() {
			@Override
			public void doAction(SocketReceiver o) {
				o.stop();
			}
		});
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {

	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {
		CollectionUtil.forEach(receivers, new CollectionUtil.Action<SocketReceiver>() {
			@Override
			public void doAction(SocketReceiver o) {
				o.stop();
			}
		});
	}


	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public TaskInfo info() {
		if (receivers != null) {
			info.update(receivers);
		}

		return info;
	}
}
