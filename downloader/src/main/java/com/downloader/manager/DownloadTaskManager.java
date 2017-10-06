package com.downloader.manager;

import com.downloader.engine.Task;
import com.downloader.engine.downloader.DownloadTask;
import com.downloader.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager extends AbstractDownloadTaskManager {
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = null;
	/** Max并行的任务数 */
	public final static int MAX_PARALELL_TASK = 5;

	protected int runningTasks = 0;

	protected int allTasksNum = 0;

	protected List<DownloadTask> finishedTasks = new ArrayList<>();

	protected Operation startOperation = new Operation.StartOperation();

	protected Operation pauseOperation = new Operation.PauseOperation();

	protected Operation resumeOperation = new Operation.ResumeOperation();

	protected Operation stopOperation = new Operation.StopOperation();

	protected CollectionUtil.Filter unstartTaskFilter = new StateFilter.Unstart();

	protected CollectionUtil.Filter runningTaskFilter = new StateFilter.Running();

	protected CollectionUtil.Filter pausedTaskFilter = new StateFilter.Paused();

	protected CollectionUtil.Filter stopedTaskFilter = new StateFilter.Stoped();

	protected boolean isAutoStart = false;

	
	/**
	 * Private constructor for singleton pattern.
	 */
	private DownloadTaskManager() { }

	
	/**
	 * Gets instance of manager.
	 * @return Instance of manager.
	 */
	public final synchronized static DownloadTaskManager getInstance() {
		if (mInstance == null)
			mInstance = new DownloadTaskManager();

		return mInstance;
	}


	protected List<DownloadTask> getUnstartTask() {
		return CollectionUtil.filter(mList, unstartTaskFilter);
	}


	protected void startRemainTask() {
		List<DownloadTask> neededTasks = getUnstartTask();
		CollectionUtil.forEach(neededTasks, new Operation.StartOperation());
		runningTasks += neededTasks.size();
	}


	protected void startTask(DownloadTask t) {
		if (isAutoStart && runningTasks < MAX_PARALELL_TASK) {
			startOperation.doAction(t);
			runningTasks++;
		}
	}


	public boolean add(DownloadTask d) {
		synchronized (mList) {
			boolean ret = super.add(d);
			startTask(d);
			return ret;
		}
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void startAll() throws Exception {
		synchronized (mList) {
			startRemainTask();
		}
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pauseAll() throws Exception {
		synchronized (mList) {
			CollectionUtil.forEach((DownloadTask[]) null, pauseOperation);
		}
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resumeAll()  {
		synchronized (mList) {
			CollectionUtil.forEach(mList, resumeOperation);
		}
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stopAll() {
		synchronized (mList) {
			CollectionUtil.forEach(mList, stopOperation);
		}
	}


	protected DownloadTask getTask(int i) {
		synchronized (mList) {
			if (mList.isEmpty())
				return null;

			return mList.get(i);
		}
	}


	@Override
	public void start(int i) throws Exception {
		startOperation.exec(getTask(i));
	}


	@Override
	public void pause(int i) throws Exception {
		pauseOperation.exec(getTask(i));
	}


	@Override
	public void resume(int i) throws Exception {
		resumeOperation.exec(getTask(i));
	}


	@Override
	public void stop(int i) throws Exception {
		stopOperation.exec(getTask(i));
	}


	protected static abstract class Operation implements CollectionUtil.Action<DownloadTask> {
		public void doAction(DownloadTask t) {
			try {
				if (t != null) {
					exec(t);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		public abstract void exec(DownloadTask t) throws Exception;


		protected static class StartOperation extends Operation {
			public void exec(DownloadTask d) throws Exception {
				d.start();
			}
		}


		protected static class StopOperation extends Operation{
			public void exec(DownloadTask d) throws Exception {
				d.stop();
			}
		}


		protected static class PauseOperation extends Operation {
			public void exec(DownloadTask d) throws Exception {
				d.pause();
			}
		}


		protected static class ResumeOperation extends Operation {
			public void exec(DownloadTask d) throws Exception {
				d.resume();
			}
		}
	}


	protected static abstract class StateFilter implements CollectionUtil.Filter<DownloadTask> {
		public boolean filter(DownloadTask d) {
			return check(d);
		}


		public abstract boolean check(DownloadTask d);


		static class Unstart extends StateFilter {
			public boolean check(DownloadTask d) {
				return !Task.State.running.equals(d.getState()) && !Task.State.finished.equals(d.getState());
			}
		}


		static class Running extends StateFilter {
			public boolean check(DownloadTask d) {
				return Task.State.running.equals(d.getState());
			}
		}


		static class Paused extends StateFilter {
			public boolean check(DownloadTask d) {
				return Task.State.paused.equals(d.getState());
			}
		}


		static class Stoped extends StateFilter {
			public boolean check(DownloadTask d) {
				return Task.State.stoped.equals(d.getState());
			}
		}
	}
}
