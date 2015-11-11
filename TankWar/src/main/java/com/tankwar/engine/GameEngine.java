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
        this.setPower(this.allocThread(this));
    }

    /**
     * Start command.
     */
    @Override
    public void start() {
        this.getPower().start();
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
    public synchronized void pause() {
        this.setIsPause(true);
    }

    /**
     * Resume command.
     */
    @Override
    public synchronized void resume() {
        this.setIsPause(false);
    }

    /**
     * Stop command.
     */
    @Override
    public synchronized void stop() {
        this.setIsStop(true);
    }


    /**
     * Get single instance of engine.
     */
    public static Engine getEngine() {
        return new GameEngine();
    }
}


