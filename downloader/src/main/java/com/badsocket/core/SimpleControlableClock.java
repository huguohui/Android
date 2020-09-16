package com.badsocket.core;

import com.badsocket.core.downloader.ControlableClock;
import com.badsocket.core.downloader.Downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import java8.util.stream.StreamSupport;

import static com.badsocket.core.downloader.ControlableClock.MS_SECOND;

public class SimpleControlableClock extends Timer implements ControlableClock {

	protected long time;

	protected List<ControlableClock.OnTickListener> onTickListeners = new ArrayList<>();

	protected ClockTicker clockTicker = new ClockTicker();

	protected long interval = MS_SECOND;

	@Override
	public long runtime() {
		return time;
	}

	@Override
	public void addOnTickListener(ControlableClock.OnTickListener listener) {
		onTickListeners.add(listener);
	}

	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		schedule(clockTicker, 0, MS_SECOND);
	}

	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		stop();
	}

	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {
		start();
	}

	@Override
	public void interval(long interval) {
		this.interval = interval;
	}

	@Override
	public long interval() {
		return interval;
	}

	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {
		cancel();
	}

	protected class ClockTicker extends TimerTask {
		/**
		 * The action to be performed by this timer task.
		 */
		@Override
		public void run() {
			time += MS_SECOND;
			StreamSupport.stream(onTickListeners).forEach(listener -> listener.onTick());
		}
	}
}
