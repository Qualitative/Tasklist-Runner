package com.ns.dao;

import java.util.List;

import com.ns.model.Task;

public interface TaskDao {

    List<Task> getAllTasks();
    Task getTask(int pid);
    void saveOrUpdateTask(Task task);
    void removeTask(Task task);
    void removeAllTasks();

}
