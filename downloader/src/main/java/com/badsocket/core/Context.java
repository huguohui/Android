package com.badsocket.core;


import com.badsocket.io.writer.FileWriter;
import com.badsocket.manager.Manager;
import com.badsocket.manager.factory.ThreadFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Context for downloader.
 */
public abstract class Context {


	public abstract Manager getThreadManager();


	public abstract Manager getFileManager();


	public abstract Config getConfig();


	public abstract ThreadFactory getThreadFactory();


	public abstract FileWriter getFileWriter(String path) throws IOException;


	public abstract FileWriter getFileWriter(File path) throws IOException;


	public abstract FileWriter getFileWriter(String path, long size) throws IOException;


	public abstract FileWriter getFileWriter(File path, long size) throws IOException;


}
