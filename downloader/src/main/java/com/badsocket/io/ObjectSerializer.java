package com.badsocket.io;

import java.io.InputStream;

/**
 * Created by skyrim on 2017/10/6.
 */
public interface ObjectSerializer {


	void serialize(Object o);


	Object unserialize(InputStream is);


}
