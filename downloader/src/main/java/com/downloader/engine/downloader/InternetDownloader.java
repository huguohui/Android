package com.downloader.engine.downloader;

import com.downloader.client.Context;
import com.downloader.engine.Monitor;
import com.downloader.engine.downloader.exception.UnsupportedProtocolException;
import com.downloader.engine.downloader.factory.DownloadTaskFactory;
import com.downloader.engine.worker.AsyncWorker;
import com.downloader.engine.worker.Worker;
import com.downloader.io.writer.Writer;
import com.downloader.manager.DownloadTaskManager;
import com.downloader.manager.ThreadManager;
import com.downloader.util.CollectionUtil;
import com.downloader.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

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

	protected Context context;

	protected DownloadTaskManager taskManager = DownloadTaskManager.getInstance();

	protected Map<Protocols, ProtocolHandler> protocolHandlers = new HashMap<>();

	protected ThreadAllocPolicy policy = new ThreadAllocPolicy() {
		@Override
		public int alloc(DownloadTaskInfo info) {
			long len = Math.max(info.getLength(), 1);
			int i = 1;
			while((len /= 1024) != 0) {
				i++;
			}
			return Math.min(MAX_THREAD, i);
		}
	};

	protected List<Monitor> monitors = new Vector<>();

	protected Timer monitorTimer = new Timer();

	protected TimerTask monitorTimerTask = new TimerTask() {
		@Override
		public void run() {
			monitor();
		}
	};


	public InternetDownloader(Context c) {
		context = c;
	}


	protected void init() {
		worker = new AsyncWorker(threadManager);
	}


	public void newTask(DownloadDescriptor desc) throws UnsupportedProtocolException, IOException {
		String strPtl = desc.getAddress().getProtocol();
		if (!Protocols.isSupport(strPtl)) {
			throw new UnsupportedProtocolException();
		}

		Protocols protocol = Protocols.getProtocol(strPtl);
		ProtocolHandler handler = protocolHandlers.get(protocol);
		if (handler == null) {
			throw new UnsupportedProtocolException();
		}
		taskManager.add(DownloadTaskFactory.create(desc, handler, policy));
	}


	@Override
	public DownloadTask findTask(int id) {
		return taskManager.get(id);
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


	public void monitor() {
		CollectionUtil.forEach(taskManager.list(), new CollectionUtil.Action<DownloadTask>() {
			@Override
			public void doAction(DownloadTask o) {
				//Log.println(o.info().getProgress());
			}
		});

		monitorTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < threadManager.list().size(); i++) {
					Log.println(threadManager.get(i).getId() + "\t" + threadManager.get(i).getName() + "\t" + threadManager.get(i).getState().toString());
				}
			}
		}, 0, 1000);
	}


	public void start() {
		try {
			taskManager.startAll();
//			monitorTimer.schedule(monitorTimerTask, 0, 1000);
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


	public ProtocolHandler getProtocolHandler(Protocols p) {
		return protocolHandlers.get(p);
	}


	public interface ThreadAllocPolicy {

		int alloc(DownloadTaskInfo info);

	}
}
