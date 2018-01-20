package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.DownloadComponentFactory;
import com.badsocket.net.Request;
import com.badsocket.net.Response;

import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */

public abstract class DownloadHelper {

	public static Response fetchResponseByDescriptor(Downloader c, DownloadTaskDescriptor desc, ProtocolHandler handler) throws IOException {
		DownloadComponentFactory factory = handler.downloadComponentFactory();
		Request req = factory.createRequest(desc);
		Response rep = null;
		req.send();
		rep = req.response();
		req.close();

		return rep;
	}


	public static void fetchTaskInfoAsync(Context context, final DownloadTaskDescriptor desc,
				ProtocolHandler handler, final OnFetchTaskInfoListener listener) throws IOException {
		final DownloadComponentFactory factory = handler.downloadComponentFactory();
		final Request req = factory.createRequest(desc);

		context.getThreadFactory().createThread(() -> {
			Response rep = null;
			try {
				req.send();
				rep = req.response();
				req.close();
//				taskExtraInfo.update(rep);
//				listener.onFetchTaskInfo(taskExtraInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}


	public interface OnFetchTaskInfoListener {

		void onFetchTaskInfo(Object info);

	}
}
