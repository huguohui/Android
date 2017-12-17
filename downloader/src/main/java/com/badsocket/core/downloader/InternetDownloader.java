package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.io.writer.Writer;
import com.badsocket.manager.DownloadTaskManager;
import com.badsocket.manager.ThreadManager;
import com.badsocket.worker.AsyncWorker;
import com.badsocket.worker.Worker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Downloads data based http protocol.
 */
public class InternetDownloader extends AbstractDownloader {

	final public static int MAX_THREAD = 10;

	protected Writer fileWriter;

	protected ThreadManager threadManager = ThreadManager.getInstance();

	protected Worker worker;

	protected boolean isResumeFromInfo;

	protected int parallelTaskNum = 1;

	protected long blockSize;

	protected DownloadTask info;

	protected android.content.Context androidContext;

	protected int monitorInterval = 1000;

	protected DownloadTaskManager taskManager = DownloadTaskManager.getInstance();

	protected Map<Protocols, ProtocolHandler> protocolHandlers = new HashMap<>();

	protected Context context;

	protected ThreadAllocStategy policy = (info) -> {
		long len = Math.max(info.getLength(), 1);
		int num = 3;
		if (len < 3) {
			num = 1;
		}
		else if (len > 1024 * 1024 * 100) {
			num = 5;
		}
		else if (len > 1024 * 1024 * 10) {
			num = 10;
		}
		return num;
	};

	protected Monitor monitor = new DownloadMonitor(1000);


	public InternetDownloader(Context context) {
		this.context = context;
		androidContext = context.getAndroidContext();
	}


	protected void init() {
		worker = new AsyncWorker(threadManager);
		monitor.monitor(this);
		taskManager.setAutoStart(true);
	}


	protected void loadUnfinishedTasks() {

	}


	public DownloadTask newTask(DownloadDescriptor desc) throws IOException {
		String strPtl = desc.getAddress().getProtocol();

		return null;
	}


	@Override
	public DownloadTask findTask(int id) {
		return taskManager.get(id);
	}


	@Override
	public void addTask(DownloadTask t) {
		taskManager.add(t);
	}


	@Override
	public void deleteTask(int id) {
		taskManager.remove(id);
	}


	@Override
	public void startTask(int id) {
		try {
			taskManager.start(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
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


	public void start() {
		try {
			taskManager.startAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void stop() {
		try {
			taskManager.startAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void pause() {
		try {
			taskManager.pauseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void resume() {
		taskManager.resumeAll();
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


	public ProtocolHandler getProtocolHandler(Protocols p) {
		return protocolHandlers.get(p);
	}


	public interface ThreadAllocStategy {

		int alloc(DownloadTask info);

	}
}
