package com.downloader.io;


import java.io.IOException;

public interface DataWritable extends Writable {
	void writeInt(int i) throws IOException;


	void writeShort(short s) throws IOException;


	void writeLong(long l) throws IOException;


	void writeByte(byte b) throws IOException;


	void writeByte(char c) throws IOException;
}
