/*
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
import com.badsocket.net.SocketComponentFactory;
import com.badsocket.net.SocketReceiver;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.SocketResponse;
import com.badsocket.net.WebAddress;
import com.badsocket.util.CollectionUtils;

import java.io.File;
import java.io.IOException;

import static com.badsocket.core.downloader.DownloadHelper.fetchTaskInfo;

*/
/**
 * Created by skyrim on 2017/10/6.
 *//*

public class GenericDownloadTask
	extends DownloadTask
	implements Receiver.OnFinishedListener
{

	protected DownloadAddress downloadAddress;

	protected Response response;

	protected Request[] requests;

	protected Response[] responses;

	protected Receiver[] receivers;

	protected Protocol protocol;

	protected int threadNum;

	protected String downloadPath;

	protected int priority;

	protected boolean isResumeFromInfo;

	protected DownloadTaskInfo taskExtraInfo;

	protected DownloadComponentFactory factory;

	protected DownloadTaskDescriptor descriptor;

	protected ProtocolHandler handler;

	protected FileWriter writer;

	protected InternetDownloader.ThreadAllocStategy stategy;

	protected Worker worker;

	protected int perThreadExecInterval = 500;

	private CollectionUtils.Action<Receiver> stopAction = (rec) -> {
		rec.stop();
	};


	public GenericDownloadTask(DownloadTaskDescriptor d, ProtocolHandler handler,
				InternetDownloader.ThreadAllocStategy stategy) throws IOException {
		super(d);
		downloadAddress = d.getAddress();
		protocol = Protocol.getProtocol(downloadAddress.getProtocol());
		threadNum = d.getMaxThread();
		downloadPath = d.getPath();
		priority = d.getPriority();
		descriptor = d;
		this.handler = handler;
		this.stategy = stategy;
		this.factory = handler.socketFamilyFactory();
		this.taskExtraInfo = handler.downloadTaskInfoFactory().create(descriptor);
		init();
	}


	public GenericDownloadTask(DownloadTaskInfo i, ProtocolHandler handler,
				InternetDownloader.ThreadAllocStategy stategy) throws IOException {
		this(DownloadTaskDescriptor.fromDownloadTaskInfo(i), handler, stategy);
		taskExtraInfo = i;
		isResumeFromInfo = true;
	}


	protected void init() throws IOException {
		state = TaskState.unstart;
		try {
			worker = new AsyncWorker(ThreadManager.getInstance());
			worker.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected Receiver wrapAsyncReceiver(Receiver re) {
		AsyncReceiver ac = new AsyncReceiver(re, worker);
		ac.setOnFinishedListener(this);
		return ac;
	}


	protected void doTask() throws IOException {
		if (!isResumeFromInfo) {
			taskExtraInfo.update(DownloadHelper.fetchResponseByDescriptor(descriptor, handler));
		}

		writer = new ConcurrentFileWriter(new File(taskExtraInfo.getPath(), taskExtraInfo.getName()));
		requests = factory.createRequest(taskExtraInfo, stategy);
		receivers = new Receiver[requests.length];
		taskExtraInfo.update(requests);
		for (int i = 0; i < requests.length; i++) {
			Request req = requests[i];
			req.open();
			req.send();
			receivers[i] = factory.createReceiver(req, writer);
			wrapAsyncReceiver(receivers[i]).receive();
		}
	}


	protected boolean checkFinished() {
		return (taskExtraInfo.getLength() == 0 || taskExtraInfo.getProgress() >= 1f);
	}


	@Override
	public synchronized void onFinished(Receiver r) {
		taskExtraInfo.update(receivers);
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


	*/
/**
	 * To do some work.
	 *//*

	@Override
	public void work() throws Exception {
		state = TaskState.running;
		doTask();
	}


	*/
/**
	 * Controls the task start.
	 *//*

	@Override
	public void start() throws Exception {
		super.start();
		worker.add(this);
	}


	*/
/**
	 * Controls the task pause.
	 *//*

	@Override
	public void pause() throws Exception {
		super.pause();
		CollectionUtils.forEach(receivers, stopAction);
	}


	*/
/**
	 * Controls the task resume.
	 *//*

	@Override
	public void resume() throws Exception {
		super.resume();
	}


	*/
/**
	 * Controls the task stop.
	 *//*

	@Override
	public void stop() throws Exception {
		super.stop();
		CollectionUtils.forEach(receivers, stopAction);
	}


	*/
/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 *//*

	@Override
	public TaskInfo taskExtraInfo() {
		if (receivers != null) {
			taskExtraInfo.update(receivers);
		}

		return taskExtraInfo;
	}
}
*/
