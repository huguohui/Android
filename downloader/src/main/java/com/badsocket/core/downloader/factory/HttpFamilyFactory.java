package com.badsocket.core.downloader.factory;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.HTTPDownloadTask;
import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.DownloadTask;
import com.badsocket.core.downloader.HttpDownloadTask;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.io.writer.Writer;
import com.badsocket.net.SocketComponentFactory;
import com.badsocket.net.SocketReceiver;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.WebAddress;
import com.badsocket.net.http.Http;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.HttpRequest;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Created by skyrim on 2017/10/6.
 */
public class HttpFamilyFactory implements SocketComponentFactory {
	@Override
	public SocketRequest createRequest(SocketAddress d) throws IOException {
		WebAddress address = (WebAddress) d;
		BaseHttpRequest hr = new BaseHttpRequest();
		hr.setAddress(address);
		return hr;
	}


	@Override
	public SocketRequest createRequest(DownloadDescriptor d) throws IOException {
		return createRequest(d.getAddress());
	}


	@Override
	public SocketRequest createRequest(DownloadTask i) throws IOException {
		HTTPDownloadTask hti = (HTTPDownloadTask) i;
		WebAddress address = new WebAddress(hti.getURL());
		return createRequest(address);
	}


	@Override
	public SocketRequest createRequest(DownloadDescriptor d, SocketRequest.Range r) throws IOException {
		BaseHttpRequest hr = (BaseHttpRequest) createRequest(d);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}

	@Override
	public SocketRequest[] createRequest(DownloadTask task, InternetDownloader.ThreadAllocStategy stategy) throws IOException {
		int  num = task.getSections().length != 0 ? task.getSections().length : stategy.alloc(task);
		long length = task.getLength(), blockSize = length / num;
		SocketRequest[] requests = new SocketRequest[num];
		long[] pStart = task.getPartOffsetStart(),
				pLen = task.getPartLength(),
				pDownLen = task.getPartDownloadLength();

		for (int j = 0; j < num; j++) {
			if (pStart != null && pLen != null && pDownLen != null) {
				if (pLen != pDownLen) {
					requests[j] = createRequest(task, new HttpRequest.Range(pStart[j] + pDownLen[j],
							pLen[j] - pDownLen[j]));
				}
				continue;
			}

			requests[j] = createRequest(task, new HttpRequest.Range(j * blockSize,
					num > -~j ? blockSize * -~j : length));
		}

		return requests;
	}


	@Override
	public SocketRequest createRequest(DownloadTask i, SocketRequest.Range r) throws IOException {
		BaseHttpRequest hr = (BaseHttpRequest) createRequest(i);
		hr.setHeader(Http.RANGE, new HttpRequest.Range(r).toString());
		hr.setRange(r);
		return hr;
	}


	@Override
	public SocketReceiver createReceiver(SocketRequest r, Writer w) throws IOException {
		BaseHttpRequest req = (BaseHttpRequest) r;
		HttpReceiver rec = new HttpReceiver((BaseHttpRequest) r, w);
		return rec;
	}


	@Override
	public DownloadTask creatTask(DownloadDescriptor ds) throws Exception {
		return new HTTPDownloadTask(null);
	}
}
