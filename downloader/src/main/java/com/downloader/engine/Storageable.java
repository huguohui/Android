package com.downloader.engine;

import java.io.File;
import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */

interface Storageable {


	void storage(File f) throws IOException;


}
