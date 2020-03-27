package com.badsocket.core;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by skyrim on 2017/12/15.
 */

public interface TaskExecutor extends ScheduledExecutorService {

	Future<Task> start(Task task) throws Exception;

	Future<Task> start(Task task, long delay) throws Exception;

	void start(Task task, long delay, long interval) throws Exception;

	boolean isDone(Task t);

	void cancel(Task t);

	List<Task> tasks();

	boolean remove(Task t);

	void pause(Task t) throws Exception;

	void resume(Task t) throws Exception;

	void stop(Task t) throws Exception;

}
