package com.ns.processor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.dao.TaskDao;
import com.ns.model.Task;

public class ServiceListProcessor implements Processor<List<Task>> {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceListProcessor.class);

    private TaskDao taskDao;

    public ServiceListProcessor(TaskDao taskDao) {
        this.taskDao = checkNotNull(taskDao);
    }

    @Override
    public void process(List<Task> tasks) {
        LOG.debug("Start tasks processing");

        for (Task task : tasks) {
            int pid = task.getPid();
            Task savedTask = taskDao.getTask(pid);
            if (savedTask != null) {
                List<String> services = task.getServices();
                savedTask.setServices(services);
                taskDao.saveTask(savedTask);
            }
        }

        LOG.debug("Finished tasks processing");
    }

}
