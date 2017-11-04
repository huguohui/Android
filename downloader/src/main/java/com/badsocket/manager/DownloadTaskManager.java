package com.badsocket.manager;

import com.badsocket.engine.Task;
import com.badsocket.engine.downloader.DownloadTask;
import com.badsocket.util.CollectionUtil;
import com.badsocket.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager extends AbstractDownloadTaskManager implements Task.OnTaskFinishListener {
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

	protected CollectionUtil.Filter runnableTaskFilter = new StateFilter.Runnable();

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
		return mInstance;
	}


	protected List<DownloadTask> getRunnableTask() {
		return CollectionUtil.filter(mList, runnableTaskFilter);
	}


	protected void startRemainTask() {
		int needStartTask = parallelTaskNum - runningTaskNum;
		List<DownloadTask> tasks = null;
		if (needStartTask > 0 && (tasks = getRunnableTask()).size() != 0) {
			CollectionUtil.forEach(tasks.subList(0, needStartTask), new Operation.StartOperation());
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
			t.setState(Task.State.waiting);
		}
	}


	public boolean add(DownloadTask d) {
		synchronized (mList) {
			boolean ret = super.add(d);
			d.setOnFinishListener(this);
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
					t.setState(Task.State.waiting);
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


	@Override
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


		static class Runnable extends StateFilter {
			public boolean check(DownloadTask d) {
				return Task.State.unstart.equals(d.getState()) || Task.State.waiting.equals(d.getState());
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
