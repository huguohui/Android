package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.manager.factory.ThreadFactory;
import com.badsocket.net.SocketComponentFactory;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.SocketResponse;

import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */

public abstract class DownloadHelper {

	private static final Context context = null;

	public static SocketResponse fetchTaskInfo(DownloadDescriptor desc, ProtocolHandler handler) throws IOException {
		SocketComponentFactory factory = handler.socketFamilyFactory();
		SocketRequest req = factory.createRequest(desc);
		SocketResponse rep = null;
		req.open(desc.getAddress());
		req.send();
		rep = req.response();
		req.close();
		return rep;
	}


	public static void fetchTaskInfo(final DownloadDescriptor desc, ProtocolHandler handler,
				final OnFetchTaskInfoListener listener) throws IOException {
		final SocketComponentFactory factory = handler.socketFamilyFactory();
		final SocketRequest req = factory.createRequest(desc);
		final DownloadTaskInfo info = handler.downloadTaskInfoFactory().create(desc);

		context.getThreadFactory().createThread(() -> {
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
		}).start();
	}


	public interface OnFetchTaskInfoListener {

		void onFetchTaskInfo(DownloadTaskInfo info);

	}
}
