package com.downloader.manager;

import com.downloader.base.Controlable;
import com.downloader.base.DownloadTask;

import java.io.File;
import java.io.IOException;

/**
 * Abstracts for manager of download task.
 */
public abstract class AbstractFileManager extends AbstractManager<File>{
	/**
	 * Create a download task by task descriptor.
	 * @param desc Task descriptor.
	 * @return Download task instance.
	 * @throws Throwable When exception occured.
	 */
	public abstract DownloadTask create(DownloadTaskDescriptor desc) throws Throwable;


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
	public abstract void deleteAll() throws Throwable;
}
