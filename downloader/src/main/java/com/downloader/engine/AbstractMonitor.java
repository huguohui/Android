package com.downloader.engine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by skyrim on 2017/10/7.
 */

public abstract class AbstractMonitor extends TimerTask implements Monitor {

	protected Object monitored;

	protected int interval;

	protected Object collectionData;

	protected Timer monitorTimer = new Timer();


	public AbstractMonitor(int interval) {
		this.interval = interval;
	}


	@Override
	public void monitor(Object d) {
		monitored = d;
		monitorTimer.schedule(this, 0, interval);
	}


	protected void doMonitor() {
		if (monitored == null) {
			return;
		}
	}

	/**
	 * The action to be performed by this timer task.
	 */
	@Override
	public void run() {
		doMonitor();
	}
}
