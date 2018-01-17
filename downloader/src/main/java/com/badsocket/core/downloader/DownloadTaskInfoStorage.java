package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;

import java.io.File;
import java.util.List;

/**
 * Created by skyrim on 2018/1/17.
 */

public interface DownloadTaskInfoStorage {


	List<DownloadTask> read() throws Exception;


	void write(List<DownloadTask> tasks) throws Exception;


	void write(DownloadTask task) throws Exception;


	File location();


	void setLocation(File location);


}
