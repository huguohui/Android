package com.badsocket.core;

import java.io.File;
import java.net.URL;

/**
 * Created by skyrim on 2017/11/28.
 */

public abstract class AbstractDownloadTask extends AbstractTask implements DownloadTask {

	protected int speedPerSecond;

	protected int speedAverage;

	protected int speedRealTime;

	protected URL url;

	protected long length;

	protected long downloadedLength;

	protected File downloadPath;

	protected int sectionNumber;

	protected int speedLimited;

	protected DownloadSection[] downloadSections;


	public AbstractDownloadTask(URL url) {
		this(url, "");
	}


	public AbstractDownloadTask(URL url, String name) {
		super(name);
		this.url = url;
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


	@Override
	public URL getURL() {
		return url;
	}


	@Override
	public void setURL(URL url) {
		this.url = url;
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
}
