package com.badsocket.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
		ReadableByteChannel channel = Channels.newChannel(src);
		ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
		WritableByteChannel dstChannel = Channels.newChannel(dst);
		while (channel.read(buffer) != -1) {
			buffer.flip();
			dstChannel.write(buffer);
			buffer.clear();
		}

		channel.close();
		dstChannel.close();
		src.close();
		dst.close();
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
