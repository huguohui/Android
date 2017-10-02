package com.downloader.manager;

import com.downloader.engine.downloader.DownloadTask;
import com.downloader.util.CollectionUtil;
import com.downloader.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static android.R.attr.id;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager extends AbstractDownloadTaskManager {
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = null;

	protected int taskRunnable = 1;

	protected List<DownloadTask> runnableTasks = new ArrayList<>(taskRunnable);
	
	
	/**
	 * Private constructor for singleton pattern.
	 */
	private DownloadTaskManager() {

	}

	
	/**
	 * Gets instance of manager.
	 * @return Instance of manager.
	 */
	public final synchronized static DownloadTaskManager getInstance() {
		if (mInstance == null)
			mInstance = new DownloadTaskManager();
		
		return mInstance;
	}


	public boolean add(DownloadTask d) {
		return super.add(d);
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		synchronized (runnableTasks) {
			CollectionUtil.forEach(runnableTasks, new Control(new Control.StartOperation()));
		}
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		synchronized (runnableTasks) {
			CollectionUtil.forEach(runnableTasks, new Control(new Control.PauseOperation()));
		}
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume()  {
		synchronized (runnableTasks) {
			CollectionUtil.forEach(runnableTasks, new Control(new Control.ResumeOperation()));
		}
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() {
		synchronized (runnableTasks) {
			CollectionUtil.forEach(runnableTasks, new Control(new Control.StopOperation()));
		}
	}


	protected DownloadTask getTask(int i) {
		synchronized (mList) {
			if (mList.isEmpty())
				return null;

			return mList.get(id);
		}
	}


	@Override
	public void start(int i) throws Exception {
		new Control.StopOperation().exec(getTask(i));
	}


	@Override
	public void pause(int i) throws Exception {
		new Control.PauseOperation().exec(getTask(i));
	}


	@Override
	public void resume(int i) throws Exception {
		new Control.ResumeOperation().exec(getTask(i));
	}


	@Override
	public void stop(int i) throws Exception {
		new Control.StopOperation().exec(getTask(i));
	}


	protected static class Control implements CollectionUtil.Callback<DownloadTask> {
		Operation operation;
		interface Operation {
			void exec(DownloadTask d) throws Exception;
		}

		private Control(Operation o) {
			operation = o;
		}


		@Override
		public void callback(DownloadTask o) {
			try {
				operation.exec(o);
			} catch (Exception e) {
				Log.e(e);
			}
		}

		static class StartOperation implements Operation {
			@Override
			public void exec(DownloadTask d) throws Exception {
				d.start();
			}
		}

		static class StopOperation implements Operation {
			@Override
			public void exec(DownloadTask d) throws Exception {
				d.stop();
			}
		}

		static class PauseOperation implements Operation {
			@Override
			public void exec(DownloadTask d) throws Exception {
				d.pause();
			}
		}

		static class ResumeOperation implements Operation {
			@Override
			public void exec(DownloadTask d) throws Exception {
				d.resume();
			}
		}
	}
}
