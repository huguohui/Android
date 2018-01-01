package com.badsocket.core;

import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.net.AsyncReceiver;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.Receiver;
import com.badsocket.net.ReceiverGroup;
import com.badsocket.net.Request;
import com.badsocket.net.RequestGroup;
import com.badsocket.net.Response;
import com.badsocket.net.http.HttpResponse;
import com.badsocket.util.DateUtils;
import com.badsocket.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by skyrim on 2017/11/28.
 */

public class HttpDownloadTask
		extends
		AbstractDownloadTask
{

	transient protected RequestGroup requestGroup;

	transient protected ReceiverGroup receiverGroup;

	transient protected List<Future<Long>> receiverFutures = new ArrayList<>();

	transient protected DownloadComponentFactory downloadComponentFactory;

	public static final Protocol PROTOCOL = Protocol.HTTP;


	public HttpDownloadTask(Downloader c, DownloadAddress url) {
		this(c, url, "");
	}


	public HttpDownloadTask(Downloader c, DownloadAddress url, String name) {
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
		downloadComponentFactory = downloader.getProtocolHandler(PROTOCOL).downloadComponentFactory();
	}


	@Override
	public void onCreate(TaskExtraInfo info) throws Exception {
		this.extraInfo = info;
		prepare();
	}


	@Override
	public void onStart() {
		super.onStart();
		startTime = DateUtils.millisTime();
		isRunning = true;
		state = DownloadTaskState.RUNNING;

		try {
			fileWriter = context.getFileWriter(new File(getDownloadPath(), getName()), 0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onStop() {
		super.onStop();
	}


	@Override
	public void onPause() {
		super.onPause();
	}


	@Override
	public void onResume() {
		super.onResume();
	}


	@Override
	public void onStore() {

	}


	@Override
	public void onRestore() {

	}


	@Override
	protected Request openRequest() throws IOException {
		Request req = null;
		if (taskDescriptor != null) {
			req = downloadComponentFactory.createRequest(taskDescriptor);
		}
		else {
			req = downloadComponentFactory.createRequest(downloadAddress);
		}

		return req;
	}


	@Override
	protected void fetchResponse(Request r) throws IOException {
		if (!r.connected()) {
			throw new ConnectException();
		}

		r.send();
		response = r.response();
		r.closed();
	}


	@Override
	protected void fetchInfoFromResponse() {
		HttpResponse hr = (HttpResponse) response;
		name = hr.getFileName();
		length = hr.getContentLength();
	}


	protected void createRequests() throws IOException {
		Request[] reqs = downloadComponentFactory.createRequest(this, downloader.getThreadAllocStategy());
		Receiver recs[] = new Receiver[reqs.length];
		requestGroup = new RequestGroup();
		receiverGroup = new ReceiverGroup();
		requestGroup.addRequests(reqs);
		requestGroup.openRequests();
		requestGroup.sendRequests();
	}


	protected void receiveDataFromResponse() throws IOException {
		Response reps[] = requestGroup.getResponses();
		ThreadExecutor executor = (ThreadExecutor) context.getThreadExecutor();

		AsyncReceiver asyncReceiver = null;
		for (int i = 0; i < reps.length; i++) {
			receiverGroup.addReceiver(downloadComponentFactory.createReceiver(requestGroup.getRequest(i), fileWriter));
			asyncReceiver = new AsyncReceiver(receiverGroup.getReceiver(i));
			//asyncReceiver.setOnFinishedListener(this);
			receiverFutures.add(executor.submit(asyncReceiver));
		}
	}


	protected void startDownloadTask() throws IOException {
		createRequests();
		receiveDataFromResponse();
	}


	private void waitReceiveDone() throws ExecutionException, InterruptedException {
		for (Future<Long> future : receiverFutures) {
			downloadedLength += future.get();
			progress = (float) downloadedLength / (float) length;
		}

		if (length > 0 && downloadedLength == length) {
			isFinished = true;
		}

		if (isFinished) {
			onFinish();
		}
	}


	public void run() {
		try {
			call();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public Task call() throws Exception {
		try {
			startDownloadTask();
			waitReceiveDone();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}


	@Override
	public void update() {
		synchronized (this) {
			downloadedLength = receiverGroup.getTotalReceivedLength();
			progress = (float) downloadedLength / (float) length;
		}
	}


	public abstract class HttpDownloadTaskExtraInfo extends TaskExtraInfo {


	}
}
