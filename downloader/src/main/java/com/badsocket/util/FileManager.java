package com.badsocket.util;

import com.badsocket.core.Manager;

import java.io.File;
import java.io.IOException;

/**
 * Created by skyrim on 2017/12/16.
 */

public interface FileManager extends Manager<File> {

	public  void createDirectory(File parent, String name) throws IOException;

	public  void createDirectory(String dir) throws IOException;

	/**
	 * Delete a file or directory.
	 *
	 * @param idx Index of File.
	 * @return If deleted.
	 */
	public boolean delete(int idx) throws IOException;

	/**
	 * Delete all Files.
	 *
	 * @return If deleted true else false.
	 */
	public void deleteAll() throws IOException;

	/**
	 * Load files in special directory.
	 *
	 * @param dir The special directory.
	 */
	public void loadDirectory(String dir) throws IOException;

	/**
	 * Get a file of managing by name.
	 *
	 * @param name The name of file.
	 * @return file equals given name.
	 */
	public  File getByName(String name);
}
