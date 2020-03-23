package com.badsocket.manager;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.Task;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.executor.DownloadTaskExecutor;
import com.badsocket.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Tool for management download task.
 *
 * @since 2016/12/26 15:45
 */
public class DefaultDownloadTaskManager
		extends
		AbstractManager<DownloadTask>
		implements
		DownloadTaskManager, Task.OnTaskFinishListener, Task.OnTaskStartListener,
		Task.OnTaskStopListener, DownloadTask.OnDownloadTaskPauseListener,
		DownloadTask.OnDownloadTaskResumeListener {
	/**
	 * Instance of manager.
	 */
	private static DefaultDownloadTaskManager mInstance = null;
	/**
	 * Max并行的任务数
	 */
	public final static int MAX_PARALELL_TASK = 5;

	protected int runningTaskNum = 0;

	protected int allTasksNum = 0;

	protected int parallelTaskNum = MAX_PARALELL_TASK;

	protected List<DownloadTask> completedTasks = new ArrayList<>();

	protected List<DownloadTask> uncompleteTasks = new ArrayList<>();

	protected boolean isAutoStart = false;

	protected Downloader downloader;

	protected DownloadTaskExecutor taskExecutor;

	public enum Controller {
		START, PAUSE, RESUME, STOP
	}

	/**
	 * Private constructor for singleton pattern.
	 */
	private DefaultDownloadTaskManager(Downloader downloader) {
		this.downloader = downloader;
		taskExecutor = downloader.getDownloaderContext().getDownloadTaskExecutor();
	}

	/**
	 * Gets instance of manager.
	 *
	 * @return Instance of manager.
	 */
	public final synchronized static DefaultDownloadTaskManager getInstance(Downloader downloader) {
		synchronized (DefaultDownloadTaskManager.class) {
			if (mInstance == null) {
				mInstance = new DefaultDownloadTaskManager(downloader);
			}
			return mInstance;
		}
	}

	protected List<DownloadTask> getRunnableTask() {
		return CollectionUtils.filter(mList, (o) -> {
			return o.getState() == DownloadTask.DownloadTaskState.UNSTART;
		});
	}

	protected void startRemainTask() throws Exception {
		int needStartTask = parallelTaskNum - runningTaskNum;
		List<DownloadTask> tasks = null;
		if (needStartTask > 0 && (tasks = getRunnableTask()).size() != 0) {
			uncompleteTasks.addAll(tasks);
			taskControll(Controller.START, tasks.subList(0, needStartTask));
			runningTaskNum += needStartTask;
		}
		else {
			//TODO: all done.
		}
	}

	protected void executeTask(DownloadTask task) throws Exception {
		taskExecutor.start(task);
		uncompleteTasks.add(task);
	}

	protected void pauseTask(DownloadTask task) throws Exception {
		taskExecutor.pause(task);
	}

	protected void resumeTask(DownloadTask task) throws Exception {
		taskExecutor.resume(task);
	}

	protected void stopTask(DownloadTask task) throws Exception {
		taskExecutor.stop(task);
	}

	protected void taskControll(Controller controller, List<DownloadTask> dts) throws Exception {
		for (DownloadTask dt : dts) {
			taskControll(controller, dt);
		}
	}

	protected void taskControll(Controller controller, DownloadTask dt) throws Exception {
		switch (controller) {
			case START:
				startTask(dt);
				break;
			case STOP:
				stopTask(dt);
				break;
			case PAUSE:
				pauseTask(dt);
				break;
			case RESUME:
				resumeTask(dt);
				break;
		}
	}

	protected void startTask(DownloadTask t) throws Exception {
		if (runningTaskNum < parallelTaskNum) {
			executeTask(t);
			runningTaskNum++;
		}
		else {
		}
	}

	public boolean add(DownloadTask task) throws Exception {
		boolean isAdd = super.add(task);
		task.addOnTaskFinishListener(this);
		task.addOnTaskStartListener(this);
		task.addOnTaskStopListener(this);
		task.addOnDownloadTaskPauseListener(this);
		task.addOnDownloadTaskResumeListener(this);
		if (isAutoStart()) {
			startTask(task);
		}
		return isAdd;
	}

	public void finalize() {
		super.finalize();
		synchronized (mInstance) {
			mInstance = null;
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
		taskControll(Controller.PAUSE, mList);
	}

	/**
	 * Controls the task resume.
	 */
	@Override
	public void resumeAll() throws Exception {
		taskControll(Controller.RESUME, mList);
	}

	/**
	 * Controls the task stop.
	 */
	@Override
	public void stopAll() throws Exception {
		taskControll(Controller.STOP, mList);
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

	@Override
	public List<DownloadTask> getUncompleteTasks() {
		return uncompleteTasks;
	}

	@Override
	public List<DownloadTask> getCompletedTasks() {
		return completedTasks;
	}

	@Override
	public boolean hasTask(Task task) {
		return mList.contains(task);
	}

	@Override
	public boolean deleteTask(Task task) {
		return mList.remove(task);
	}

	public DownloadTask getTask(int i) {
		synchronized (mList) {
			if (mList.isEmpty())
				return null;

			return mList.get(i);
		}
	}

	@Override
	public boolean addTasks(Collection<? extends DownloadTask> tasks) {
		for (DownloadTask task : tasks) {
			addTask(task);
		}
		return true;
	}

	@Override
	public synchronized boolean addTask(DownloadTask task) {
		mList.add(task);
		return task.isCompleted() ? completedTasks.add(task) : uncompleteTasks.add(task);
	}

	@Override
	public DownloadTask getTaskById(int id) {
		for (DownloadTask task : mList) {
			if (task != null && task.getId() == id) {
				return task;
			}
		}
		return null;
	}

	@Override
	public void start(int i) throws Exception {
		startTask(getTask(i));
	}

	@Override
	public void start(DownloadTask task) throws Exception {
		startTask(task);
	}

	@Override
	public void pause(int i) throws Exception {
		pauseTask(getTask(i));
	}

	@Override
	public void pause(DownloadTask task) throws Exception {
		pauseTask(task);
	}

	@Override
	public void resume(int i) throws Exception {
		resumeTask(getTask(i));
	}

	@Override
	public void resume(DownloadTask task) throws Exception {
		resumeTask(task);
	}

	@Override
	public void stop(int i) throws Exception {
		stopTask(getTask(i));
	}

	@Override
	public void stop(DownloadTask task) throws Exception {
		stopTask(task);
	}

	public void onTaskFinish(Task t) {
		synchronized (mList) {
			uncompleteTasks.remove(t);
			taskExecutor.remove(t);
			runningTaskNum--;
		}
		synchronized (completedTasks) {
			completedTasks.add((DownloadTask) t);
		}
		try {
			startRemainTask();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDownloadTaskPause(DownloadTask t) {

	}

	@Override
	public void onTaskStart(Task t) {

	}

	@Override
	public void onDownloadTaskResume(DownloadTask t) {

	}

	@Override
	public void onTaskStop(Task t) {

	}
}
