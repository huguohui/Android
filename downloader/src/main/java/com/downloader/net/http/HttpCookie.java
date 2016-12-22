package com.downloader.net.http;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie is important thing of http on message transfer.
 * Cookie holds text message, expire time, and special domain.
 *
 * @since 2015/11/24
 */
public class HttpCookie {
    /**
     * The cookie content that sent by server, it always described by key => value.
     * and the content should be less than 4kb.
     */
    private Map<String, String> mContent = new HashMap<>();

    /** Every cookie have a expire time. when cookie expired, we couldn't sent it.*/
    private Date mExpireDate;

    /** A cookie map a domain. */
    private String mDomain;


}
