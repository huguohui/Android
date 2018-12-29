package com.badsocket.core.executor;

import com.badsocket.core.Task;

import java.util.List;

/**
 * Created by skyrim on 2017/12/15.
 */

public interface TaskExecutor {


	void start(Task task) throws Exception;


	void start(Task task, long delay) throws Exception;


	boolean isDone(Task t);


	void cancel(Task t);


	List<Task> tasks();


	boolean remove(Task t);

}
