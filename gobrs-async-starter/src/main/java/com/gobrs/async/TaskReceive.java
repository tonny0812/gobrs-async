package com.gobrs.async;

import com.gobrs.async.task.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Task receive.
 *
 * @program: gobrs -async-starter
 * @ClassName
 * @description:
 * @author: sizegang
 * @create: 2022 -03-16
 */
public class TaskReceive {

    private final TaskFlow taskFlow;
    /**
     * cache taskList
     */
    private List<AsyncTask> cacheTaskList;

    /**
     * Instantiates a new Task receive.
     *
     * @param taskFlow the task flow
     * @param taskList the task list
     */
    TaskReceive(TaskFlow taskFlow, List<AsyncTask> taskList) {
        synchronized (taskFlow) {
            this.taskFlow = taskFlow;
            this.cacheTaskList = new ArrayList<>(taskList.size());
            /**
             *  src -> dest
             */
            copyList(taskList, this.cacheTaskList);
            for (AsyncTask task : taskList) {
                taskFlow.addDependency(task, null);
            }
        }
    }

    /**
     * Then task receive.
     *
     * @param clear      the clear
     * @param asyncTasks the async tasks
     * @return the task receive
     */
    public TaskReceive then(boolean clear, AsyncTask... asyncTasks) {
        synchronized (taskFlow) {
            for (AsyncTask from : this.cacheTaskList) {
                for (AsyncTask to : asyncTasks) {
                    taskFlow.addDependency(from, to);
                }
            }
            for (AsyncTask to : asyncTasks) {
                taskFlow.addDependency(to, null);
            }
            /**
             *     for Compatible with regular commas so note
             */
            if (clear) {
                this.cacheTaskList = new ArrayList<AsyncTask>(
                        asyncTasks.length);
                copyList(Arrays.asList(asyncTasks), this.cacheTaskList);
            }
            return this;
        }
    }


    /**
     * Refresh.
     *
     * @param cacheTaskList the cache task list
     */
    public void refresh(List<AsyncTask> cacheTaskList) {
        this.cacheTaskList.clear();
        this.cacheTaskList.addAll(cacheTaskList);
    }


    private void copyList(List<AsyncTask> src,
                          List<AsyncTask> dest) {
        dest.addAll(src);
    }

}
