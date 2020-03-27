package com.badsocket.core.downloader;

import com.badsocket.core.AbstractDownloadTask;
import com.badsocket.core.DownloadComponentFactory;
import com.badsocket.core.Protocols;
import com.badsocket.core.Task;
import com.badsocket.core.ThreadExecutor;
import com.badsocket.core.downloader.exception.DownloadTaskException;
import com.badsocket.core.downloader.exception.StopDownloadTaskException;
import com.badsocket.net.AsyncReceiver;
import com.badsocket.net.Receiver;
import com.badsocket.net.ReceiverGroup;
import com.badsocket.net.Request;
import com.badsocket.net.RequestGroup;
import com.badsocket.net.Response;
import com.badsocket.net.http.HttpRequest;
import com.badsocket.net.http.HttpResponse;
import com.badsocket.net.newidea.URI;
import com.badsocket.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by skyrim on 2017/11/28.
 */

public class HttpDownloadTask
		extends AbstractDownloadTask {

	private static final long serialVersionUID = 5175561149447466807L;

	transient protected RequestGroup requestGroup;

	transient protected ReceiverGroup receiverGroup;

	transient protected List<Future<Long>> receiverFutures;

	transient protected DownloadComponentFactory downloadComponentFactory;

	public static Protocols PROTOCOL;

	public HttpDownloadTask(Downloader c, URI url) {
		this(c, url, "");
	}

	public HttpDownloadTask(Downloader c, URI url, String name) {
		super(c, url, name);
		init();
	}

	public HttpDownloadTask(Downloader c, DownloadTaskDescriptor d) {
		this(c, d, null);
	}

	public HttpDownloadTask(Downloader c, DownloadTaskDescriptor d, Response r) {
		super(c, d, r);
		init();
	}

	protected void init() {
		PROTOCOL = Protocols.HTTP;
		downloadComponentFactory = downloader.getProtocolHandler(PROTOCOL)
				.downloadComponentFactory();
		requestGroup = new RequestGroup();
		receiverGroup = new ReceiverGroup();
		receiverFutures = new ArrayList<>();
		context = downloader.getDownloaderContext();
		isRunning = false;
		isStoped = true;
	}

	protected void prepare() throws Exception {
		super.prepare();
		prepareDownload();
	}

	protected void prepareDownload() throws Exception {
		fileWriter = context.getFileWriter(new File(getDownloadPath(), getName()), getLength());
	}

	@Override
	protected void prefetchResponse() throws IOException {
		Request req = null;
		if (taskDescriptor != null) {
			req = downloadComponentFactory.createRequest(taskDescriptor);
		}
		else {
			req = downloadComponentFactory.createRequest(downloadAddress);
		}

		if (!req.connected()) {
			throw new ConnectException("未打开下载连接！");
		}

		req.send();
		response = req.response();
		req.close();
	}

	@Override
	protected void fetchInfoFromResponse() {
		HttpResponse hr = (HttpResponse) response;
		name = hr.getFileName();
		length = hr.getContentLength();
	}

	protected void createDownloadRequests() throws IOException, InterruptedException {
		Request[] reqs = downloadComponentFactory.createRequest(this, downloader.getThreadAllocStategy());
		Receiver recs[] = new Receiver[reqs.length];
		Thread[] threads = new Thread[reqs.length];

		for (int i = 0; i < reqs.length; i++) {
			final int idx = i;
			threads[i] = context.getThreadFactory().createThread(new RequestThread((HttpRequest) reqs[idx], requestGroup));
			threads[i].start();
		}

		for (Thread thread : threads) {
			if (Thread.State.RUNNABLE.equals(thread.getState())) {
//				thread.start();
				thread.join();
			}
		}

		requestGroup.addRequests(reqs);
	}

	protected void createDownloadReceiver() throws IOException {
		System.out.println("Rec...............");
		Request reqs[] = requestGroup.getRequests();
		AsyncReceiver asyncReceiver = null;
		for (int i = 0; i < reqs.length; i++) {
			receiverGroup.addReceiver(reqs[i] != null
					? downloadComponentFactory.createReceiver(reqs[i], fileWriter)
							: null);
		}
	}

	protected void downloadDataByReceiver() {
		AsyncReceiver asyncReceiver = null;
		Receiver[] receivers = receiverGroup.getReceivers();
		ThreadExecutor executor = (ThreadExecutor) context.getThreadExecutor();
		for (int i = 0; i < receivers.length; i++) {
			if (receivers[i] != null) {
				asyncReceiver = new AsyncReceiver(receivers[i]);
				receiverFutures.add(executor.submit(asyncReceiver));
			}
		}
	}

	protected void onDownloadStarted() throws Exception {
		isRunning = true;
		isPaused = false;
		isStoped = false;
		state = DownloadTaskState.RUNNING;
		if (action == DownloadTaskAction.START) {
			notifyTaskStarted();
		}
		else {
			notifyTaskResumed();
		}
	}

	protected synchronized void startDownload() throws DownloadTaskException {
		try {
			prepare();
			createDownloadRequests();
			createDownloadReceiver();
			downloadDataByReceiver();
			onDownloadStarted();
		}
		catch (Exception e) {
			state = DownloadTaskState.STOPED;
			throw new DownloadTaskException(e);
		}
	}

	protected void closeRequests() throws Exception {
		requestGroup.closeAll();
	}

	protected void stopReceivers() throws Exception {
		receiverGroup.stopAll();
	}

	protected void clearGroups() {
		receiverGroup.clear();
		requestGroup.clear();
	}

	protected void interruptExecutor() throws Exception {
		for (Future<Long> future : receiverFutures) {
			future.cancel(true);
		}
	}

	protected void releaseResource() throws Exception {
		clearGroups();
		fileWriter.close();
	}

	protected void onDownloadStoped() {
		for (DownloadSection section : downloadSections) {
			Log.d(section.toString());
		}

		isRunning = false;
		if (action == DownloadTaskAction.PAUSE) {
			isPaused = true;
			state = DownloadTaskState.PAUSED;
			notifyTaskPaused();
		}
		else {
			state = DownloadTaskState.STOPED;
			isStoped = true;
			notifyTaskStoped();
		}
	}

	protected synchronized void stopDownload() throws DownloadTaskException {
		try {
			stopReceivers();
			closeRequests();
			interruptExecutor();
			clearGroups();
		}
		catch (Exception e) {
			throw new DownloadTaskException(e);
		}
		finally {
			onDownloadStoped();
		}
	}

	protected void updateSectionsInfo() {
		Receiver[] receivers = receiverGroup.getReceivers();
		Receiver receiver = null;
		long perReceivedLength = 0, sectionDownloaded = 0;
		if (receivers != null) {
			for (int i = 0; i < receivers.length; i++) {
				receiver = receivers[i];
				if (receiver != null) {
					perReceivedLength = receiver.getReceivedLengthFromLast();
					sectionDownloaded = downloadSections[i].getDownloadedLength();
					downloadSections[i].setDownloadedLength(sectionDownloaded + perReceivedLength);
				}
			}
		}
	}

	protected void updateProgressInfo() {
		long totalReceivedLength = 0;
		if (downloadSections != null) {
			for (int i = 0; i < downloadSections.length; i++) {
				totalReceivedLength += downloadSections[i].getDownloadedLength();
			}
		}
		if (totalReceivedLength > 0) {
			downloadedLength = totalReceivedLength;
		}

		progress = (float) downloadedLength / (float) length;
	}

	protected void updateDownloadInfo() {
		updateSectionsInfo();
		updateProgressInfo();
	}

	protected void checkDownloadStatus() {
		if (length < 0 || progress >= 1f) {
			isCompleted = true;
		}

		if (isCompleted) {
			onTaskFinish();
		}
	}

	public void afterTaskComplete() throws Exception {
		releaseResource();
		notifyTaskFinished();
	}

	public void onTaskFinish() {
		try {
			super.onTaskFinish();
			afterTaskComplete();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(TaskExtraInfo info) throws Exception {
		this.extraInfo = info;
		super.onCreate(info);
		notifyTaskCreated();
	}

	@Override
	public void onStart() throws Exception {
		super.onStart();
	}

	@Override
	public void onStop() throws Exception {
		super.onStop();
	}

	@Override
	public void onPause() throws Exception {
		super.onPause();
	}

	@Override
	public void onResume() throws Exception {
		super.onResume();
	}

	@Override
	public void onStore() {
		super.onStore();
	}

	@Override
	public void onRestore(Downloader downloader) {
		super.onRestore(downloader);
		init();
	}

	@Override
	public void update() {
		super.update();
		try {
			updateDownloadInfo();
			checkDownloadStatus();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		call();
	}

	@Override
	public Task call() {
		try {
			switch (action) {
				case DownloadTaskAction.PAUSE:
				case DownloadTaskAction.STOP:
					if (isRunning) {
						stopDownload();
					}
					break;

				case DownloadTaskAction.START:
				case DownloadTaskAction.RESUME:
				case DownloadTaskAction.RESTORE:
					if (isStoped || isPaused || !isRunning) {
						startDownload();
					}
					break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public boolean isPauseSupport() {
		return true;
	}

	public static abstract class HttpDownloadTaskExtraInfo extends TaskExtraInfo {

	}

	private static class RequestThread implements Runnable {

		HttpRequest rq;

		RequestGroup rg;

		public RequestThread(HttpRequest rq, RequestGroup rg) {
			this.rq = rq;
			this.rg = rg;
		}

		@Override
		public void run() {
			HttpRequest httpRequest = rq;
			try {
				if (!httpRequest.connected()) {
					httpRequest.open();
				}
				httpRequest.send();
				rg.addResponse(httpRequest.response());
			}
			catch (Exception e) {
				e.printStackTrace();
				if (httpRequest.connected() && !httpRequest.closed()) {
					try {
						httpRequest.close();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
