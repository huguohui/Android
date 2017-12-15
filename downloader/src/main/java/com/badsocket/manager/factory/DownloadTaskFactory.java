package com.badsocket.manager.factory;

import com.badsocket.core.TaskInfo;
import com.badsocket.core.downloader.Descriptor;
import com.badsocket.core.downloader.DownloadTask;

/**
 * @since 2017/10/3.
 */
public interface DownloadTaskFactory extends Factory<DownloadTask> {

	DownloadTask create();


	DownloadTask create(Descriptor d);


	DownloadTask create(TaskInfo d);

}
