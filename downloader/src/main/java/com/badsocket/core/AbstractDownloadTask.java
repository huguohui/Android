package com.badsocket.core;

import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.net.Response;
import com.badsocket.net.newidea.URI;
import com.badsocket.util.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java8.util.stream.StreamSupport;

import static com.badsocket.core.downloader.ControlableClock.MS_SECOND;

/**
 * Created by skyrim on 2017/11/28.
 */

public abstract class AbstractDownloadTask extends AbstractTask implements DownloadTask {

	private static final long serialVersionUID = 12341234123413413L;

	protected long length = 0;

	protected long downloadedLength;

	protected File downloadPath;

	protected int sectionNumber;

	protected int speedLimited;

	protected int maxThreads;

	protected int priority;

	protected int state = TaskState.UNSTART;

	protected TaskExtraInfo extraInfo;

	protected URI downloadAddress;

	protected DownloadSection[] downloadSections;

	protected int action = DownloadTaskAction.START;

	protected DownloadTaskDescriptor taskDescriptor;

	protected boolean isPaused = false;

	transient protected Context context;

	transient protected Downloader downloader;

	transient protected Response response;

	transient protected FileWriter fileWriter;

	transient protected List<OnTaskCreateListener> onTaskCreateListeners = new ArrayList<>();

	transient protected List<OnTaskStartListener> onTaskStartListeners = new ArrayList<>();

	transient protected List<OnTaskStopListener> onTaskStopListeners = new ArrayList<>();

	transient protected List<OnTaskFinishListener> onTaskFinishListeners = new ArrayList<>();

	transient protected List<OnDownloadTaskPauseListener> onDownloadTaskPauseListeners = new ArrayList<>();

	transient protected List<OnDownloadTaskResumeListener> onDownloadTaskResumeListeners = new ArrayList<>();

	public AbstractDownloadTask(Downloader c, URI url) {
		this(c, url, "");
	}

	public AbstractDownloadTask(Downloader c, URI url, String name) {
		super(name);
		this.downloadAddress = url;
		this.downloader = c;
	}

	public AbstractDownloadTask(Downloader c, DownloadTaskDescriptor d) {
		this(c, d, null);
	}

	public AbstractDownloadTask(Downloader c, DownloadTaskDescriptor d, Response r) {
		super(d.getTaskName());
		this.downloader = c;
		this.downloadAddress = d.getUri();
		this.downloadPath = new File(d.getPath());
		this.maxThreads = d.getMaxThread();
		this.priority = d.getPriority();
		this.extraInfo = d.getTaskExtraInfo();
		this.response = r;
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

	public boolean equals(Task t) {
		DownloadTask task = (DownloadTask) t;
		return this.name().equals(task.name())
				&& this.getStorageDir().equals(task.getStorageDir());
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

	protected void afterTaskFinish() {
		isCompleted = true;
		state = DownloadTaskState.COMPLETED;
	}

	public void onCreate(TaskExtraInfo info) throws Exception {
		state = DownloadTaskState.PREPARING;
	}

	public void onStart() throws Exception {
		isRunning = true;
		state = DownloadTaskState.STARTING;
		action = DownloadTaskAction.START;
		createTime = DateUtils.millisTime();
	}

	public void onStop() throws Exception {
		state = DownloadTaskState.STOPPING;
		action = DownloadTaskAction.STOP;
	}

	public void onPause() throws Exception {
		state = DownloadTaskState.PAUSING;
		action = DownloadTaskAction.PAUSE;
	}

	public void onResume() throws Exception {
		state = DownloadTaskState.RESUMING;
		action = DownloadTaskAction.RESUME;
	}

	public void onRestore(Downloader downloader) {
		this.downloader = downloader;
		state = DownloadTaskState.STOPED;
		action = DownloadTaskAction.RESTORE;
	}

	public void onStore() {
		state = DownloadTaskState.STOPED;
		action = DownloadTaskAction.STORE;
	}

	protected void notifyTaskCreated() {
		StreamSupport.stream(onTaskCreateListeners).forEach(listener -> listener.onTaskCreate(this));
	}

	protected void notifyTaskFinished() {
		StreamSupport.stream(onTaskFinishListeners).forEach(listener -> listener.onTaskFinish(this));
	}

	protected void notifyTaskStarted() {
		StreamSupport.stream(onTaskStartListeners).forEach(listener -> listener.onTaskStart(this));
	}

	protected void notifyTaskPaused() {
		StreamSupport.stream(onDownloadTaskPauseListeners).forEach(listener -> listener.onDownloadTaskPause(this));
	}

	protected void notifyTaskResumed() {
		StreamSupport.stream(onDownloadTaskResumeListeners).forEach(listener -> listener.onDownloadTaskResume(this));
	}

	protected void notifyTaskStoped() {
		StreamSupport.stream(onTaskStopListeners).forEach(listener -> listener.onTaskStop(this));
	}

	public void setSpeedLimit(int limit) {
		speedLimited = limit;
	}

	public int getSpeedLimited() {
		return speedLimited;
	}

	public URI getDownloadURI() {
		return downloadAddress;
	}

	public void setDownloadURI(URI address) {
		this.downloadAddress = address;
	}

	public boolean isPaused() {
		return isPaused;
	}

	@Override
	public long size() {
		return length;
	}

	@Override
	public long downloadedSize() {
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
	public File getStorageDir() {
		try {
			return downloadPath.getCanonicalFile();
		}
		catch (Exception e) {
			return downloadPath;
		}
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
	public TaskExtraInfo extraInfo() {
		return extraInfo;
	}

	public int state() {
		return state;
	}
}
