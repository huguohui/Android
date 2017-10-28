package com.xstream.engine;

import java.util.List;

/**
 * Created by skyrim on 2017/10/7.
 */
public interface Monitor extends Stopable {


	void monitor(Object d);


	Object collectedData();


	void addWatcher(MonitorWatcher w);


	void removeWatcher(MonitorWatcher w);


	long interval();


	void setInterval(long i);


	List<MonitorWatcher> getWatchers();

}
