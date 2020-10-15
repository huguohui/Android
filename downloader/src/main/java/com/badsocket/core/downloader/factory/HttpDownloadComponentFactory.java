package com.badsocket.core.downloader.factory;

import com.badsocket.core.DownloadComponentFactory;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.HttpDownloadTask;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.net.Request;
import com.badsocket.net.Response;
import com.badsocket.net.Receiver;
import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.Http;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.http.HttpRequest;
import com.badsocket.net.http.HttpResponse;
import com.badsocket.net.newidea.URI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyrim on 2017/10/6.
 */
public class HttpDownloadComponentFactory implements DownloadComponentFactory {
	@Override
	public Request createRequest(URI d) throws IOException {
		BaseHttpRequest hr = new BaseHttpRequest();
		hr.open(d);
		return hr;
	}

	@Override
	public Request createRequest(DownloadTaskDescriptor d) throws IOException {
		return createRequest(d.getUri());
	}

	@Override
	public Request createRequest(Response i) throws IOException {
		return createRequest(((HttpResponse) i).getURI());
	}

	@Override
	public Request createRequest(DownloadTask i) throws IOException {
		return createRequest(i.getDownloadURI());
	}

	@Override
	public Request createRequest(DownloadTaskDescriptor d, Request.Range r) throws IOException {
		BaseHttpRequest hr = (BaseHttpRequest) createRequest(d);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}

	@Override
	public Request[] createRequest(DownloadTask task, InternetDownloader.ThreadAllocStategy stategy)
			throws IOException {
		DownloadTask.DownloadSection[] oldSections = task.getSections();
		int num = oldSections != null && oldSections.length != 0 ? oldSections.length : stategy.alloc(task);
		long length = task.size(), blockSize = length / num, curBlockSize = 0,
				offsetBegin = 0, offsetEnd = 0;

		List<Request> requestList = new ArrayList<>();
		DownloadTask.DownloadSection section = null;
		DownloadTask.DownloadSection[] sections = new DownloadTask.DownloadSection[num];

		for (int j = 0; j < num; j++) {
			if (oldSections != null) {
				if (oldSections.length - 1 >= j) {
					section = oldSections[j];
				}

				if (section != null) {
					System.out.println(section.toString());
					if (section.getDownloadedLength() < section.getLength()) {
						requestList.add(createRequest(task,
								new HttpRequest.Range(section.getOffsetBegin() + section.getDownloadedLength(),
										section.getOffsetBegin() + section.getLength())));
					}
				}
			}
			else {
				offsetBegin = j * blockSize;
				offsetEnd = num > -~j ? blockSize * -~j : length;
				curBlockSize = offsetEnd - offsetBegin;
				requestList.add(createRequest(task, new HttpRequest.Range(offsetBegin, offsetEnd)));
				sections[j] = new DownloadTask.DownloadSection(j)
						.setLength(curBlockSize)
						.setOffsetBegin(offsetBegin)
						.setOffsetEnd(offsetEnd);
			}
		}

		if (oldSections == null) {
			task.setSections(sections);
		}

		return requestList.toArray(new Request[0]);
	}

	@Override
	public Request createRequest(DownloadTask i, Request.Range r) throws IOException {
		BaseHttpRequest hr = (BaseHttpRequest) createRequest(i);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}

	@Override
	public Receiver createReceiver(Request r) throws IOException {
		BaseHttpRequest req = (BaseHttpRequest) r;
		HttpReceiver rec = new HttpReceiver((BaseHttpRequest) r, null);
		return rec;
	}

	@Override
	public DownloadTask creatDownloadTask(Downloader c, URI address) throws IOException {
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
