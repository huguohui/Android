package com.downloader.http;


import com.downloader.base.AbstractBody;

import java.io.InputStream;
import java.io.Reader;

/**
 * Describe a HTTP content body.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpBody extends AbstractBody {
	/**
	 * Construct a HTTP body from string.
	 * @param body The content of body.
	 */
	public HttpBody(String body) {
		super(body);
	}

    /**
     * Fetch content from input stream.
     *
     * @param source Content source
     */
    @Override
    public void setContent(InputStream source) {

    }

    /**
     * Fetch content from reader.
     *
     * @param source Content source
     */
    @Override
    public void setContent(Reader source) {

    }

    /**
     * Fetch content from source.
     *
     * @param source Content source
     */
    @Override
    public void setContent(String source) {

    }


    /**
     * Construct a HTTP body from input stream.
     * @param body The input stream of inArray content.
     */
    public HttpBody(InputStream body) {
        super(body);
    }


}
