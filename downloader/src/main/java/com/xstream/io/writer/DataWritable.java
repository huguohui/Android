package com.xstream.io.writer;


import java.io.IOException;

public interface DataWritable extends Writable {


	void writeInt(int i) throws IOException;


	void writeShort(short s) throws IOException;


	void writeLong(long l) throws IOException;


	void writeByte(byte b) throws IOException;


	void writeChar(char c) throws IOException;


	void writeChars(char[] cs) throws IOException;


	void writeChars(char[] cs, int s, int e) throws IOException;


}
