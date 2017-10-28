package com.badsocket.engine.downloader.factory;

import com.badsocket.engine.downloader.DownloadDescriptor;
import com.badsocket.engine.downloader.DownloadTaskInfo;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface DownloadTaskInfoFactory {


	DownloadTaskInfo create(DownloadDescriptor rp);


}
