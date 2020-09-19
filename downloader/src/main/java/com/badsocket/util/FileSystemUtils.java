package com.badsocket.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by skyrim on 2017/12/16.
 */

public abstract class FileSystemUtils {

	public static void mkdir(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory())
				throw new IOException("Can't create directory, file is exists!");
			return;
		}
		if (!dir.mkdirs()) {
			throw new IOException("Can't create directory!");
		}
	}

	public static void transfer(File src, File dst) throws IOException {
		if (src.isDirectory()) {
			dst = new File(dst, src.getName());
			mkdir(dst);
			for (File file : src.listFiles()) {
				transfer(file, dst);
			}
		}
		else {
			File dstFile = dst.isDirectory() ? new File(dst, src.getName()) : dst;
			try (
					FileChannel srcChannel = new RandomAccessFile(src, "r").getChannel();
					FileChannel dstChannel = new RandomAccessFile(dstFile, "rw").getChannel();
			) {
				srcChannel.transferTo(0, srcChannel.size(), dstChannel);
			}
		}
	}

	public static void copy(InputStream src, OutputStream dst, boolean closeStream) throws IOException {
		try (
				ReadableByteChannel channel = Channels.newChannel(src);
				WritableByteChannel dstChannel = Channels.newChannel(dst);
		) {
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
			while (channel.read(buffer) != -1) {
				buffer.flip();
				dstChannel.write(buffer);
				buffer.clear();
			}
		}
		finally {
			if (closeStream) {
				src.close();
				dst.close();
			}
		}
	}

	public static void copy(InputStream src, OutputStream dst) throws IOException {
		copy(src, dst, true);
	}

	public static void copy(InputStream src, File dst) throws IOException {
		if (src == null || dst == null) {
			throw new IllegalArgumentException("Arguments can't NULL!");
		}
		if (dst.isDirectory()) {
			throw new FileAlreadyExistsException("The path: " + dst + " is a directory!");
		}

		copy(src, new FileOutputStream(dst));
	}

	public static void copy(File src, File dst) throws IOException {
		if (src.isDirectory()) {
			dst = new File(dst, src.getName());
			mkdir(dst);
			for (File file : src.listFiles()) {
				copy(file, dst);
			}
		}
		else {
			File dstFile = dst.isDirectory() ? new File(dst, src.getName()) : dst;
			copy(new FileInputStream(src), dstFile);
		}
	}

	public static void copy(File[] srcs, File dst) throws IOException {
		for (File src : srcs) {
			copy(src, dst);
		}
	}

	public static void copy(File[] srcs, File dst, CopyFrontFilter filter,
							  CopyExceptionHandler eh, CopySuccessHandler sh) throws IOException {
		for (File src : srcs) {
			try {
				if (!filter.doFrontCheck(src, dst)) {
					continue;
				}

				copy(src, dst);
				sh.handleCopySuccess(src, dst);
			}
			catch (Exception e) {
				eh.handleCopyException(src, dst, e);
			}
		}
	}

	public static void copy(File[] srcs, File[] dsts) throws IOException {
		for (int i = 0, j = 0; i < srcs.length; i++, j++) {
			copy(srcs[i], dsts[i]);
		}
	}

	public static void copy(File[] srcs, File[] dsts, CopyFrontFilter filter,
							  CopyExceptionHandler eh, CopySuccessHandler sh) throws IOException {
		for (int i = 0, j = 0; i < srcs.length; i++, j++) {
			File src = srcs[i], dst = dsts[i];
			try {
				if (!filter.doFrontCheck(src, dst)) {
					continue;
				}

				copy(src, dst);
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

		copy(src, dst);
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

	public static boolean delete(File name) throws IOException {
		if (!name.isFile() && !name.isDirectory())
			return false;

		if (name.isDirectory()) {
			if (!name.canWrite() && !name.setWritable(true))
				throw new IOException("Can't delete this directory cause the directory is read-only!");

			File[] deletedFiles = name.listFiles();
			for (File file : deletedFiles) {
				if (!delete(name)) return false;
			}
		}

		return name.delete();
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
