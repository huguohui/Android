package com.badsocket.core.downloader.factory;

import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.DownloadTaskInfo;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface DownloadTaskInfoFactory {


	DownloadTaskInfo create(DownloadDescriptor rp);


}
