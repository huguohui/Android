package com.badsocket.core;

import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.Response;
import com.badsocket.util.DateUtils;

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

	protected long length = 0;

	protected long downloadedLength;

	protected File downloadPath;

	protected int sectionNumber;

	protected int speedLimited;

	protected boolean isPaused;

	protected int maxThreads;

	protected int priority;

	protected int state = TaskState.UNSTART;

	protected TaskExtraInfo extraInfo;

	protected DownloadAddress downloadAddress;

	protected DownloadSection[] downloadSections;

	transient protected Context context;

	transient protected Downloader downloader;

	transient protected Response response;

	transient protected DownloadTaskDescriptor taskDescriptor;

	transient protected FileWriter fileWriter;

	transient protected List<OnTaskCreateListener> onTaskCreateListeners = new ArrayList<>();

	transient protected List<OnTaskStartListener> onTaskStartListeners = new ArrayList<>();

	transient protected List<OnTaskStopListener> onTaskStopListeners = new ArrayList<>();

	transient protected List<OnTaskFinishListener> onTaskFinishListeners = new ArrayList<>();

	transient protected List<OnDownloadTaskPauseListener> onDownloadTaskPauseListeners = new ArrayList<>();

	transient protected List<OnDownloadTaskResumeListener> onDownloadTaskResumeListeners = new ArrayList<>();

	transient final protected String[] LISTENER_TYPES = {
			"START", "PAUSE", "RESUME", "STOP", "FINISH"
	};


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
			prefetchResponse();
		}
		fetchInfoFromResponse();
	}


	protected abstract void prefetchResponse() throws IOException;


	protected abstract void fetchInfoFromResponse();


	protected void prepare() throws Exception {
		state = DownloadTaskState.PREPARING;
		fetchInfo();
	}


	protected List<?> getListeners(Class listenerType) {
		List<?> listeners = null;
		if (listenerType.equals(OnTaskStartListener.class)) {
			listeners = onTaskStartListeners;
		}
		else if (listenerType.equals(OnDownloadTaskPauseListener.class)) {
			listeners = onDownloadTaskPauseListeners;
		}
		else if (listenerType.equals(OnTaskStopListener.class)) {
			listeners = onTaskStopListeners;
		}
		else if (listenerType.equals(OnDownloadTaskResumeListener.class)) {
			listeners = onDownloadTaskResumeListeners;
		}
		else if (listenerType.equals(OnTaskFinishListener.class)) {
			listeners = onTaskFinishListeners;
		}
		else if (listenerType.equals(OnTaskCreateListener.class)) {
			listeners = onTaskCreateListeners;
		}

		return listeners;
	}


	protected void notifyListeners(Class listenerType) {
		List<?> listeners = getListeners(listenerType);
		if (listeners != null) {
			for (Object obj : listeners) {
				if (obj == null) {
					continue;
				}
				if (OnTaskStartListener.class.equals(listenerType)) {
					((OnTaskStartListener) obj).onTaskStart(this);
				}
				else if (OnTaskStopListener.class.equals(listenerType)){
					((OnTaskStopListener) obj).onTaskStop(this);
				}
				else if (OnDownloadTaskResumeListener.class.equals(listenerType)){
					((OnDownloadTaskResumeListener) obj).onDownloadTaskResume(this);
				}
				else if (OnDownloadTaskPauseListener.class.equals(listenerType)){
					((OnDownloadTaskPauseListener) obj).onDownloadTaskPause(this);
				}
				else if (OnTaskFinishListener.class.equals(listenerType)){
					((OnTaskFinishListener) obj).onTaskFinish(this);
				}
				else if (OnTaskCreateListener.class.equals(listenerType)) {
					((OnTaskCreateListener) obj).onTaskCreate(this);
				}
			}
		}
	}


	public void setSections(DownloadSection[] sections) {
		this.downloadSections = sections;
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


	protected void onTaskFinish() {
		state = DownloadTaskState.FINISHED;
		notifyListeners(OnTaskFinishListener.class);
	}


	public void onCreate(TaskExtraInfo info) throws Exception {
		state = DownloadTaskState.PREPARING;
		notifyListeners(OnTaskCreateListener.class);
	}


	public void onStart() throws IOException {
		isRunning = true;
		state = DownloadTaskState.RUNNING;
		startTime = DateUtils.millisTime();
		notifyListeners(OnTaskStartListener.class);
	}


	public void onStop() throws Exception {
		state = DownloadTaskState.STOPED;
		notifyListeners(OnTaskStopListener.class);
	}


	public void onPause() throws Exception {
		state = DownloadTaskState.PAUSED;
		notifyListeners(OnDownloadTaskPauseListener.class);
	}


	public void onResume() throws Exception {
		state = DownloadTaskState.RUNNING;
		notifyListeners(OnDownloadTaskResumeListener.class);
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
		return state;
	}
}
