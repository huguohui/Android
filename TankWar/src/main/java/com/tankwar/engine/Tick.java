package com.tankwar.engine;

/**
 * Per game main loop will trigger a tick.
 * @author hgh
 * @since 2015/11/09
 */
public interface Tick {
    /**
     * Per loop will call this method.
     */
    void tick();
}
