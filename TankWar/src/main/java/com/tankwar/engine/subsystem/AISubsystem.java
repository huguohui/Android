package com.tankwar.engine.subsystem;


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
	private List<AIControllable> mAIControllables = new ArrayList<>();

	int count = 0;

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
			for (AIControllable controllable : mAIControllables) {
				controllable.move(MovableEntity.Direction.values()[(int)(Math.random() * 32) % 4]);
			}
			count = 0;
		}
		count++;
    }


	public List<AIControllable> getAIControllables() {
		return mAIControllables;
	}


	public synchronized void addAIControllable(AIControllable AIControllable) {
		mAIControllables.add(AIControllable);
	}



	public synchronized void removeAIControllable(AIControllable AIControllable) {
		mAIControllables.remove(AIControllable);
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


	/**
	 * A entity that can be controlled by AI subsystem.
	 * @since 2015/12/05
	 */
	public interface AIControllable {
		/**
		 * Move a distance on current direction.
		 */
		void move();


		/**
		 * Move a distance by special direction.
		 */
		void move(MovableEntity.Direction direction);


		/**
		 * Stopping!
		 */
		void stop();
	}
}