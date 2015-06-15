package com.ns.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ns.model.Task;

public class InMemoryTaskDao implements TaskDao {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryTaskDao.class);

    private Map<Integer, Task> tasks = Maps.newHashMap();

    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().collect(Collectors.toList());
    }

    @Override
    public Task getTask(int pid) {
        return tasks.get(pid);
    }

    @Override
    public void saveTask(Task task) {
        checkNotNull(task);
        int pid = task.getPid();
        if (pid == Task.DEFAULT) {
            LOG.error("Can't save task without PID");
            throw new IllegalArgumentException("Can't save task withou PID");
        }
        LOG.debug("Saving task with PID [{}]", pid);
        tasks.put(pid, task);
    }

    @Override
    public void removeTask(Task task) {
        checkNotNull(task);
        int pid = task.getPid();
        Task removed = tasks.remove(pid);
        if (removed != null) {
            LOG.debug("Task with PID [{}] has been removed", pid);
        } else {
            LOG.debug("Can't remove task. Task with PID [{}] was not found", pid);
        }
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

}
