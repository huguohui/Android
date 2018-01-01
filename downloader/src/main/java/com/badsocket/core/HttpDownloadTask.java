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
		extends AbstractDownloadTask
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

		try {
			fileWriter = context.getFileWriter(new File(getDownloadPath(), getName()), 0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onStop() throws Exception {
		super.onStop();
		stopDownload();
	}


	@Override
	public void onPause() throws Exception {
		super.onPause();
		stopDownload();
	}


	@Override
	public void onResume() throws Exception {
		super.onResume();
		downloadData();
	}


	@Override
	public void onStore() {
	}


	@Override
	public void onRestore() {
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
			throw new ConnectException("");
		}

		req.send();
		response = req.response();
		req.closed();
	}


	@Override
	protected void fetchInfoFromResponse() {
		HttpResponse hr = (HttpResponse) response;
		name = hr.getFileName();
		length = hr.getContentLength();
	}


	protected void createDownloadRequests() throws IOException {
		Request[] reqs = downloadComponentFactory.createRequest(this, downloader.getThreadAllocStategy());
		Receiver recs[] = new Receiver[reqs.length];
		requestGroup = new RequestGroup();
		requestGroup.addRequests(reqs);
		requestGroup.openRequests();
		requestGroup.sendRequests();
	}


	protected void createDownloadReceiver() throws IOException {
		Response reps[] = requestGroup.getResponses();
		receiverGroup = new ReceiverGroup();
		AsyncReceiver asyncReceiver = null;
		for (int i = 0; i < reps.length; i++) {
			receiverGroup.addReceiver(downloadComponentFactory.createReceiver(requestGroup.getRequest(i), fileWriter));
		}
	}


	protected void downloadDataByReceiver() {
		AsyncReceiver asyncReceiver = null;
		Receiver[] receivers = receiverGroup.getReceivers();
		ThreadExecutor executor = (ThreadExecutor) context.getThreadExecutor();
		for (int i = 0; i < receivers.length; i++) {
			asyncReceiver = new AsyncReceiver(receiverGroup.getReceiver(i));
			receiverFutures.add(executor.submit(asyncReceiver));
		}
	}


	protected void downloadData() throws IOException {
		createDownloadRequests();
		createDownloadReceiver();
		downloadDataByReceiver();
	}


	protected void resetRequests() throws Exception {
		requestGroup.closeAll();
		requestGroup.clear();
	}


	protected void resetReceivers() throws Exception {
		receiverGroup.stopAll();
		receiverGroup.clear();
	}


	protected void stopDownload() throws Exception {
		resetRequests();
		resetReceivers();
	}


	protected void waitReceiveDone() throws ExecutionException, InterruptedException {
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
			downloadData();
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
			if (receiverGroup == null) {
				return;
			}

			downloadedLength = receiverGroup.getTotalReceivedLength();
			progress = (float) downloadedLength / (float) length;
		}
	}


	public abstract class HttpDownloadTaskExtraInfo extends TaskExtraInfo {


	}
}
