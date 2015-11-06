package com.tankwar.net;

import java.net.InetAddress;
import java.net.URL;

/**
 * Describe a request.
 * @author HGH
 * @since 2015/11/04
 */
public interface Request {
    /**
     * Open a HTTP connection, when success return true,
     * if error, some exception will throws.
     * @return If connect success, return true else false.
     */
    public abstract boolean connect();


    /**
     * Open a HTTP connection, when success return true,
     * if error, some exception will throws.
     * @return If connect success, return true else false.
     */
    public abstract boolean connect(InetAddress address);


    /**
     * Open a HTTP connection by a special URL.
     * @param url The special URL.
     * @return If connect success, return true else false.
     */
    public abstract boolean connect(URL url);


    /**
     * Open a HTTP connection, when success return true,
     * if error, some exception will throws.
     * @param address Internet address.
     * @param timeout Request timeout.
     * @return If connect success, return true else false.
     */
    public abstract boolean connect(InetAddress address, long timeout);


    /**
     * Open a HTTP connection by a special URL.
     * @param url The special URL.
     * @param timeout Request timeout.
     * @return If connect success, return true else false.
     */
    public abstract boolean connect(URL url, long timeout);


    /**
     * Sends http request.
     */
    public abstract void send();


    /**
     * Faster sending http request.
     */
    public abstract void send(Header header, Body body);


    /**
     * Close http connection.
     */
    public abstract void close();


}
