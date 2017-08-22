package com.downloader.http;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss GMT", Locale.ENGLISH);


    public HttpCookie(String domain, String path, String date, String values, boolean isSecure)
            throws Exception {
        String[] keyVal = values.split("=");
        mDomain = domain;
        mPath = path;
        mKey = keyVal[0];
        mValue = keyVal[1];
        this.isSecure = isSecure;

        try {
            mExpireDate = mDateFormat.parse(date);
        }
        catch (ParseException e) {}
    }


    public HttpCookie(String str) throws Exception {
        String[] strArr = str.split(";");
        for (String v : strArr) {
            if (!v.contains("=")) {
                if (v.equals("secure"))
                    isSecure = true;
                else
                    mKey = v;
            }else{
                String[] arr = v.split("=");
                if (arr[0].equals("domain"))
                    mDomain = arr[1];
                else if (arr[0].equals("path"))
                    mPath = arr[1];
                else if (arr[0].equals("expire"))
                    try { mExpireDate = mDateFormat.parse(arr[1]); } catch (ParseException e) {}
                else {
                    mKey = arr[0];
                    mValue = arr[1];
                }
            }
        }

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
}
