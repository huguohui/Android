package com.badsocket.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by skyrim on 2017/12/16.
 */

public abstract class FileUtils {

	public static void copyTo(InputStream src, OutputStream dst) throws IOException {
		ReadableByteChannel channel = null;
		WritableByteChannel dstChannel = null;
		try {
			channel = Channels.newChannel(src);
			dstChannel = Channels.newChannel(dst);
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
			while (channel.read(buffer) != -1) {
				buffer.flip();
				dstChannel.write(buffer);
				buffer.clear();
			}
		}
		finally {
			if (channel != null) {
				channel.close();
			}
			if (dstChannel != null) {
				dstChannel.close();
			}
			src.close();
			dst.close();
		}
	}


	public static void copyTo(InputStream src, File dst) throws IOException {
		if (src == null || dst == null) {
			throw new IllegalArgumentException("Arguments can't NULL!");
		}
		if (dst.isDirectory()) {
			throw new FileAlreadyExistsException("The path: " + dst + " isn't a directory!");
		}
		if (!dst.getParentFile().exists()) {
			dst.getParentFile().mkdirs();
		}
		if (!dst.exists()) {
			dst.createNewFile();
		}

		copyTo(src, new FileOutputStream(dst));
	}


	public static void copyTo(File src, File dst) throws IOException {
		if (src.isDirectory()) {
			for (File file : src.listFiles()) {
				copyTo(new FileInputStream(file), new File(dst, src.getName()));
			}
		}
		else {
			copyTo(new FileInputStream(src), new File(dst, src.getName()));
		}
	}


	public static void copyTo(File[] srcs, File dst) throws IOException {
		for (File src : srcs) {
			copyTo(src, dst);
		}
	}


	public static void copyTo(File[] srcs, File dst, CopyFrontFilter filter,
			CopyExceptionHandler eh, CopySuccessHandler sh) throws IOException {
		for (File src : srcs) {
			try {
				if (!filter.doFrontCheck(src, dst)) {
					continue;
				}

				copyTo(src, dst);
				sh.handleCopySuccess(src, dst);
			}
			catch (Exception e) {
				eh.handleCopyException(src, dst, e);
			}
		}
	}


	public static void copyTo(File[] srcs, File[] dsts) throws IOException {
		for (int i = 0, j = 0; i < srcs.length; i++, j++) {
			copyTo(srcs[i], dsts[i]);
		}
	}


	public static void copyTo(File[] srcs, File[] dsts, CopyFrontFilter filter,
							  CopyExceptionHandler eh, CopySuccessHandler sh) throws IOException {
		for (int i = 0, j = 0; i < srcs.length; i++, j++) {
			File src = srcs[i], dst = dsts[i];
			try {
				if (!filter.doFrontCheck(src, dst)) {
					continue;
				}

				copyTo(src, dst);
				sh.handleCopySuccess(src, dst);
			}
			catch (Exception e) {
				eh.handleCopyException(src, dst, e);
			}
		}
	}


	public static void moveTo(File src, File dst) throws IOException {
		if (!src.exists()) {
			throw new FileNotFoundException(src.getAbsolutePath());
		}
		if (dst.exists()) {
			throw new FileAlreadyExistsException(dst.getAbsolutePath());
		}

		copyTo(src, dst);
		src.delete();
	}


	public static void renameFile(File src, File dst) throws IOException {
		if (!src.exists()) {
			throw new FileNotFoundException(src.getAbsolutePath());
		}
		if (dst.exists()) {
			throw new FileAlreadyExistsException(dst.getAbsolutePath());
		}

		src.renameTo(dst);
	}


	public static File[] listDirectory(File dir) {
		File[] files = new File[0];
		if (dir.exists()) {
			files = dir.listFiles();
		}

		return files;
	}


	public static File[] listDirectory(String dir) {
		return listDirectory(new File(dir));
	}


	public static <T> T readObject(File file) throws IOException, ClassNotFoundException {
		T object = null;
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			object = (T) inputStream.readObject();
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return object;
	}


	public static void writeObject(Object obj, File file) throws IOException {
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(obj);
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}


	public interface CopyExceptionHandler {
		void handleCopyException(File src, File dst, Exception e);
	}


	public interface CopySuccessHandler {
		void handleCopySuccess(File src, File dst);
	}


	public interface CopyFrontFilter {
		boolean doFrontCheck(File src, File dst);
	}



}
