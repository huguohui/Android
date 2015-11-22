package com.tankwar.net.http;

import com.tankwar.net.Body;
import com.tankwar.net.Header;
import com.tankwar.net.Request;
import com.tankwar.net.RequestStateListener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequest extends Http implements Request {
    /**
     * Enum of support request method.
     */
    public static enum Method {
        GET,  //GET method.
        POST, //POST mehtod.
    };

    /**
     * The method of requesting http.
     */
    private Method method = Method.GET;


    /**
     * Connection timeout time.
     * Default time is 3s.
     */
    private long timeout = 3000;


    /**
     * Request state listeners.
     */
    private RequestStateListener listener;


    /**
     * Construct a http request object.
     * @param url Special URL.
     */
	public HttpRequest(URL url, Method method) {
		super(url);
        this.method = method;
        connect();
	}


    /**
     * Open a http connection.
     * @return If success true, else false.
     */
	@Override
	public boolean connect() {
		return connect(getUrl(), this.timeout);
	}

    /**
     * Open a HTTP connection, when success return true,
     * if error, some exception will throws.
     *
     * @param address
     * @return If connect success, return true else false.
     */
    @Override
    public boolean connect(InetAddress address) {
        return false;
    }

    /**
     * Open a HTTP connection by a special URL.
     *
     * @param url The special URL.
     * @return If connect success, return true else false.
     */
    @Override
    public boolean connect(URL url) {
        setUrl(url);
        return connect();
    }


    /**
     * Open a HTTP connection, when success return true,
     * if error, some exception will throws.
     *
     * @param address A internet address.
     * @param timeout Set request timeout.
     * @return If connect success, return true else false.
     */
    @Override
    public boolean connect(InetAddress address, long timeout) {
        return false;
    }

    /**
     * Open a HTTP connection by a special URL.
     *
     * @param url     The special URL.
     * @param timeout Set request timeout.
     * @return If connect success, return true else false.
     */
    @Override
    public boolean connect(URL url, long timeout) {
        try {
            this.connect(new InetSocketAddress(url.getHost(), url.getPort()));
            if (this.isConnected()) {
                this.listener.onConnected();
                return true;
            } else {
                this.listener.onConnectedFail();
            }
        }catch (SocketTimeoutException ex) {
            this.listener.onTimeout();
            this.close();
        }catch (IOException ex) {
            this.listener.onException();
            this.close();
        }
        return false;
    }

    /**
     * Sends http request.
     */
    @Override
    public void send() {
        OutputStream os = null;

        try{
            os = this.getOutputStream();
            os.write(getHttpBody().getContent());
            os.flush();
            this.listener.onSended();
        }catch(IOException ex) {
            this.listener.onSendFailed();
        }finally{
            if (os != null) {
                try{
                    os.close();
                }catch(IOException ex) {
                    this.listener.onException();
                }
            }
        }
    }

    /**
     * Faster sending http request.
     *
     * @param header
     * @param body
     */
    @Override
    public void send(Header header, Body body) {

    }


    /**
     * Close http connection.
     */
    @Override
    public void close() {
        try{
            super.shutdownInput();
            super.shutdownOutput();
            super.close();
        }catch(IOException ex) {
            this.listener.onException();
        }
    }


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    public void setListener(RequestStateListener listener) {
        this.listener = listener;
    }
}
