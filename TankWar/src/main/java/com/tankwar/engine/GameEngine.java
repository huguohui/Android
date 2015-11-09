package com.tankwar.engine;

import com.tankwar.utils.Log;

/**
 * Client engine implements.
 * @since 2015/11/06
 */
public class GameEngine extends Engine {

    /**
     * Construct a engine instance.
     */
    private GameEngine() {
        this.initialize();
    }

    /**
     * Initialization control.
     */
    @Override
    public void initialize() {
        this.addSubsystem(new WorldSubsystem(this));
        this.addSubsystem(new PhysicalSubsystem(this));
        this.addSubsystem(new GraphicsSubsystem(this));
    }

    /**
     * Start command.
     */
    @Override
    public void start() {
        for (Class<? extends Subsystem> key : this.getSubsystems()) {
            Subsystem subsystem = getSubsystem(key);
            subsystem.start();

        }
    }


    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        try {
            while (!this.isStop()) {
                if (this.isPause()) {
                    wait();
                }
            }

            for (Class<? extends Subsystem> key : this.getSubsystems()) {
                Tick tick = getSubsystem(key);
                tick.tick();
            }
        }catch(InterruptedException ex) {
            Log.e(ex);
        }
    }

    /**
     * Pause command.
     */
    @Override
    public void pause() {
        super.pause();
    }

    /**
     * Resume command.
     */
    @Override
    public void resume() {
        super.resume();
    }

    /**
     * Stop command.
     */
    @Override
    public void stop() {
        super.stop();
    }


    /**
     * Get single instance of engine.
     */
    public static Engine getEngine() {
        return new GameEngine();
    }
}


