package com.badsocket.manager;

import com.badsocket.core.downloader.DownloadTask;

import java.io.File;
import java.io.IOException;

/**
 * Abstracts for manager of download task.
 */
public abstract class AbstractFileManager extends AbstractManager<File> {

	/**
	 * Create a download task by task descriptor.
	 * @param desc Task descriptor.
	 * @return Download task instance.
	 * @throws Throwable When exception occured.
	 */
	public abstract DownloadTask createDirectory(File parent, String name) throws IOException;


	/**
	 * Delete a object.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	public abstract boolean delete(DownloadTask obj) throws IOException;


	/**
	 * Delete a file or directory.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	public abstract boolean delete(File obj) throws IOException;


	/**
	 * Delete a file or directory.
	 * @param idx Index of File.
	 * @return If deleted.
	 */
	public abstract boolean delete(int idx) throws IOException;



	/**
	 * Delete all Files.
	 * @return If deleted true else false.
	 */
	public abstract void deleteAll() throws IOException;


	/**
	 * Load files in special directory.
	 * @param dir The special directory.
	 */
	public abstract void loadDirectory(String dir) throws IOException;


	/**
	 * Get a file of managing by name.
	 * @param name The name of file.
	 * @return file equals given name.
	 */
	public abstract File getByName(String name);

}
