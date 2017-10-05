package com.downloader.engine;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Abstract
 */
public abstract class AbstractWorker implements Worker {
	/** Queue of workables. */
	protected Queue<Workable> mQueue = new ConcurrentLinkedQueue<>();

	/** Is worker paused? */
	protected boolean isPause;

	/** Is worker stoped? */
	protected boolean isStop;

	protected Thread mThread;

	protected OnExceptionListener mOnExceptionListener;

	protected OnWorkDoneListener mOnWorkDoneListener;


	/**
	 * Add a workable to working.
	 *
	 * @param workable For working.
	 */
	@Override
	public void add(Workable workable) {
		mQueue.offer(workable);
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
		remove = mQueue.remove(workable);
		return remove ? workable : null;
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() {
		isPause = true;
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() {
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
	public void stop()  {
		resume();
		isStop = true;
	}


	protected void doWork(Workable w) throws Exception {
		if (w != null)
			w.work();
	}


	protected void doWorks() {
		Workable workable = null;
		try {
			doWork(workable = mQueue.poll());
			isPause = mQueue.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workable != null && mOnWorkDoneListener != null) {
				mOnWorkDoneListener.onWorkDone(this);
			}
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


	public interface OnExceptionListener {
		void onException(Exception e);
	}
}
