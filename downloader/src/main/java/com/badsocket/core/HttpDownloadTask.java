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
		extends AbstractDownloadTask
{

	transient protected RequestGroup requestGroup = new RequestGroup();

	transient protected ReceiverGroup receiverGroup = new ReceiverGroup();

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
		downloadComponentFactory = downloader.getProtocolHandler(PROTOCOL)
				.downloadComponentFactory();
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
		requestGroup.addRequests(reqs);
		requestGroup.openRequests();
		requestGroup.sendRequests();
	}


	protected void createDownloadReceiver() throws IOException {
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


	protected synchronized void downloadData() throws IOException {
		createDownloadRequests();
		createDownloadReceiver();
		downloadDataByReceiver();
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


	protected void flushIO() throws Exception {
		fileWriter.flush();
		fileWriter.close();
	}


	protected synchronized void stopDownload() throws Exception {
		stopReceivers();
		closeRequests();
		interruptExecutor();
		clearGroups();
		for (DownloadSection section : downloadSections) {
			Log.debug(section);
		}
	}


	protected void updateSectionsInfo() {
		Receiver[] receivers = receiverGroup.getReceivers();
		Receiver receiver = null;
		long perReceivedLength = 0,  sectionDownloaded = 0;
		for (int i = 0; i < receivers.length; i++) {
			receiver = receivers[i];
			if (receiver != null) {
				perReceivedLength = receiver.getReceivedLengthFromLast();
				sectionDownloaded = downloadSections[i].getDownloadedLength();
				downloadSections[i].setDownloadedLength(sectionDownloaded + perReceivedLength);
			}
		}
	}


	protected void updateProgressInfo() {
		long totalReceivedLength = 0;
		for (int i = 0; i < downloadSections.length; i++) {
			totalReceivedLength += downloadSections[i].getDownloadedLength();
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
			isFinished = true;
		}

		if (isFinished) {
			onTaskFinish();
		}
	}


	public void afterTaskFinish() throws Exception {
		clearGroups();
		flushIO();
		super.onTaskFinish();
	}


	public void onTaskFinish() {
		Log.debug("Finished download task.");
		try {
			afterTaskFinish();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onCreate(TaskExtraInfo info) throws Exception {
		this.extraInfo = info;
		this.prepare();
		super.onCreate(info);
	}


	@Override
	public void onStart() throws IOException {
		super.onStart();
	}


	@Override
	public void onStop() throws Exception {
		stopDownload();
		super.onStop();
	}


	@Override
	public void onPause() throws Exception {
		stopDownload();
		super.onPause();
	}


	@Override
	public void onResume() throws Exception {
		super.onResume();
	}


	@Override
	public void onStore() {
	}


	@Override
	public void onRestore() {
	}


	@Override
	public void update() {
		try {
			if (state == DownloadTaskState.UNSTART || state == DownloadTaskState.PREPARING) {
				return;
			}

			updateDownloadInfo();
			checkDownloadStatus();
		}
		catch (Exception e) {
			e.printStackTrace();
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public abstract class HttpDownloadTaskExtraInfo extends TaskExtraInfo {


	}
}
