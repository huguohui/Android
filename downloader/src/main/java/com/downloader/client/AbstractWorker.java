package com.downloader.client;

import com.downloader.manager.ThreadManager;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Abstract
 */
public abstract class AbstractWorker implements ControlableWorker {
	/** Queue of workables. */
	protected Queue<Workable> mWorkableQueue = new ConcurrentLinkedQueue<>();

	/** Is worker paused? */
	protected boolean isPause;

	/** Is worker stoped? */
	protected boolean isStop;


	/**
	 * Add a workable to working.
	 *
	 * @param workable For working.
	 */
	@Override
	public void add(Workable workable) throws Exception {
		mWorkableQueue.offer(workable);
		resume();
	}


	/**
	 * Remove a workable.
	 *
	 * @param workable Will to removing.
	 */
	@Override
	public Workable remove(Workable workable) {
		boolean remove = false;
		remove = mWorkableQueue.remove(workable);
		return remove ? workable : null;
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		isPause = true;
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {
		synchronized (this) {
			if (isPause) {
				isPause = false;
			}
			notify();
		}
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {
		resume();
		isStop = true;
	}


	protected void doWorks() {
		Workable workable = null;
		try {
			workable = mWorkableQueue.poll();
			if (workable != null) {
				workable.work();
				return;
			}

			isPause = mWorkableQueue.isEmpty() && true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}


	protected void pauseWorker() {
		synchronized (this) {
			try { wait(); } catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void run() {
		while(!isStop) {
			if (isPause) {
				pauseWorker();
				continue;
			}

			doWorks();
		}
	}
}
