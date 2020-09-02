package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.Task;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.DownloadConfig;
import com.badsocket.core.downloader.exception.UnsupportedProtocolException;
import com.badsocket.core.executor.DownloadTaskExecutor;
import com.badsocket.io.writer.Writer;
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
		implements Task.OnTaskFinishListener {

	public static int MAX_PARALLEL_TASKS = 10;

	protected Writer fileWriter;

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

	protected String defaultDownloadPath;

	protected DownloadTaskInfoStorage downloadTaskInfoStorage;

	protected ThreadAllocStategy stategy = (task) -> {
		long len = Math.max(task.size(), 1);
		int num = 3;
		return len < 3 ? 1 : (len > 1024 * 1024 ? 10 : num);
	};


	public InternetDownloader(Context context) {
		this.context = context;
		androidContext = context.getAndroidContext();
	}

	protected void initEnvironment() {
		taskManager = new DefaultDownloadTaskManager(this);
		config = context.getDownloaderConfig();
		downloadTaskExecutor = context.getDownloadTaskExecutor();
		MAX_PARALLEL_TASKS = config.getInteger(DownloadConfig.GLOBAL_MAX_PARALLEL_TASKS);
		defaultDownloadPath = DownloaderContext.ROOT_PATH + DownloaderContext.DS
				+ config.get(DownloadConfig.GLOBAL_DOWNLAOD_PATH);
		downloadTaskInfoStorage = new FileDownloadTaskInfoStorage(
				new File(DownloaderContext.HOME_DIRECTORY + DownloaderContext.DS + DownloaderContext.HISTORY_DIR));

		taskManager.setAutoStart(true);
	}

	protected void loadTasks() throws Exception {
		List<DownloadTask> tasks = downloadTaskInfoStorage.readList();
		for (DownloadTask task : tasks) {
			taskManager.addTask(task);
			task.onRestore(this);
		}
	}

	protected void initTasks() throws Exception {
		loadTasks();
	}

	public void init() throws Exception {
		initEnvironment();
		initTasks();
	}

	protected boolean checkDownloadTaskExists(DownloadTask task) {
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
			desc.setPath(defaultDownloadPath);
		}

		task = createTask(desc, protocolHandlers.get(protocol));
		if (checkDownloadTaskExists(task)) {
			//throw new FileAlreadyExistsException("下载任务: " + task.name() + "已存在！");
		}

		task.onCreate(desc.getTaskExtraInfo());
		taskManager.add(task);
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
		init();
//		taskManager.startAll();
	}

	public void stop() throws Exception {
		taskManager.stopAll();
	}

	public void pause() throws Exception {
		taskManager.pauseAll();
	}

	public void resume() throws Exception {
		taskManager.resumeAll();
	}

	@Override
	public void addTask(DownloadTask t) throws Exception {
		taskManager.add(t);
	}

	private void deleteTaskFile(DownloadTask task) {
		File taskFile = new File(task.getStorageDir(), task.name()
				+ (task.isCompleted() ? "" : UNCOMPLETE_DOWNLAOD_TASK_SUFFIX));
		File taskInfoFile = new File(task.getStorageDir(), task.name()
				+ DOWNLOAD_TASK_INFO_SUFFIX);

		taskFile.delete();
		taskInfoFile.delete();
	}

	@Override
	public void deleteTask(int id) {
		deleteTaskFile(taskManager.getTask(id));
		taskManager.remove(id);
	}

	@Override
	public void deleteTask(DownloadTask task) {
		deleteTaskFile(task);
		taskManager.deleteTask(task);
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
		taskManager.finalize();
	}

	public ProtocolHandler getProtocolHandler(Protocols p) {
		return protocolHandlers.get(p);
	}

	public interface ThreadAllocStategy {

		int alloc(DownloadTask info);

	}
}
