package com.downloader.client;

import com.downloader.manager.ThreadManager;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Abstract
 */
public class TaskWorker implements ControlableWorker {
	/** Tag for class. */
	public final static String TAG = "TASK_WORKER";

	/** Queue of workables. */
	protected Queue<Workable> mWorkableQueue = new ConcurrentLinkedQueue<>();

	/** A thread for worker. */
	protected Thread mThread;

	/** Is worker paused? */
	protected boolean isPause;

	/** Is worker stoped? */
	protected boolean isStop;


	/**
	 * Private contructor.
	 */
	public TaskWorker() {
		try {
			mThread = ThreadManager.getInstance().create(
								new ThreadManager.ThreadDescriptor(this, TAG));
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}


	/**
	 * Start to doing work.
	 */
	@Override
	public void start() throws Exception {
		if (mThread != null) {
			mThread.start();
		}
	}


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
	 * Get thread of this worker.
	 *
	 * @return A thread of this worker.
	 */
	@Override
	public Thread thread() {
		return mThread;
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


	@Override
	public void run() {
		Workable workable = null;
		while(!isStop) {
			if (isPause) {
				synchronized (this) {
					try { wait(); } catch (Exception e) {
						e.printStackTrace();
					}
				}
				continue;
			}

			try {
				workable = mWorkableQueue.poll();
				if (workable != null)
					workable.work();
				else
					isPause = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
