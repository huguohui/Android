package com.badsocket.manager;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.Task;
import com.badsocket.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager extends AbstractDownloadTaskManager {
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = new DownloadTaskManager();
	/** Max并行的任务数 */
	public final static int MAX_PARALELL_TASK = 5;

	protected int runningTaskNum = 0;

	protected int allTasksNum = 0;

	protected int parallelTaskNum = MAX_PARALELL_TASK;

	protected List<DownloadTask> finishedTasks = new ArrayList<>();

	protected List<DownloadTask> runningTasks = new ArrayList<>();

	protected Operation startOperation = new Operation.StartOperation();

	protected Operation pauseOperation = new Operation.PauseOperation();

	protected Operation resumeOperation = new Operation.ResumeOperation();

	protected Operation stopOperation = new Operation.StopOperation();

	protected CollectionUtils.Filter runnableTaskFilter = new StateFilter.Runnable();

	protected CollectionUtils.Filter runningTaskFilter = new StateFilter.Running();

	protected CollectionUtils.Filter pausedTaskFilter = new StateFilter.Paused();

	protected CollectionUtils.Filter stopedTaskFilter = new StateFilter.Stoped();

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
		return mInstance;
	}


	protected List<DownloadTask> getRunnableTask() {
		return CollectionUtils.filter(mList, runnableTaskFilter);
	}


	protected void startRemainTask() {
		int needStartTask = parallelTaskNum - runningTaskNum;
		List<DownloadTask> tasks = null;
		if (needStartTask > 0 && (tasks = getRunnableTask()).size() != 0) {
			CollectionUtils.forEach(tasks.subList(0, needStartTask), new Operation.StartOperation());
			runningTaskNum += needStartTask;
		}
		else {
			//TODO: all done.
		}
	}


	protected void startTask(DownloadTask t) {
		if (isAutoStart && runningTaskNum < parallelTaskNum) {
			startOperation.doAction(t);
			runningTaskNum++;
		}
		else {
//			t.setState(Task.State.waiting);
		}
	}


	public boolean add(DownloadTask d) {
		synchronized (mList) {
			boolean ret = super.add(d);
//			d.setOnFinishListener(this);
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
			for (int i = 0; i < mList.size(); i++) {
				Task t = mList.get(i);
				if (t != null) {
//					t.setState(Task.State.waiting);
				}
			}

			startRemainTask();
		}
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pauseAll() throws Exception {
		synchronized (mList) {
			CollectionUtils.forEach((DownloadTask[]) null, pauseOperation);
		}
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resumeAll()  {
		synchronized (mList) {
			CollectionUtils.forEach(mList, resumeOperation);
		}
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stopAll() {
		synchronized (mList) {
			CollectionUtils.forEach(mList, stopOperation);
		}
	}


	@Override
	public boolean isAutoStart() {
		return isAutoStart;
	}


	@Override
	public void setAutoStart(boolean is) {
		isAutoStart = is;
	}


	@Override
	public void setParallelTaskNum(int num) {
		parallelTaskNum = num;
	}


	@Override
	public int getParallelTaskNum() {
		return parallelTaskNum;
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


	public void onTaskFinish(Task t) {
		synchronized (mList) {
			mList.remove(t);
			runningTaskNum--;
		}

		synchronized (finishedTasks) {
			finishedTasks.add((DownloadTask) t);
		}

		startRemainTask();
	}


	protected static abstract class Operation implements CollectionUtils.Action<DownloadTask> {
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
//				d.start();
			}
		}


		protected static class StopOperation extends Operation{
			public void exec(DownloadTask d) throws Exception {
//				d.stop();
			}
		}


		protected static class PauseOperation extends Operation {
			public void exec(DownloadTask d) throws Exception {
//				d.pause();
			}
		}


		protected static class ResumeOperation extends Operation {
			public void exec(DownloadTask d) throws Exception {
//				d.resume();
			}
		}
	}


	protected static abstract class StateFilter implements CollectionUtils.Filter<DownloadTask> {
		public boolean filter(DownloadTask d) {
			return check(d);
		}


		public abstract boolean check(DownloadTask d);


		static class Runnable extends StateFilter {
			public boolean check(DownloadTask d) {
				return true; //Task.State.unstart.equals(d.getState()) || Task.State.waiting.equals(d.getState());
			}
		}


		static class Running extends StateFilter {
			public boolean check(DownloadTask d) {
				return false; //Task.State.running.equals(d.getState());
			}
		}


		static class Paused extends StateFilter {
			public boolean check(DownloadTask d) {
				return false;// Task.State.paused.equals(d.getState());
			}
		}


		static class Stoped extends StateFilter {
			public boolean check(DownloadTask d) {
				return true; //Task.State.stoped.equals(d.getState());
			}
		}
	}
}
