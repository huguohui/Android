package com.tankwar.engine.manager;

import com.tankwar.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File manager is manager based directory, managed files is under special directory.
 * To use file manager by pass a special directory path or pass nothing to instancing it.
 * @since 2015/12/15
 */
public class FileManager extends AbstractManager<File> {
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
	 * it. If directory contains some files, file manager will be
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
	 */
	public void checkDirectory(String directory) throws IOException {
		File dir = new File(directory);
		if (dir.exists()) {
			if (!dir.isDirectory())
				throw new RuntimeException("These is a file that name same as the directory!");
		}else if (!dir.isDirectory()) {
			if (!dir.mkdirs())
				throw new RuntimeException("Can't create the directory " + dir.getAbsolutePath() + "!");
		}

		if (!dir.canRead())
			throw new IOException("Can't list files of directory " + dir.getAbsolutePath()
					+ ",Because the directory can't read!");
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
	 * Search a file.
	 *
	* @param schstr A search condition of object will be searched.
	* @return If searched had result list else null.
			*/
	@Override
	public List<File> search(String schstr) {
		if (schstr == null)
			throw new NullPointerException("The search string can't null!");

		if (schstr.equals("")) return mFiles;

		List<File> searched = new ArrayList<>();
		Pattern pattern = Pattern.compile(schstr);
		Matcher matcher = null;
		try{
			for (File file : mFiles) {
				matcher = pattern.matcher(file.getName());
				if (matcher != null && matcher.matches()) {
					searched.add(file);
				}
			}
		}catch(Exception ex) {
			Log.e(ex);
		}
		return searched;
	}

	/**
	 * Get a list that contains all managed objects.
	 *
	 * @return A list that contains all managed objects.
	 */
	@Override
	public List<File> getList() {
		return mFiles;
	}


	/**
	 * Delete a file or directory.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public boolean delete(File obj) throws IOException {
		if (obj == null) return false;
		if (!obj.isFile() && !obj.isDirectory() && !obj.exists())
			return true;

		if (obj.isDirectory()) {
			if (!obj.canWrite())
				throw new IOException("Can't delete this directory cause the directory is read-only!");

			File[] deletedFiles = obj.listFiles();
			for (File file : deletedFiles) {
				if (!obj.isFile() && !obj.isDirectory() && !obj.exists())
					continue;

				if (!delete(obj)) return false;
			}
		}

		return obj.delete();
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
	 * Add a object to management list.
	 *
	 * @param obj The object will add to list.
	 */
	@Override
	public boolean add(File obj) throws IOException {
		if (!obj.isFile() && !obj.isDirectory() && !obj.exists()) {
			if ((obj.isFile() && obj.createNewFile()) || (obj.isDirectory()) && (obj.mkdirs()))
				return mFiles.add(obj);

			return false;
		}

		return mFiles.add(obj);
	}

	/**
	 * Get a details of object.
	 *
	 * @param name The file name.
	 * @return A details of object.
	 */
	@Override
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
	 * Add all object to managed list.
	 *
	 * @param list Object of needing to management.
	 */
	@Override
	public void addAll(List<File> list) {
		mFiles.addAll(list);
	}


	@Override
	public boolean hasNext() {
		return mCurrentIndex < mFiles.size();
	}


	@Override
	public synchronized File next() {
		return mFiles.get(mCurrentIndex++);
	}

	@Override
	public synchronized void remove() {
		mFiles.remove(mCurrentIndex);
	}
}
