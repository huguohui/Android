package com.badsocket.manager;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.DownloadTaskExecutor;
import com.badsocket.core.Task;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DefaultDownloadTaskManager
		extends AbstractManager<DownloadTask>
		implements DownloadTaskManager, Task.OnTaskFinishListener, Task.OnTaskStartListener,
			Task.OnTaskStopListener, DownloadTask.OnDownloadTaskPauseListener,
			DownloadTask.OnDownloadTaskResumeListener
{
	/** Instance of manager. */
	private static DefaultDownloadTaskManager mInstance = null;
	/** Max并行的任务数 */
	public final static int MAX_PARALELL_TASK = 5;

	protected int runningTaskNum = 0;

	protected int allTasksNum = 0;

	protected int parallelTaskNum = MAX_PARALELL_TASK;

	protected List<DownloadTask> finishedTasks = new ArrayList<>();

	protected List<DownloadTask> runningTasks = new ArrayList<>();

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
		taskExecutor = (DownloadTaskExecutor) downloader.getDownloaderContext().getDownloadTaskExecutor();
	}

	
	/**
	 * Gets instance of manager.
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
			return true;
		});
	}


	protected void startRemainTask() throws Exception {
		int needStartTask = parallelTaskNum - runningTaskNum;
		List<DownloadTask> tasks = null;
		if (needStartTask > 0 && (tasks = getRunnableTask()).size() != 0) {
			taskControll(Controller.START, tasks.subList(0, needStartTask));
			runningTaskNum += needStartTask;
		}
		else {
			//TODO: all done.
		}
	}


	protected void executeTask(DownloadTask task) throws Exception {
		taskExecutor.execute(task);
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
			case START:		startTask(dt);	break;
			case STOP:		stopTask(dt);	break;
			case PAUSE:		pauseTask(dt);	break;
			case RESUME:	resumeTask(dt);	break;
		}
	}


	protected void startTask(DownloadTask t) throws Exception {
		if (isAutoStart && runningTaskNum < parallelTaskNum) {
			executeTask(t);
			runningTaskNum++;
		}
		else {
		}
	}


	public boolean add(DownloadTask task) throws Exception {
		synchronized (mList) {
			boolean isAdd = super.add(task);
			task.addOnTaskFinishListener(this);
			task.addOnTaskStartListener(this);
			task.addOnTaskStopListener(this);
			task.addOnDownloadTaskPauseListener(this);
			task.addOnDownloadTaskResumeListener(this);
			startTask(task);
			return isAdd;
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
		taskControll(Controller.RESUME, mList);
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
		taskControll(Controller.RESUME, mList);
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


	public void onTaskFinish(Task t)  {
		synchronized (mList) {
			mList.remove(t);
			runningTaskNum--;
		}

		synchronized (finishedTasks) {
			finishedTasks.add((DownloadTask) t);
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
