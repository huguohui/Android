package com.downloader.net.http;

import com.downloader.engine.TaskInfo;
import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTaskInfo;
import com.downloader.engine.downloader.HttpDownloadTaskInfo;
import com.downloader.engine.downloader.InternetDownloader;
import com.downloader.io.writer.Writer;
import com.downloader.net.SocketFamilyFactory;
import com.downloader.net.SocketReceiver;
import com.downloader.net.SocketRequest;
import com.downloader.net.WebAddress;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Created by skyrim on 2017/10/6.
 */
public class HttpFamilyFactory implements SocketFamilyFactory {
	@Override
	public SocketRequest createRequest(SocketAddress d) throws IOException {
		WebAddress address = (WebAddress) d;
		HttpRequest hr = new HttpRequest();
		hr.setAddress(address);
		return hr;
	}


	@Override
	public SocketRequest createRequest(DownloadDescriptor d) throws IOException {
		return createRequest(d.getAddress());
	}


	@Override
	public SocketRequest createRequest(DownloadTaskInfo i) throws IOException {
		HttpDownloadTaskInfo hti = (HttpDownloadTaskInfo) i;
		WebAddress address = new WebAddress(((HttpDownloadTaskInfo) i).getUrl());
		return createRequest(address);
	}


	@Override
	public SocketRequest createRequest(DownloadDescriptor d, SocketRequest.Range r) throws IOException {
		HttpRequest hr = (HttpRequest) createRequest(d);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}

	@Override
	public SocketRequest[] createRequest(DownloadTaskInfo i, InternetDownloader.ThreadAllocPolicy policy) throws IOException {
		int num = policy.alloc(i);
		long length = i.getLength(), blockSize = length / num;
		SocketRequest[] requests = new SocketRequest[num];

		for (int j = 0; j < num; j++) {
			requests[j] = createRequest(i, new HttpRequest.Range(j * blockSize,
					num > -~j ? blockSize * -~j : length));
		}

		return requests;
	}


	@Override
	public SocketRequest createRequest(DownloadTaskInfo i, SocketRequest.Range r) throws IOException {
		HttpRequest hr = (HttpRequest) createRequest(i);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}


	@Override
	public SocketReceiver createReceiver(SocketRequest r, Writer w) throws IOException {
		HttpRequest req = (HttpRequest) r;
		HttpReceiver rec = new HttpReceiver((HttpRequest) r, w);
		return rec;
	}
}
