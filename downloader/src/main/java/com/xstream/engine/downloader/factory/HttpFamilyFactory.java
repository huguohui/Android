package com.xstream.engine.downloader.factory;

import com.xstream.engine.downloader.DownloadDescriptor;
import com.xstream.engine.downloader.DownloadTaskInfo;
import com.xstream.engine.downloader.HttpDownloadTaskInfo;
import com.xstream.engine.downloader.InternetDownloader;
import com.xstream.io.writer.Writer;
import com.xstream.net.SocketFamilyFactory;
import com.xstream.net.SocketReceiver;
import com.xstream.net.SocketRequest;
import com.xstream.net.WebAddress;
import com.xstream.net.http.Http;
import com.xstream.net.http.HttpReceiver;
import com.xstream.net.http.HttpRequest;

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
		WebAddress address = hti.getAddress();
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
		int  num = i.getTotalThreads() != 0 ? i.getTotalThreads() : policy.alloc(i);
		long length = i.getLength(), blockSize = length / num;
		SocketRequest[] requests = new SocketRequest[num];
		long[] pStart = i.getPartOffsetStart(),
				pLen = i.getPartLength(),
				pDownLen = i.getPartDownloadLength();

		for (int j = 0; j < num; j++) {
			if (pStart != null && pLen != null && pDownLen != null) {
				requests[j] = createRequest(i, new HttpRequest.Range(pStart[j] + pDownLen[j],
						pLen[j] - pDownLen[j]));
				continue;
			}

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
