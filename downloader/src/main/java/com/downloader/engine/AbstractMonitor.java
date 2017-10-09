package com.downloader.engine;

import com.downloader.engine.downloader.Downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by skyrim on 2017/10/7.
 */

public abstract class AbstractMonitor extends TimerTask implements Monitor {

	protected ThreadLocal<Downloader> localObj;

	protected Object monitored;

	protected int interval;

	protected Object collectionData;

	protected Timer monitorTimer = new Timer();

	protected List<MonitorWatcher> watchers = new ArrayList<>();


	public AbstractMonitor(int interval) {
		this.interval = interval;
	}


	@Override
	public void monitor(Object d) {
		monitored = d;
		monitorTimer.schedule(this, 0, interval);
	}


	public void doMonitor() {
		localObj.set((Downloader) monitored);
		for (int i = 0; i < watchers.size(); i++) {
			watchers.get(i).watch(localObj.get());
		}
	}

	/**
	 * The action to be performed by this timer task.
	 */
	@Override
	public void run() {
		if (monitored == null) {
			return;
		}

		doMonitor();
	}


	public void addWatcher(MonitorWatcher w) {
		synchronized (watchers) {
			watchers.add(w);
		}
	}


	public Object collectedData() {
		return localObj.get();
	}
}
