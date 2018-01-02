package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.DownloadTaskExecutor;
import com.badsocket.core.DownloaderContext;
import com.badsocket.core.DownloaderWatcher;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.Protocol;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Task;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.DownloadConfig;
import com.badsocket.core.downloader.exception.FileAlreadyExistsException;
import com.badsocket.core.downloader.exception.UnsupportedProtocolException;
import com.badsocket.io.writer.Writer;
import com.badsocket.manager.DefaultDownloadTaskManager;
import com.badsocket.manager.DownloadTaskManager;
import com.badsocket.manager.ThreadManager;
import com.badsocket.worker.AsyncWorker;
import com.badsocket.worker.Worker;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Downloads data based http protocol.
 */
public class InternetDownloader extends AbstractDownloader {

	public static int MAX_PARALLEL_TASKS = 10;

	protected Writer fileWriter;

	protected ThreadManager threadManager = ThreadManager.getInstance();

	protected Worker worker;

	protected boolean isResumeFromInfo;

	protected int parallelTaskNum = 1;

	protected long blockSize;

	protected DownloadTask info;

	protected android.content.Context androidContext;

	protected int monitorInterval = 1000;

	protected DownloadTaskManager taskManager;

	protected Map<Protocol, ProtocolHandler> protocolHandlers = new HashMap<>();

	protected Context context;

	protected Config config;

	protected DownloadTaskExecutor downloadTaskExecutor;

	protected String defaultDownloadPath;


	protected ThreadAllocStategy stategy = (info) -> {
		long len = Math.max(info.getLength(), 1);
		int num = 3;

		if (len < 3) {
			num = 1;
		}

		if (len > 1024 * 1024 * 1) {
			num = 10;
		}

		return num;
	};

	protected Monitor monitor = new DownloadMonitor(1000);


	public InternetDownloader(Context context) {
		this.context = context;
		androidContext = context.getAndroidContext();
		init();
	}


	protected void init() {
		worker = new AsyncWorker(threadManager);
		taskManager = DefaultDownloadTaskManager.getInstance(this);
		config = context.getDownloadConfig();
		downloadTaskExecutor = (DownloadTaskExecutor) context.getDownloadTaskExecutor();
		MAX_PARALLEL_TASKS = config.getInteger(DownloadConfig.GLOBAL_MAX_PARALLEL_TASKS);
		defaultDownloadPath = DownloaderContext.ROOT_PATH + DownloaderContext.DS
				+ config.get(DownloadConfig.GLOBAL_DOWNLAOD_PATH);
		monitor.monitor(this);
		taskManager.setAutoStart(true);
		monitor.addWatcher(new DownloaderWatcher());
	}


	protected void loadUnfinishedTasks() {

	}


	@Override
	public boolean isTaskExists(Task task) {
		return taskManager.hasTask(task);
	}


	public DownloadTask newTask(DownloadTaskDescriptor desc) throws Exception {
		String protocolName = desc.getAddress().getProtocol();
		Protocol protocol = null;
		ProtocolHandler protocolHandler = null;
		DownloadTask task = null;
		if ((protocol = Protocol.getProtocol(protocolName)) == null
				|| (protocolHandler = protocolHandlers.get(protocol)) == null) {
			throw new UnsupportedProtocolException("暂不支持此下载协议:" + protocolName.toUpperCase() + "！");
		}
		if (desc.getPath() == null || desc.getPath().length() == 0) {
			desc.setPath(defaultDownloadPath);
		}

		task = protocolHandler.downloadComponentFactory().creatDownloadTask(
				this, desc, DownloadHelper.fetchResponseByDescriptor(this, desc, protocolHandler));
		File downloadFile = new File(task.getDownloadPath(), task.getName());
//		if (downloadFile.exists()) {
//			throw new FileAlreadyExistsException("下载任务: " + task.getName() + "已存在！");
//		}

		task.onCreate(desc.getTaskExtraInfo());
		taskManager.add(task);
		return task;
	}


	@Override
	public DownloadTask findTask(int id) {
		return taskManager.get(id);
	}


	@Override
	public DownloadTask findTaskByTaskId(int idx) {
		return taskManager.getTaskById(idx);
	}


	public void start() throws Exception {
		taskManager.startAll();
	}


	public void stop() throws Exception {
		taskManager.startAll();
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


	@Override
	public void deleteTask(int id) {
		taskManager.remove(id);
	}


	@Override
	public void deleteTask(DownloadTask task) {
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


	@Override
	public void addWatcher(MonitorWatcher w) {
		monitor.addWatcher(w);
	}


	@Override
	public Monitor getMonitor() {
		return monitor;
	}


	public void addProtocolHandler(Protocol protocol, ProtocolHandler handler) {
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


	public ProtocolHandler getProtocolHandler(Protocol p) {
		return protocolHandlers.get(p);
	}


	public interface ThreadAllocStategy {

		int alloc(DownloadTask info);

	}
}
