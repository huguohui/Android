package com.badsocket.core;

import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.Request;
import com.badsocket.net.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyrim on 2017/11/28.
 */

public abstract class AbstractDownloadTask extends AbstractTask implements DownloadTask {

	protected int speedPerSecond;

	protected int speedAverage;

	protected int speedRealTime;

	protected DownloadAddress downloadAddress;

	protected long length;

	protected long downloadedLength;

	protected File downloadPath;

	protected int sectionNumber;

	protected int speedLimited;

	protected boolean isPaused;

	protected DownloadSection[] downloadSections;

	protected transient Context context;

	protected transient Downloader downloader;

	protected int maxThreads;

	protected int priority;

	protected TaskExtraInfo extraInfo;

	protected transient Response response;

	protected int state = TaskState.UNSTART;

	protected transient DownloadTaskDescriptor taskDescriptor;

	protected transient FileWriter fileWriter;

	transient protected List<OnTaskCreateListener> onTaskCreateListeners = new ArrayList<>();

	transient protected List<OnTaskStartListener> onTaskStartListeners = new ArrayList<>();

	transient protected List<OnTaskStopListener> onTaskStopListeners = new ArrayList<>();

	transient protected List<OnTaskFinishListener> onTaskFinishListeners = new ArrayList<>();

	transient protected List<OnDownloadTaskPauseListener> onDownloadTaskPauseListeners = new ArrayList<>();

	transient protected List<OnDownloadTaskResumeListener> onDownloadTaskResumeListeners = new ArrayList<>();


	public AbstractDownloadTask(Downloader c, DownloadAddress url) {
		this(c, url, "");
	}


	public AbstractDownloadTask(Downloader c, DownloadAddress url, String name) {
		super(name);
		this.downloadAddress = url;
		this.downloader = c;
		this.context = c.getDownloaderContext();
	}


	public AbstractDownloadTask(Downloader c, DownloadTaskDescriptor d) {
		this(c, d, null);
	}


	public AbstractDownloadTask(Downloader c, DownloadTaskDescriptor d, Response r) {
		super(d.getTaskName());
		this.downloader = c;
		this.downloadAddress = d.getAddress();
		this.downloadPath = new File(d.getPath());
		this.maxThreads = d.getMaxThread();
		this.priority = d.getPriority();
		this.extraInfo = d.getTaskExtraInfo();
		this.response = r;
		this.context = c.getDownloaderContext();
		if (r != null) {
			fetchInfoFromResponse();
		}
	}


	protected void fetchInfo() throws IOException {
		if (response == null) {
			fetchResponse(openRequest());
		}
	}


	protected abstract Request openRequest() throws IOException;


	protected abstract void fetchResponse(Request r) throws IOException;


	protected abstract void fetchInfoFromResponse();


	protected void prepare() throws IOException {
		state = DownloadTaskState.PREPARING;
		fetchInfo();
	}


	@Override
	public void addOnTaskCreateListener(OnTaskCreateListener listener) {
		onTaskCreateListeners.add(listener);
	}


	@Override
	public void addOnTaskFinishListener(OnTaskFinishListener listener) {
		onTaskFinishListeners.add(listener);
	}


	@Override
	public void addOnTaskStopListener(OnTaskStopListener listener) {
		onTaskStopListeners.add(listener);
	}


	@Override
	public void addOnTaskStartListener(OnTaskStartListener listener) {
		onTaskStartListeners.add(listener);
	}


	@Override
	public void addOnDownloadTaskPauseListener(OnDownloadTaskPauseListener listener) {
		onDownloadTaskPauseListeners.add(listener);
	}


	@Override
	public void addOnDownloadTaskResumeListener(OnDownloadTaskResumeListener listener) {
		onDownloadTaskResumeListeners.add(listener);
	}


	public void onFinish() {
		if (onTaskFinishListeners != null) {
			for (OnTaskFinishListener listener : onTaskFinishListeners) {
				listener.onTaskFinish(this);
			}
		}
	}


	public void onCreate(TaskExtraInfo info) throws Exception {
		if (onTaskCreateListeners != null) {
			for (OnTaskCreateListener listener : onTaskCreateListeners) {
				listener.onTaskCreate(this);
			}
		}
	}


	public void onStart() {
		if (onTaskStartListeners != null) {
			for (OnTaskStartListener listener : onTaskStartListeners) {
				listener.onTaskStart(this);
			}
		}
	}


	public void onStop() {
		if (onTaskStopListeners != null) {
			for (OnTaskStopListener listener : onTaskStopListeners) {
				listener.onTaskStop(this);
			}
		}
	}


	public void onPause() {
		if (onDownloadTaskPauseListeners != null) {
			for (OnDownloadTaskPauseListener listener : onDownloadTaskPauseListeners) {
				listener.onDownloadTaskPause(this);
			}
		}
	}


	public void onResume() {
		if (onDownloadTaskResumeListeners != null) {
			for (OnTaskFinishListener listener : onTaskFinishListeners) {
				listener.onTaskFinish(this);
			}
		}
	}


	@Override
	public int getSpeedPerSecond() {
		return speedPerSecond;
	}


	@Override
	public int getSpeedAverage() {
		return speedAverage;
	}


	@Override
	public int getSpeedRealTime() {
		return speedRealTime;
	}


	public void setSpeedLimit(int limit) {
		speedLimited = limit;
	}


	public int getSpeedLimited() {
		return speedLimited;
	}


	public DownloadAddress getDownloadAddress() {
		return downloadAddress;
	}


	public void setDownloadAddress(DownloadAddress address) {
		this.downloadAddress = address;
	}


	@Override
	public long getLength() {
		return length;
	}


	@Override
	public long getDownloadedLength() {
		return downloadedLength;
	}


	@Override
	public void setDownloadPath(String path) {
		this.downloadPath = new File(path);
	}


	@Override
	public void setDownloadPath(File path) {
		this.downloadPath = path;
	}


	@Override
	public File getDownloadPath() {
		return downloadPath;
	}


	@Override
	public int getSectionNumber() {
		return sectionNumber;
	}


	@Override
	public DownloadSection[] getSections() {
		return downloadSections;
	}


	@Override
	public TaskExtraInfo getExtraInfo() {
		return extraInfo;
	}


	public int getState() {
		return DownloadTaskState.PAUSED;
	}
}
