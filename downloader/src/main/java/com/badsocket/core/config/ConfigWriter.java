package com.badsocket.core.config;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by skyrim on 2017/12/15.
 */
public interface ConfigWriter extends Closeable {

	Config writer() throws IOException;

}
