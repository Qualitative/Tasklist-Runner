package com.ns.processor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.ns.dao.TaskDao;
import com.ns.model.Task;

public class TaskListProcessor implements Processor<List<Task>> {

    private TaskDao taskDao;

    public TaskListProcessor(TaskDao taskDao) {
        this.taskDao = checkNotNull(taskDao);
    }

    @Override
    public void process(List<Task> tasks) {
        for (Task task : tasks) {
            taskDao.saveOrUpdateTask(task);
        }
    }

}
