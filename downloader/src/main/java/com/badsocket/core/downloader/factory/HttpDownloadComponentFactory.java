package com.badsocket.core.downloader.factory;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadComponentFactory;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.HttpDownloadTask;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.io.writer.Writer;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.Receiver;
import com.badsocket.net.Request;
import com.badsocket.net.Response;
import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.Http;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.http.HttpRequest;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Created by skyrim on 2017/10/6.
 */
public class HttpDownloadComponentFactory implements DownloadComponentFactory {
	@Override
	public Request createRequest(SocketAddress d) throws IOException {
		DownloadAddress address = (DownloadAddress) d;
		BaseHttpRequest hr = new BaseHttpRequest();
		hr.setAddress(address);
		return hr;
	}


	@Override
	public Request createRequest(DownloadTaskDescriptor d) throws IOException {
		return createRequest(d.getAddress());
	}


	@Override
	public Request createRequest(Response i) throws IOException {
		return null;
	}


	@Override
	public Request createRequest(DownloadTask i) throws IOException {
		return createRequest(i.getDownloadAddress());
	}


	@Override
	public Request createRequest(DownloadTaskDescriptor d, Request.Range r) throws IOException {
		BaseHttpRequest hr = (BaseHttpRequest) createRequest(d);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}

	@Override
	public Request[] createRequest(DownloadTask task, InternetDownloader.ThreadAllocStategy stategy) throws IOException {
		int  num = task.getSections() != null && task.getSections().length != 0 ? task.getSections().length : stategy.alloc(task);
		long length = task.getLength(), blockSize = length / num;
		Request[] requests = new Request[num];
		DownloadTask.DownloadSection[] sections = task.getSections();
		DownloadTask.DownloadSection section = null;

		for (int j = 0; j < num; j++) {
			if (sections != null && sections.length - 1 >= j) {
				section = sections[j];
			}

			if (section != null) {
				if (section.getDownloadedLength() != section.getLength()) {
					requests[j] = createRequest(task,
							new HttpRequest.Range(section.getOffsetBegin() + section.getDownloadedLength(),
									section.getLength() - section.getDownloadedLength()));
				}

				continue;
			}

			requests[j] = createRequest(task, new HttpRequest.Range(j * blockSize,
					num > -~j ? blockSize * -~j : length));
		}

		return requests;
	}


	@Override
	public Request createRequest(DownloadTask i, Request.Range r) throws IOException {
		BaseHttpRequest hr = (BaseHttpRequest) createRequest(i);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}


	@Override
	public Receiver createReceiver(Request r, Writer w) throws IOException {
		BaseHttpRequest req = (BaseHttpRequest) r;
		HttpReceiver rec = new HttpReceiver((BaseHttpRequest) r, w);
		return rec;
	}


	@Override
	public DownloadTask creatDownloadTask(Downloader c, DownloadAddress address) throws IOException {
		return new HttpDownloadTask(c, address);
	}


	@Override
	public DownloadTask creatDownloadTask(Downloader c, DownloadTaskDescriptor d) throws IOException {
		DownloadTask task = new HttpDownloadTask(c, d);
		return task;
	}


	@Override
	public DownloadTask creatDownloadTask(Downloader c, DownloadTaskDescriptor d, Response response) throws IOException {
		return new HttpDownloadTask(c, d, response);
	}

}
