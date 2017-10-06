package com.downloader.engine.downloader;

import com.downloader.net.SocketFamilyFactory;
import com.downloader.net.SocketRequest;
import com.downloader.net.SocketResponse;

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
}
