package com.tankwar.engine;

import com.tankwar.entity.Entity;
import com.tankwar.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Client engine implements.
 * @since 2015/11/06
 */
public class GameEngine extends Engine {
    /** All entity exclude terrain in world. */
    private List<Entity> mEntitys = new ArrayList<>();


    /**
     * Construct a engine instance.
     */
    private GameEngine() {
        getGameContext().setEngine(this);
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
     * Start Engine.
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

                for (Class<? extends Subsystem> key : this.getSubsystems()) {
                    Tick tick = getSubsystem(key);
                    tick.tick();
                }
            }
        }catch(InterruptedException ex) {
            Log.e(ex);
        }
    }


    /**
     * Create a entity and add entity to list.
     */
    public void createTank() {
        //mEntitys.add();
    }


    /**
     * Get all created entities.
     * @return All created entities.
     */
    public List<Entity> getEntitys() {
        return mEntitys;
    }


    /**
     * Pause engine.
     */
    @Override
    public synchronized void pause() {
        this.setIsPause(true);
    }

    /**
     * Resume engine.
     */
    @Override
    public synchronized void resume() {
        this.setIsPause(false);
    }

    /**
     * Stop engine.
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


