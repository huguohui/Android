package com.tankwar.net;

import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

/**
 * Describe a HTTP header. This header maybe 
 * is request header or response header.
 * 
 * @author HGH
 * @since 2015/11/05
 */
public abstract class Header {
	public Header(InputStream header) {
		
	}


	public Header(Reader header) {
		
	}

	
	public Header(String header) {
	}


	public Header(Map<String, String> header) {

	}
}
