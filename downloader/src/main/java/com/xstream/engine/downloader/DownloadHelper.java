package com.xstream.engine.downloader;

import com.xstream.net.SocketFamilyFactory;
import com.xstream.net.SocketRequest;
import com.xstream.net.SocketResponse;

import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */

public abstract class DownloadHelper {

	public static DownloadTaskInfo fetchTaskInfo(DownloadDescriptor desc, ProtocolHandler handler) throws IOException {
		SocketFamilyFactory factory = handler.socketFamilyFactory();
		SocketRequest req = factory.createRequest(desc);
		SocketResponse rep = null;
		DownloadTaskInfo info = handler.downloadTaskInfoFactory().create(desc);

		req.open(desc.getAddress());
		req.send();
		rep = req.response();
		req.close();
		info.update(rep);
		return info;
	}


	public static void fetchTaskInfo(final DownloadDescriptor desc, ProtocolHandler handler,
				final OnFetchTaskInfoListener listener) throws IOException {
		final SocketFamilyFactory factory = handler.socketFamilyFactory();
		final SocketRequest req = factory.createRequest(desc);
		final DownloadTaskInfo info = handler.downloadTaskInfoFactory().create(desc);

		new Thread() {
			public void run() {
				SocketResponse rep = null;
				try {
					req.open(desc.getAddress());
					req.send();
					rep = req.response();
					req.close();
					info.update(rep);
					listener.onFetchTaskInfo(info);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}


	public interface OnFetchTaskInfoListener {

		void onFetchTaskInfo(DownloadTaskInfo info);

	}
}
