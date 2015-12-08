package com.tankwar.engine.subsystem;


import com.tankwar.engine.Controllable;
import com.tankwar.engine.Engine;
import com.tankwar.engine.entity.MovableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 *  The engine AI subsystem that let NPC can do something
 *  like human, the NPC(BOT) can find, attack player.
 *
 *  @since 2015/11/20
 */
public class AISubsystem extends Subsystem implements Engine.StateListener {
	/** The all controllable entity. */
	private List<Controllable> mControllables = new ArrayList<>();

	int count = 0;
	int dir = 0;

    /**
     * Construct a module object by gameContext.
     *
     * @param engine
     */
    public AISubsystem(Engine engine) {
        super(engine);
		engine.addStateListener(this);
    }


    /**
     * Per loop will call this method.
     */
    @Override
    public void update() {
		if (count > 10) {
			if (count > 100) {
				dir = (int)(Math.random() * 32) % 4;
				count = 0;
			}
			for (Controllable controllable : mControllables) {
				controllable.move(MovableEntity.Direction.values()[dir]);
			}
		}
		count++;
    }


	public List<Controllable> getControllables() {
		return mControllables;
	}


	public synchronized void addControllable(Controllable Controllable) {
		mControllables.add(Controllable);
	}



	public synchronized void removeControllable(Controllable Controllable) {
		mControllables.remove(Controllable);
	}


	/**
	 * When engine initialized.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onInitialize(Engine engine) {

	}


	/**
	 * When engine start work.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onStart(Engine engine) {

	}


	/**
	 * When engine pause.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onPause(Engine engine) {

	}


	/**
	 * When engine resume.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onResume(Engine engine) {

	}


	/**
	 * When engine stop work.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onStop(Engine engine) {

	}
}