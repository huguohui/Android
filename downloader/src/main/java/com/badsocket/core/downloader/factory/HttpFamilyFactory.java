package com.badsocket.core.downloader.factory;

import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.DownloadTaskInfo;
import com.badsocket.core.downloader.HttpDownloadTaskInfo;
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
	public SocketRequest createRequest(DownloadTaskInfo i) throws IOException {
		HttpDownloadTaskInfo hti = (HttpDownloadTaskInfo) i;
		WebAddress address = hti.getAddress();
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
	public SocketRequest[] createRequest(DownloadTaskInfo i, InternetDownloader.ThreadAllocStategy stategy) throws IOException {
		int  num = i.getTotalThreads() != 0 ? i.getTotalThreads() : stategy.alloc(i);
		long length = i.getLength(), blockSize = length / num;
		SocketRequest[] requests = new SocketRequest[num];
		long[] pStart = i.getPartOffsetStart(),
				pLen = i.getPartLength(),
				pDownLen = i.getPartDownloadLength();

		for (int j = 0; j < num; j++) {
			if (pStart != null && pLen != null && pDownLen != null) {
				if (pLen != pDownLen) {
					requests[j] = createRequest(i, new HttpRequest.Range(pStart[j] + pDownLen[j],
							pLen[j] - pDownLen[j]));
				}
				continue;
			}

			requests[j] = createRequest(i, new HttpRequest.Range(j * blockSize,
					num > -~j ? blockSize * -~j : length));
		}

		return requests;
	}


	@Override
	public SocketRequest createRequest(DownloadTaskInfo i, SocketRequest.Range r) throws IOException {
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
}
