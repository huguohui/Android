package com.tankwar.engine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The game resource manager.
 * @since 2015/12/14
 */
public class ResourceManager extends FileManager {

	/**
	 * Constructor a file manager instance by a special directory,
	 * if the directory is not exists, file manager will be create
	 * it. If directory contains some files, file manager will be
	 * load them to a list so that it can manage files.
	 *
	 * @param directory The special directory to managing.
	 */
	public ResourceManager(String directory) throws IOException {
		super(directory);
	}

	/**
	 * Constructor a file manager instance by default directory setting.
	 */
	public ResourceManager() throws IOException {
		super();
	}



}
