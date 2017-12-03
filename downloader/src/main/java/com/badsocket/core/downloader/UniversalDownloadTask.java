package com.badsocket.core.downloader;

import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.TaskInfo;
import com.badsocket.worker.AsyncWorker;
import com.badsocket.worker.Worker;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.manager.ThreadManager;
import com.badsocket.net.AsyncSocketReceiver;
import com.badsocket.net.SocketFamilyFactory;
import com.badsocket.net.SocketReceiver;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.SocketResponse;
import com.badsocket.net.WebAddress;
import com.badsocket.util.CollectionUtils;

import java.io.File;
import java.io.IOException;

import static com.badsocket.core.downloader.DownloadHelper.fetchTaskInfo;

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

	protected boolean isResumeFromInfo;

	protected DownloadTaskInfo info;

	protected SocketFamilyFactory factory;

	protected DownloadDescriptor descriptor;

	protected ProtocolHandler handler;

	protected FileWriter writer;

	protected InternetDownloader.ThreadAllocPolicy policy;

	protected Worker worker;

	protected int perThreadExecInterval = 500;

	private CollectionUtils.Action<SocketReceiver> stopAction = (rec) -> {
		rec.stop();
	};


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
		this.info = handler.downloadTaskInfoFactory().create(descriptor);
		init();
	}


	public UniversalDownloadTask(DownloadTaskInfo i, ProtocolHandler handler,
				InternetDownloader.ThreadAllocPolicy policy) throws IOException {
		this(DownloadDescriptor.fromDownloadTaskInfo(i), handler, policy);
		info = i;
		isResumeFromInfo = true;
	}


	protected void init() throws IOException {
		state = State.unstart;
		try {
			worker = new AsyncWorker(ThreadManager.getInstance());
			worker.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected SocketReceiver wrapAsyncReceiver(SocketReceiver re) {
		AsyncSocketReceiver ac = new AsyncSocketReceiver(re, worker);
		ac.setOnFinishedListener(this);
		return ac;
	}


	protected void doTask() throws IOException {
		if (!isResumeFromInfo) {
			info.update(DownloadHelper.fetchTaskInfo(descriptor, handler));
		}
		writer = new ConcurrentFileWriter(new File(info.getPath(), info.getName()));
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
		return (info.getLength() == 0 || info.getProgress() >= 1f);
	}


	@Override
	public synchronized void onFinished(SocketReceiver r) {
		info.update(receivers);
		if (checkFinished()) {
			try {
				worker.stop();
				writer.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (onFinishListener != null) {
					onFinishListener.onTaskFinish(this);
				}
			}
		}
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		state = State.running;
		doTask();
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		super.start();
		worker.add(this);
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		super.pause();
		CollectionUtils.forEach(receivers, stopAction);
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {
		super.resume();
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		CollectionUtils.forEach(receivers, stopAction);
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
