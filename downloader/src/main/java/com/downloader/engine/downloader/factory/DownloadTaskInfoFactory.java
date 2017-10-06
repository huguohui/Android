package com.downloader.engine.downloader.factory;

import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTaskInfo;
import com.downloader.net.SocketResponse;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface DownloadTaskInfoFactory {


	DownloadTaskInfo create(DownloadDescriptor rp);


}
