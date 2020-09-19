package com.badsocket.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
public abstract class FileChannelHelper {

	public static FileChannel newChannel(File file, String mode) throws FileNotFoundException {
		return new RandomAccessFile(file, mode).getChannel();
	}

	public static FileChannel newChannel(String file, String mode) throws FileNotFoundException {
		return new RandomAccessFile(file, mode).getChannel();
	}
}
