package com.badsocket.net.http;

import com.badsocket.engine.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Cookie is important thing of http on message transfer.
 * Cookie holds text message, expire time, and special domain.
 *
 * @since 2015/11/24
 */
public class HttpCookie {
    /** Every cookie have a expire time. when cookie expired, we couldn't sent it.*/
    private Date mExpireDate;

    /** A cookie map a domain. */
    private String mDomain;

    private String mPath;

    private boolean isSecure;

    private String mKey;

    private String mValue;
    
    private Long mMaxAge;

    static HttpCookieParser httpCookieParser = new HttpCookieParser();


	private final static SimpleDateFormat mDateFormat = new SimpleDateFormat(Http.GMT_DATE_FORMAT[1], Locale.ENGLISH);
    
    private boolean isKeyValueOnly = false;


    public HttpCookie(String k, String v, String domain, String path, Date expire, long maxAge, boolean isSecure)
            throws Exception {
        mDomain = domain;
        mPath = path;
        mKey = k;
        mValue = v;
        mExpireDate = expire;
        mMaxAge = maxAge;
        this.isSecure = isSecure;
    }
    
    
    public HttpCookie(String k, String v) {
    	mKey = k;
    	mValue = v;
    	isKeyValueOnly = true;
    }


    public static HttpCookie[] formString(String str) {
        return httpCookieParser.parse(str);
    }
    
    
    public String toString() {
    	return mKey + '=' + mValue;
    }


    public Date getExpireDate() {
        return mExpireDate;
    }


    public void setExpireDate(Date expireDate) {
        mExpireDate = expireDate;
    }


    public String getDomain() {
        return mDomain;
    }


    public void setDomain(String domain) {
        mDomain = domain;
    }


    public String getPath() {
        return mPath;
    }


    public void setPath(String path) {
        mPath = path;
    }


    public boolean isSecure() {
        return isSecure;
    }


    public void setSecure(boolean secure) {
        isSecure = secure;
    }


    public String getKey() {
        return mKey;
    }


    public void setKey(String key) {
        mKey = key;
    }


    public String getValue() {
        return mValue;
    }


    public void setValue(String value) {
        mValue = value;
    }


    public Long getmMaxAge() {
		return mMaxAge;
	}


	public void setmMaxAge(Long mMaxAge) {
		this.mMaxAge = mMaxAge;
	}


	public static class HttpCookieParser implements Parser {

        /**
         * Parse data to a kind of format.
         *
         * @param data Provided data.
         * @return Some data.
         */
        @Override
        public HttpCookie[] parse(Object data) {
            if (data == null)
                return new HttpCookie[0];

            String str = (String) data;
            String[] cookieArr = str.contains(Http.CRLF) ? str.split(Http.CRLF) : new String[] { str };
            HttpCookie[] cookies = new HttpCookie[cookieArr.length];

            for (String cs : cookieArr) {
                String[] kvArr = str.split(";");
                boolean secure = false;
                Date date = null;
                long maxAge = 0L;
                String k = null, v = null, domain = null, path = null;
                for (String s : kvArr) {
                    s = s.trim();
                    if (!s.contains("=")) {
                        if (s.equals("secure"))
                            secure = true;
                        else
                            k = s;
                    } else {
                        String[] arr = s.split("=");
                        if (arr[0].equals("domain"))
                            domain = arr[1];
                        else if (arr[0].equals("path"))
                            path = arr[1];
                        else if (arr[0].equals("expires"))
                            try {
                                date = mDateFormat.parse(arr[1]);
                            } catch (ParseException e) {
                            }
                        else if (arr[0].equals("max-age"))
                            maxAge = Long.parseLong(arr[1]);
                        else {
                            k = arr[0];
                            v = arr[1];
                        }
                    }
                }
            }

            return cookies;
        }


        /**
         * Parse data to a kind of format.
         *
         * @param data Provided data.
         * @return Some data.
         */
        @Override
        public HttpCookie[] parse(byte[] data) {
            return parse(new String(data));
        }


        /**
         * Parse data to a kind of format.
         *
         * @param data Provided data.
         * @return Some data.
         */
        @Override
        public Object parse(InputStream data) throws IOException {
            return null;
        }


        /**
         * Parse data to a kind of format.
         *
         * @param data Provided data.
         * @return Some data.
         */
        @Override
        public Object parse(Reader data) throws IOException {
            return null;
        }
    }
}
