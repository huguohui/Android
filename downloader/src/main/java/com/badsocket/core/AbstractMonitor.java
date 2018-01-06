package com.badsocket.core;

import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by skyrim on 2017/10/7.
 */
public abstract class AbstractMonitor extends TimerTask implements Monitor {

	protected static ThreadLocal<Object> localObj = new ThreadLocal<>();

	protected Object monitored;

	protected long interval;

	protected Object collectionData;

	protected Timer monitorTimer = new Timer();

	protected List<MonitorWatcher> watchers = new ArrayList<>();


	public AbstractMonitor(int interval) {
		this.interval = interval;
	}


	@Override
	public void monitor(Object d) {
		if (monitored == null) {
			monitored = d;
			monitorTimer.schedule(this, 0, interval);
		}
		else {
			doMonitor();
		}
	}


	protected void doMonitor() {
		Object obj = null;
		if ((obj = localObj.get()) == null) {
			localObj.set(monitored);
		}

		for (int i = 0; i < watchers.size(); i++) {
			invokeWatcher(watchers.get(i), obj == null ? localObj.get() : obj);
		}
	}


	protected void invokeWatcher(MonitorWatcher w, Object o) {
		if (w != null) {
			w.watch(o);
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


	public void removeWatcher(MonitorWatcher m) {
		synchronized (watchers) {
			watchers.remove(m);
		}
	}


	public long interval() {
		return interval;
	}


	public void setInterval(long interval) {
		this.interval = interval;
	}


	public Object collectedData() {
		return localObj.get();
	}


	public List<MonitorWatcher> getWatchers() {
		return this.watchers;
	}


	public void stop() {
		monitorTimer.cancel();
	}

}
