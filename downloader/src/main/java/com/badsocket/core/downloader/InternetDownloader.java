package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.SimpleControlableClock;
import com.badsocket.core.Task;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.DownloadConfig;
import com.badsocket.core.downloader.exception.UnsupportedProtocolException;
import com.badsocket.core.executor.DownloadTaskExecutor;
import com.badsocket.manager.DefaultDownloadTaskManager;
import com.badsocket.manager.DownloadTaskManager;
import com.badsocket.manager.ThreadManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java8.util.stream.StreamSupport;

/**
 * Downloads data based http protocol.
 */
public class InternetDownloader
		extends AbstractDownloader
		implements Task.OnTaskFinishListener, ControlableClock.OnTickListener {

	public static int MAX_PARALLEL_TASKS = 10;

	protected ThreadManager threadManager = ThreadManager.getInstance();

	protected boolean isResumeFromInfo;

	protected int parallelTaskNum = 1;

	protected long blockSize;

	protected DownloadTask info;

	protected android.content.Context androidContext;

	protected int monitorInterval = 1000;

	protected DownloadTaskManager taskManager;

	protected Map<Protocols, ProtocolHandler> protocolHandlers = new HashMap<>();

	protected Context context;

	protected Config config;

	protected DownloadTaskExecutor downloadTaskExecutor;

	protected String downloadPath;

	protected DownloadTaskInfoStorage downloadTaskInfoStorage;

	protected InternalClock clock;

	protected ThreadAllocStategy stategy = task -> {
		long len = Math.max(task.size(), 1);
		int num = 3;
		return len < 3 ? 1 : (len > 1024 * 1024 ? 10 : num);
	};

	public InternetDownloader(Context context) throws Exception {
		this.context = context;
		androidContext = context.getAndroidContext();
		init();
	}

	protected void initEnvironment() {
		taskManager = new DefaultDownloadTaskManager(this);
		config = context.getDownloaderConfig();
		downloadTaskExecutor = context.getDownloadTaskExecutor();
		MAX_PARALLEL_TASKS = config.getInteger(DownloadConfig.GLOBAL_MAX_PARALLEL_TASKS);
		downloadPath = DownloaderContext.ROOT_PATH + DownloaderContext.DS
				+ config.get(DownloadConfig.GLOBAL_DOWNLAOD_PATH);
		downloadTaskInfoStorage = new BaseFileDownloadTaskInfoStorage(new FileDownloadTaskInfoStorage.Locations(
				DownloaderContext.HOME_DIRECTORY + DownloaderContext.DS + DownloaderContext.HISTORY_DIR,
				downloadPath
		));

		taskManager.setAutoStart(true);
		clock = new InternalClock(this);
		clock.addOnTickListener(this);
	}

	protected void loadTasks() throws Exception {
		DownloadTaskInfoStorage.TaskList taskList = downloadTaskInfoStorage.read();
		for (DownloadTaskInfoStorage.TaskListItem taskItem : taskList.list()) {
			DownloadTask task = downloadTaskInfoStorage.read(taskItem.taskIndex);
			task.onRestore(this);
			addTask(task);
		}
	}

	protected void initTasks() throws Exception {
		loadTasks();
	}

	public void init() throws Exception {
		initEnvironment();
		initTasks();
	}

	protected boolean isDownloadTaskExists(DownloadTask task) {
		File completeTask = new File(task.getStorageDir(), task.name()),
				uncompleteTask = new File(completeTask.getPath()
						+ Downloader.UNCOMPLETE_DOWNLAOD_TASK_SUFFIX);

		return completeTask.exists() || uncompleteTask.exists()
				|| StreamSupport.stream(taskList()).filter((t) -> t.equals(task)).count() != 0;
	}

	protected boolean isSupportProtocol(Protocols protocol) {
		return protocolHandlers.get(protocol) != null;
	}

	protected DownloadTask createTask(DownloadTaskDescriptor descriptor, ProtocolHandler handler)
			throws IOException {
		DownloadTask task = handler.downloadComponentFactory().creatDownloadTask(
				this, descriptor, DownloadHelper.fetchResponseByDescriptor(this, descriptor, handler));
		task.addOnTaskFinishListener(this);
		return task;
	}

	@Override
	public boolean isTaskExists(Task task) {
		return taskManager.hasTask(task);
	}

	public DownloadTask newTask(DownloadTaskDescriptor desc) throws Exception {
		String protocolName = desc.getUri().getScheme();
		Protocols protocol = Protocols.getProtocol(protocolName);
		DownloadTask task = null;
		if (!isSupportProtocol(protocol)) {
			throw new UnsupportedProtocolException("暂不支持此下载协议:" + protocolName.toUpperCase() + "！");
		}
		if (desc.getPath() == null || desc.getPath().length() == 0) {
			desc.setPath(downloadPath);
		}

		task = createTask(desc, protocolHandlers.get(protocol));
		if (isDownloadTaskExists(task)) {
			//throw new FileAlreadyExistsException("下载任务: " + task.name() + "已存在！");
		}

		task.onCreate(desc.getTaskExtraInfo());
		addTask(task);
		return task;
	}

	protected void trimUncompleteSuffix(DownloadTask task) {
		File uncompleteFile = new File(task.getStorageDir(), task.name() + UNCOMPLETE_DOWNLAOD_TASK_SUFFIX);
		if (uncompleteFile.exists()) {
			uncompleteFile.renameTo(new File(task.getStorageDir(), task.name()));
		}
	}

	@Override
	public void onTaskFinish(Task t) {
		DownloadTask task = (DownloadTask) t;
		trimUncompleteSuffix(task);
		File dtiFile = new File(task.getStorageDir(), task.name() + DOWNLOAD_TASK_INFO_SUFFIX);
		if (dtiFile.exists()) {
			dtiFile.delete();
		}

		onTaskListChange();
	}

	@Override
	public DownloadTask findTask(int id) {
		return taskManager.get(id);
	}

	@Override
	public DownloadTask findTaskById(int idx) {
		return taskManager.getTaskById(idx);
	}

	public void start() throws Exception {
		super.start();
		clock.start();
//		taskManager.startAll();
	}

	public void stop() throws Exception {
		super.stop();
		clock.stop();
		taskManager.stopAll();
	}

	public void pause() throws Exception {
		super.pause();
		taskManager.pauseAll();
	}

	public void resume() throws Exception {
		super.resume();
		taskManager.resumeAll();
	}

	@Override
	public void addTask(DownloadTask t) throws Exception {
		taskManager.add(t);
		clock.addOnTickListener(t);
		onTaskListChange();
	}

	private void deleteTaskFile(DownloadTask task) {
		File taskFile = new File(task.getStorageDir(), task.name()
				+ (task.isCompleted() ? "" : UNCOMPLETE_DOWNLAOD_TASK_SUFFIX));
		File taskInfoFile = new File(task.getStorageDir(), task.name()
				+ DOWNLOAD_TASK_INFO_SUFFIX);

		taskFile.delete();
		taskInfoFile.delete();
	}

	protected void writeTasksInfo() throws Exception {
		List<DownloadTask> tasks = taskManager.list();
		downloadTaskInfoStorage.write(tasks);
	}

	protected void writeListInfo() {
		List<DownloadTask> tasks = taskManager.list();
		try {
			downloadTaskInfoStorage.write(tasks);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onTaskListChange() {
		writeListInfo();
	}

	@Override
	public void deleteTask(int id) {
		deleteTaskFile(taskManager.getTask(id));
		taskManager.remove(id);
		onTaskListChange();
	}

	@Override
	public void deleteTask(DownloadTask task) {
		deleteTaskFile(task);
		taskManager.deleteTask(task);
		onTaskListChange();
	}

	@Override
	public void startTask(int id) throws Exception {
		taskManager.start(id);
	}

	@Override
	public void startTask(DownloadTask task) throws Exception {
		taskManager.start(task);
	}

	@Override
	public void stopTask(int id) throws Exception {
		taskManager.stop(id);
	}

	@Override
	public void stopTask(DownloadTask task) throws Exception {
		taskManager.stop(task);
	}

	@Override
	public void pauseTask(int id) throws Exception {
		taskManager.pause(id);
	}

	@Override
	public void pauseTask(DownloadTask task) throws Exception {
		taskManager.pause(task);
	}

	@Override
	public void resumeTask(int id) throws Exception {
		taskManager.resume(id);
	}

	@Override
	public void resumeTask(DownloadTask task) throws Exception {
		taskManager.resume(task);
	}

	@Override
	public List<DownloadTask> taskList() {
		return taskManager.list();
	}

	@Override
	public List<Thread> threadList() {
		return threadManager.list();
	}

	public void addProtocolHandler(Protocols protocol, ProtocolHandler handler) {
		protocolHandlers.put(protocol, handler);
	}

	@Override
	public void setParallelTaskNum(int num) {
		taskManager.setParallelTaskNum(num);
	}

	@Override
	public int getParallelTaskNum() {
		return taskManager.getParallelTaskNum();
	}

	@Override
	public Context getDownloaderContext() {
		return context;
	}

	@Override
	public ThreadAllocStategy getThreadAllocStategy() {
		return this.stategy;
	}

	@Override
	public void setThreadAllocStategy(ThreadAllocStategy stategy) {
		this.stategy = stategy;
	}

	@Override
	public DownloadTaskInfoStorage getDownloadTaskStorage() {
		return downloadTaskInfoStorage;
	}

	@Override
	public void setDownloadTaskInfoStorage(DownloadTaskInfoStorage storage) {
		downloadTaskInfoStorage = storage;
	}

	@Override
	public void exit() throws Exception {
		stop();
	}

	@Override
	public long runtime() {
		return clock.runtime();
	}

	public ProtocolHandler getProtocolHandler(Protocols p) {
		return protocolHandlers.get(p);
	}

	@Override
	public void onTick() {
		try {
			writeTasksInfo();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface ThreadAllocStategy {

		int alloc(DownloadTask info);

	}

	private static class InternalClock extends SimpleControlableClock {

		private InternetDownloader downloader;

		public InternalClock(InternetDownloader downloader) {
			clockTicker = new AsyncClockTicker();
			this.downloader = downloader;
		}

		class AsyncClockTicker extends ClockTicker {
			@Override
			public void run() {
				time += MS_SECOND;
				downloader.threadManager.create(() -> {
					StreamSupport.stream(onTickListeners).forEach(listener -> listener.onTick());
				}).start();
			}
		}
	}
}
