package com.tankwar.engine;

/**
 * Engine state listener.
 * @since 2015/11/06
 */
public interface EngineStateListener {
    /**
     * When engine start initialize.
     */
    void onInit();


    /**
     * When engine start work.
     */
    void onStart();


    /**
     * When engine stop work.
     */
    void onStop();


    /**
     * When engine
     */
}
