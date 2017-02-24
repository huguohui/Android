package com.downloader.manager;

import com.downloader.base.AbstractDownloadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File manager is manager based directory, managed files is under special directory.
 * To use file manager by pass a special directory path or pass nothing to instancing it.
 * @since 2015/12/15
 */
public class FileManager extends AbstractFileManager {
	/** The collection of all file. */
	private List<File> mFiles = new ArrayList<>();

	/** The home directory. */
	private String mHomeDirectory = "/";

	/** The special directory. */
	private String mManagedDir = "";

	/** The traversal index of iterator. */
	private int mCurrentIndex = 0;


	/**
	 * Constructor a file manager instance by a special directory,
	 * if the directory is not exists, file manager will be create
	 * it. If directory inArray some files, file manager will be
	 * load them to a list so that it can manage files.
	 *
	 * @param directory The special directory to managing.
	 */
	public FileManager(String directory) throws IOException {
		if (directory == null)
			throw new IllegalArgumentException("The special directory can't null!");

		mManagedDir = directory;
		loadFiles(mManagedDir);
	}


	/**
	 * Constructor a file manager instance by default directory setting.
	 */
	public FileManager() throws IOException {
		loadFiles(mHomeDirectory);
	}


	/**
	 * Check the directory whatever is valid.
	 * @return The directory whatever is valid.
	 */
	public void checkDirectory(String directory) throws IOException {
		File dir = new File(directory);
		String msg = "";

		if (!dir.isDirectory()) {
			msg = String.format("No such a directory %s!", dir.getAbsoluteFile());
		}else if (!dir.canRead()) {
			msg = String.format("The directory %s can't be read!", dir.getAbsoluteFile());
		}

		throw new IOException(msg);
	}


	/**
	 * Load all files to list.
	 */
	public void loadFiles(String dir) throws IOException {
		checkDirectory(dir);
		for (File file : new File(dir).listFiles()) {
			mFiles.add(file);
		}
	}


	/**
	 * Get a managed object by index.
	 *
	 * @param idx Index of object.
	 * @return Managed object.
	 */
	@Override
	public File get(int idx) {
		return null;
	}

	/**
	 * Add a object for management.
	 *
	 * @param obj Object what will to managing.
	 */
	@Override
	public boolean add(File obj) {
		return false;
	}

	/**
	 * Remove a managed object by index.
	 *
	 * @param idx Index of managed object.
	 * @return Removed object or null on remove failed.
	 */
	@Override
	public File remove(int idx) {
		return null;
	}

	/**
	 * Search a file.
	 *
	* @param sf A search condition of object will be searched.
	* @return If searched had result list else null.
			*/
	@Override
	public synchronized List<File> search(SearchFilter sf) {
		if (sf == null) return mFiles;

		List<File> searched = new ArrayList<>();
		for (File file : mFiles) {
			if (mFiles != null && sf.filter(file.getName())) {
				searched.add(file);
			}
		}

		return searched;
	}


	/**
	 * Get a list that contians all managed objects.
	 *
	 * @return A list that contians all managed objects.
	 */
	@Override
	public List<File> getList() {
		return mFiles;
	}

	/**
	 * To create something for managing.
	 *
	 * @param descriptor Data for creating.
	 * @return true for success, false for fail.
	 */
	@Override
	public File create(AbstractDescriptor descriptor) throws Throwable {
		return null;
	}


	/**
	 * Create a download task by task descriptor.
	 *
	 * @param desc Task descriptor.
	 * @return Download task instance.
	 * @throws Throwable When exception occured.
	 */
	@Override
	public AbstractDownloadTask create(DownloadTaskDescriptor desc) throws Throwable {
		return null;
	}

	/**
	 * Delete a object.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public boolean delete(AbstractDownloadTask obj) throws IOException {
		return false;
	}

	/**
	 * Delete a file or directory.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public synchronized boolean delete(File obj) throws IOException {
		if (obj == null || !obj.isFile() && !obj.isDirectory())
			return false;

		if (obj.isDirectory()) {
			if (!obj.canWrite())
				throw new IOException("Can't delete this directory cause the directory is read-only!");

			File[] deletedFiles = obj.listFiles();
			for (File file : deletedFiles) {
				if (!obj.isFile() && !obj.isDirectory())
					continue;

				if (!delete(obj)) return false;
			}
		}

		return !obj.exists() || obj.delete();
	}

	@Override
	public synchronized boolean delete(int idx) throws IOException {
		return false;
	}


	/**
	 * Delete a object.
	 *
	 * @return If deleted true else false.
	 */
	@Override
	public void deleteAll() throws Throwable {

	}


	/**
	 * Make dir(s) by passed parameter.
	 * @param path The directory's path.
	 */
	public boolean makeDir(String path) {
		if (path == null || path.length() == 0)
			return false;

		return new File(path).mkdirs();
	}


	/**
	 * Get a details of object.
	 *
	 * @param name The file name.
	 * @return A details of object.
	 */
	public File getByName(String name) {
		if (name == null) return null;

		for (File file : mFiles) {
			if (file.getName().equals(name)) {
				return file;
			}
		}
		return null;
	}


	/**
	 * Get a instance of iterator.
	 * @return Instance of iterator.
	 */
	@Override
	public Iterator<File> iterator() {
		return mFiles.iterator();
	}
}
