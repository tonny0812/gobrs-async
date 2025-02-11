package com.gobrs.async.util;

import com.gobrs.async.domain.AnyConditionResult;
import com.gobrs.async.task.AsyncTask;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * The type Task util.
 *
 * @program: gobrs -async
 * @ClassName TaskUtil
 * @description:
 * @author: sizegang
 * @create: 2022 -09-28
 */
public class TaskUtil {

    /**
     * Multiple dependencies boolean.
     *
     * @param upwardTasksMap the upward tasks map
     * @param subTasks       the sub tasks
     * @return the boolean
     */
    public static boolean multipleDependencies(Map<AsyncTask, List<AsyncTask>> upwardTasksMap, List<AsyncTask> subTasks) {
        for (AsyncTask subTask : subTasks) {
            if (!CollectionUtils.isEmpty(upwardTasksMap.get(subTask)) && upwardTasksMap.get(subTask).size() > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Default any condition any condition.
     *
     * @return the any condition
     */
    public static AnyConditionResult defaultAnyCondition() {
        return AnyConditionResult.builder().setState(false).build();
    }

    /**
     * Default any condition any condition.
     *
     * @param state the state
     * @return the any condition
     */
    public static AnyConditionResult defaultAnyCondition(boolean state) {
        return AnyConditionResult.builder().setState(state).build();
    }

}
