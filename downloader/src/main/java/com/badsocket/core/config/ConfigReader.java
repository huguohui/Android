package com.badsocket.core.config;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by skyrim on 2017/12/15.
 */
public interface ConfigReader extends Closeable {


	Config read() throws IOException;


	Config read(File file) throws IOException;


	Config read(InputStream stream) throws IOException;


}
