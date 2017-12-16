package com.badsocket.core.downloader;

import com.badsocket.core.Context;
import com.badsocket.core.DownloaderContext;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.downloader.exception.UnsupportedProtocolException;
import com.badsocket.core.downloader.factory.DownloadTaskFactory;
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

	protected DownloadTaskInfo info;

	protected android.content.Context androidContext;

	protected int monitorInterval = 1000;

	protected DownloadTaskManager taskManager = DownloadTaskManager.getInstance();

	protected Map<Protocols, ProtocolHandler> protocolHandlers = new HashMap<>();

	protected Context context;

	protected ThreadAllocStategy policy = (info) -> {
		long len = Math.max(info.getLength(), 1);
		int i = 1;
		for (; (len /= 1024) != 0; i++) {
		}
		return Math.min(MAX_THREAD, i);
	};

	protected Monitor monitor = new DownloadMonitor(1000);




	public InternetDownloader(android.content.Context context) {
		androidContext = context;
		initAll();
	}


	protected void initAll() {
		context = new DownloaderContext(androidContext);
		context.getConfig();
	}


	protected void init() {
		worker = new AsyncWorker(threadManager);
		monitor.monitor(this);
		taskManager.setAutoStart(true);
	}


	public DownloadTask newTask(DownloadDescriptor desc) throws IOException {
		String strPtl = desc.getAddress().getProtocol();
		if (!Protocols.isSupport(strPtl)) {
			throw new UnsupportedProtocolException();
		}

		Protocols protocol = Protocols.getProtocol(strPtl);
		ProtocolHandler handler = protocolHandlers.get(protocol);
		if (handler == null) {
			throw new UnsupportedProtocolException();
		}

		DownloadTask dt = DownloadTaskFactory.create(desc, handler, policy);
		taskManager.add(dt);
		monitor.monitor(this);
		return dt;
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

		int alloc(DownloadTaskInfo info);

	}
}
