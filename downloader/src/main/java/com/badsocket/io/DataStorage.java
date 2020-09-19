package com.badsocket.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface DataStorage {

	void open() throws IOException;

	void write(byte[] bytes) throws IOException;

	void write(ByteBuffer byteBuffer) throws IOException;

	void close() throws IOException;

}
