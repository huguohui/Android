package com.downloader.io;


import java.io.Closeable;
import java.io.IOException;

public interface DataReadable extends Closeable {
	byte read() throws IOException;


	byte[] read(int i) throws IOException;


	int readInt() throws IOException;


	short readShort() throws IOException;


	char readChar() throws IOException;


	long readLong() throws IOException;


	int[] readInt(int i) throws IOException;


	short[] readShort(int i) throws IOException;


	char[] readChar(int i) throws IOException;


	long[] readLong(int i) throws IOException;
}
