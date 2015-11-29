package com.tankwar.net;

/**
 * Requester event listener.
 */
public interface RequestStateListener {
    /**
     * When connected success, call this method.
     */
    void onConnected();


    /**
     * When connected fail, call this method.
     */
    void onConnectedFail();


    /**
     * When prepare to sending request call this method.
     */
    void onPrepareSend();


    /**
     * When sending a request call this method.
     */
    void onSended();


    /**
     * When sending failed, call this method.
     */
    void onSendFailed();


    /**
     * When request timeout, call this method.
     */
    void onTimeout();
}
