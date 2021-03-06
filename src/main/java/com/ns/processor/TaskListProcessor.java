package com.ns.processor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.dao.TaskDao;
import com.ns.model.Task;

public class TaskListProcessor implements Processor<List<Task>> {

    private static final Logger LOG = LoggerFactory.getLogger(TaskListProcessor.class);

    private TaskDao taskDao;

    public TaskListProcessor(TaskDao taskDao) {
        this.taskDao = checkNotNull(taskDao);
    }

    @Override
    public void process(List<Task> tasks) {
        LOG.debug("Start tasks processing");

        for (Task task : tasks) {
            taskDao.saveTask(task);
        }

        LOG.debug("Finished tasks processing");
    }

}
