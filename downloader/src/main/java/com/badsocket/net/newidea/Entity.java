package com.badsocket.net.newidea;

import java.io.InputStream;

public interface Entity {

	long size();

	void write(byte[] data);

	void read(byte[] buff);

}
