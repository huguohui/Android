package com.xstream.engine.downloader.factory;

import com.xstream.engine.downloader.DownloadDescriptor;
import com.xstream.engine.downloader.DownloadTaskInfo;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface DownloadTaskInfoFactory {


	DownloadTaskInfo create(DownloadDescriptor rp);


}
