package com.tankwar.engine;

import com.tankwar.engine.entity.Entity;
import com.tankwar.engine.subsystem.AISubsystem;
import com.tankwar.engine.subsystem.ControlSubsystem;
import com.tankwar.engine.subsystem.GraphicsSubsystem;
import com.tankwar.engine.subsystem.PhysicalSubsystem;
import com.tankwar.engine.subsystem.Subsystem;
import com.tankwar.engine.subsystem.TimingSubsystem;
import com.tankwar.engine.subsystem.Updatable;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.game.Game;
import com.tankwar.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Engine abstract base class.
 *
 * @author hgh.
 * @since 2015/11/06
 */
public class Engine implements Runnable {
	/**
	 * Start time.
	 */
	private long mStartTime;

	/**
	 * This subsystem whatever enable.
	 */
	private boolean mIsEnable = true;

	/**
	 * Is pause?
	 */
	private boolean mIsPause = false;

	/**
	 * Is stop?
	 */
	private boolean mIsStop = false;

	/**
	 * Is running?
	 */
	private boolean mIsRunning = false;

	/**
	 * Game context.
	 */
	private GameContext mGameContext = GameContext.getGameContext();

	/**
	 * Modules.
	 */
	private Map<Class<? extends Subsystem>, Subsystem> mSubsystems = new HashMap<>();

	/**
	 * All threads.
	 */
	private List<Thread> mAllocedThreads = new ArrayList<>();

	/**
	 * All entity exclude terrain in world.
	 */
	private List<Entity> mEntities = new ArrayList<>();

	/**
	 * All state listeners.
	 */
	private List<StateListener> mStateListeners = new ArrayList<>();

	/**
	 * All updatable objects.
	 */
	private List<Updatable> mUpdatables = new ArrayList<>();

	/**
	 * Singleton instance.
	 */
	private static Engine mEngine;

	/**
	 * The engine power.
	 */
	private Thread mPower;

	/**
	 * The world subsystem.
	 */
	private WorldSubsystem mWorldSubsystem;

	/**
	 * The physical subsystem.
	 */
	private PhysicalSubsystem mPhysicalSubsystem;

	/**
	 * The graphics subsytem.
	 */
	private GraphicsSubsystem mGraphicsSubsystem;

	/**
	 * The control subsystem.
	 */
	private ControlSubsystem mControlSubsystem;

	/**
	 * The timing subsystem.
	 */
	private TimingSubsystem mTimingSubsystem;

	/**
	 * The ai subsystem.
	 */
	private AISubsystem mAISubsystem;

	/**
	 * Constructor.
	 */
	private Engine() {
		this.setPhysicalSubsystem(new PhysicalSubsystem(this));
		this.setGraphicsSubsystem(new GraphicsSubsystem(this));
		this.setWorldSubsystem(new WorldSubsystem(this));
		this.setControlSubsystem(new ControlSubsystem(this));
		this.setTimingSubsystem(new TimingSubsystem(this));
		this.setAISubsystem(new AISubsystem(this));
	}

	/**
	 * Get all subsystems.
	 */
	public Set<Class<? extends Subsystem>> getSubsystems() {
		return this.mSubsystems.keySet();
	}

	/**
	 * Add a subsystem.
	 */
	public synchronized void addSubsystem(Subsystem subsystem) {
		mSubsystems.put(subsystem.getClass(), subsystem);
	}

	/**
	 * Get a subsystem.
	 */
	public synchronized Subsystem getSubsystem(Class<? extends Subsystem> subsystem) {
		return mSubsystems.get(subsystem);
	}

	/**
	 * Get get game instance.
	 */
	public final Game getGame() {
		return this.getGameContext().getGame();
	}

	/**
	 * Get game context.
	 */
	public final GameContext getGameContext() {
		return mGameContext;
	}

	/**
	 * Alloc a thread by a runnable.
	 */
	public synchronized Thread allocThread(Runnable runnable) {
		Thread thread = new Thread(runnable);
		mAllocedThreads.add(thread);
		return thread;
	}

	/**
	 * Add state listener.
	 *
	 * @param stateListener The state listener.
	 */
	public void addStateListener(StateListener stateListener) {
		this.mStateListeners.add(stateListener);
	}

	/**
	 * Initialization control.
	 */
	public void initialize() {
		for (StateListener listener : mStateListeners) {
			listener.onInitialize(this);
		}
		this.setPower(this.allocThread(this));
	}

	/**
	 * Start Engine.
	 */
	public void start() {
		if (mIsRunning) return;

		initialize();
		for (StateListener listener : mStateListeners) {
			listener.onStart(this);
		}
		this.getPower().start();
		this.mIsRunning = true;
	}

	/**
	 * Update all subsystems.
	 */
	public void updateSubsystems() {
		mTimingSubsystem.update();
		mAISubsystem.update();
		mControlSubsystem.update();
		mPhysicalSubsystem.update();
		mWorldSubsystem.update();
		mGraphicsSubsystem.update();
		for (Class<? extends Subsystem> key : this.getSubsystems()) {
			if (key != null)
				getSubsystem(key).update();
		}
	}

	/**
	 * Update all updatable.
	 */
	public void updateAdded() {
		for (Updatable up : mUpdatables) {
			if (up != null) {
				up.update();
			}
		}
	}

	/**
	 * Starts executing the active part of the class' code. This method is
	 * called when a thread is started that has been created with a class which
	 * implements {@code Runnable}.
	 */
	public void run() {
		try {
			while (!this.isStop()) {
				if (this.isPause()) {
					wait();
				}

				updateAdded();
				updateSubsystems();
			}
		} catch (InterruptedException ex) {
			Log.e(ex);
		} finally {
			for (Thread thread : mAllocedThreads) {
				if (thread.getState().equals(Thread.State.TERMINATED)) {
					thread = null;
				}
			}
		}
	}

	/**
	 * Get all created entities.
	 *
	 * @return All created entities.
	 */
	public List<Entity> getEntities() {
		return mEntities;
	}

	/**
	 * Add a entity.
	 *
	 * @param entity A {@link Entity}.
	 */
	public void addEntity(Entity entity) {
		mEntities.add(entity);
	}

	/**
	 * Pause engine.
	 */
	public synchronized void pause() {
		this.mIsPause = true;
	}

	/**
	 * Resume engine.
	 */
	public synchronized void resume() {
		this.mIsPause = false;
	}

	/**
	 * Stop engine.
	 */
	public synchronized void stop() {
		this.mIsStop = true;
	}

	/**
	 * Get single instance of engine.
	 */
	public static Engine getEngine() {
		return new Engine();
	}

	/**
	 * Get pause state.
	 */
	public boolean isPause() {
		return mIsPause;
	}

	/**
	 * Get stop state.
	 */
	public boolean isStop() {
		return mIsStop;
	}

	/**
	 * The game power.
	 *
	 * @param power Game power.
	 */
	public final void setPower(Thread power) {
		mPower = power;
	}

	/**
	 * Get game power.
	 *
	 * @return game power.
	 */
	public final Thread getPower() {
		return mPower;
	}

	/* Get and setter. */
	public AISubsystem getAISubsystem() {
		return mAISubsystem;
	}

	public void setAISubsystem(AISubsystem AISubsystem) {
		mAISubsystem = AISubsystem;
	}

	public TimingSubsystem getTimingSubsystem() {
		return mTimingSubsystem;
	}

	public void setTimingSubsystem(TimingSubsystem timingSubsystem) {
		mTimingSubsystem = timingSubsystem;
	}

	public ControlSubsystem getControlSubsystem() {
		return mControlSubsystem;
	}

	public void setControlSubsystem(ControlSubsystem controlSubsystem) {
		mControlSubsystem = controlSubsystem;
	}

	public PhysicalSubsystem getPhysicalSubsystem() {
		return mPhysicalSubsystem;
	}

	public void setPhysicalSubsystem(PhysicalSubsystem physicalSubsystem) {
		mPhysicalSubsystem = physicalSubsystem;
	}

	public WorldSubsystem getWorldSubsystem() {
		return mWorldSubsystem;
	}

	public void setWorldSubsystem(WorldSubsystem worldSubsystem) {
		mWorldSubsystem = worldSubsystem;
	}

	public GraphicsSubsystem getGraphicsSubsystem() {
		return mGraphicsSubsystem;
	}

	public void setGraphicsSubsystem(GraphicsSubsystem graphicsSubsystem) {
		mGraphicsSubsystem = graphicsSubsystem;
	}

	public long getStartTime() {
		return mStartTime;
	}

	public void setStartTime(long startTime) {
		mStartTime = startTime;
	}

	public boolean isEnable() {
		return mIsEnable;
	}

	public void setIsEnable(boolean isEnable) {
		mIsEnable = isEnable;
	}

	public void setIsPause(boolean isPause) {
		mIsPause = isPause;
	}

	public void setIsStop(boolean isStop) {
		mIsStop = isStop;
	}

	public boolean isRunning() {
		return mIsRunning;
	}

	public void setIsRunning(boolean isRunning) {
		mIsRunning = isRunning;
	}

	public void setGameContext(GameContext gameContext) {
		mGameContext = gameContext;
	}

	public List<Thread> getAllocedThreads() {
		return mAllocedThreads;
	}

	public List<StateListener> getStateListeners() {
		return mStateListeners;
	}

	public void setStateListeners(List<StateListener> stateListeners) {
		mStateListeners = stateListeners;
	}

	public void addUpdatable(Updatable updatable) {
		mUpdatables.add(updatable);
	}

	public List<Updatable> getUpdatables() {
		return mUpdatables;
	}

	/**
	 * Game engine state listener.
	 *
	 * @since 2015/11/06
	 */
	public interface StateListener {
		/**
		 * When engine initialized.
		 *
		 * @param engine engine engine.
		 */
		void onInitialize(Engine engine);

		/**
		 * When engine start work.
		 *
		 * @param engine engine engine.
		 */
		void onStart(Engine engine);

		/**
		 * When engine pause.
		 *
		 * @param engine engine engine.
		 */
		void onPause(Engine engine);

		/**
		 * When engine resume.
		 *
		 * @param engine engine engine.
		 */
		void onResume(Engine engine);

		/**
		 * When engine stop work.
		 *
		 * @param engine engine engine.
		 */
		void onStop(Engine engine);
	}
}