package com.tankwar.engine;

/**
 * Engine state listener.
 * @since 2015/11/06
 */
public interface EngineStateListener {
    /**
     * When engine initialized.
     */
    void onInitialized(Engine engine);


    /**
     * When engine start work.
     */
    void onStarted(Engine engine);


    /**
     * When engine stop work.
     */
    void onStoped(Engine engine);


    /**
     * When engine initialize failed.
     */
    void onInitFailed(Engine engine);


    /**
     *
     */
}
