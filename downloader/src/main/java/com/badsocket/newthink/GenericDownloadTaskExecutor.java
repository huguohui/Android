package com.badsocket.newthink;

import java.util.concurrent.ThreadFactory;

/**
 * Created by skyrim on 2017/12/15.
 */
public class GenericDownloadTaskExecutor extends AbstractDownloadTaskExecutor{

	public GenericDownloadTaskExecutor(int corePoolSize) {
		super(corePoolSize);
	}


	public GenericDownloadTaskExecutor(int corePoolSize, ThreadFactory factory) {
		super(corePoolSize, factory);
	}
}
