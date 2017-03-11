package com.tankwar.engine.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The resource manager.
 * @since 2015/12/14
 */
public class ResourceManager extends FileManager {
	/** The sound pool object. */
	private SoundPool mSoundPool = new SoundPool(0, 0, 0);


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


	public InputStream getInputStream(String name) {
		File file = null;
		InputStream fis = null;

		try {
			if ((file = getByName(name)) != null) {
				fis = new FileInputStream(file);
			}
		}catch(IOException e) {
			com.tankwar.util.Log.e(e);
		}

		return fis;
	}


	/**
	 * Get a bitmap object by a special resource name.
	 * @param name The resource name.
	 * @return The bitmap object.
	 */
	public Bitmap getBitmap(String name) {
		Bitmap bitmap = null;
		File file = null;
		if (null == (file = getByName(name)))
			return null;

		return BitmapFactory.decodeFile(file.getAbsolutePath());
	}


	/**
	 * Get a sound resource form sound pool by a special name.
	 * @param name The sound resource name.
	 * @return The id of sound resource in sound pool.
	 */
	public int getSound(String name) {
		File file = null;
		if (null == (file = getByName(name)))
			return -1;

		return mSoundPool.load(file.getAbsolutePath(), 0);
	}


	/**
	 * Get a drawable object by a special name.
	 * @param name The drawable resource name.
	 * @return The drawable resource.
	 */
//	public Drawable getDrawable(String name) {
//	}
}