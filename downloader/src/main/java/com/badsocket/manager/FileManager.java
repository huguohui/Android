package com.badsocket.manager;

import java.io.File;
import java.io.IOException;

/**
 * Created by skyrim on 2017/12/16.
 */

public interface FileManager extends Manager<File> {

	public abstract void createDirectory(File parent, String name) throws IOException;

	public abstract void createDirectory(File dir) throws IOException;

	/**
	 * Delete a file or directory.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	public abstract boolean delete(File obj) throws IOException;

	/**
	 * Delete a file or directory.
	 *
	 * @param idx Index of File.
	 * @return If deleted.
	 */
	public abstract boolean delete(int idx) throws IOException;

	/**
	 * Delete all Files.
	 *
	 * @return If deleted true else false.
	 */
	public abstract void deleteAll() throws IOException;

	/**
	 * Load files in special directory.
	 *
	 * @param dir The special directory.
	 */
	public abstract void loadDirectory(String dir) throws IOException;

	/**
	 * Get a file of managing by name.
	 *
	 * @param name The name of file.
	 * @return file equals given name.
	 */
	public abstract File getByName(String name);
}
