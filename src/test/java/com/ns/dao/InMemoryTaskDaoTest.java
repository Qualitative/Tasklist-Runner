package com.ns.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ns.model.Task;

// Getters are quite simple to be sure they work correctly
public class InMemoryTaskDaoTest {

    private static final int PID1 = 1;
    private static final int PID2 = 2;

    private InMemoryTaskDao taskDao;

    @Before
    public void init() {
        taskDao = new InMemoryTaskDao();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenSaveNullTask() {
        // When
        taskDao.saveTask(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSaveTaskWithoutPid() {
        // Given
        Task task = new Task();
        // When
        taskDao.saveTask(task);
    }

    @Test
    public void shouldSaveNewTaskWhenSaveTaskWithUniquePid() {
        // Given
        Task task = createTask(PID1);
        // When
        taskDao.saveTask(task);
        // Then
        List<Task> tasks = taskDao.getAllTasks();
        Task savedTask = taskDao.getTask(PID1);
        assertEquals(1, tasks.size());
        assertTrue(tasks.contains(task));
        assertEquals(task, savedTask);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenRemoveNullTask() {
        // When
        taskDao.removeTask(null);
    }

    @Test
    public void shouldRemoveTaskWithPidEqualToPidOfSpecifiedTask() {
        // Given
        Task task = createTask(PID1);
        taskDao.saveTask(task);
        // When
        taskDao.removeTask(task);
        // Then
        List<Task> tasks = taskDao.getAllTasks();
        Task savedTask = taskDao.getTask(PID1);
        assertTrue(tasks.isEmpty());
        assertNull(savedTask);
    }

    @Test
    public void shouldNotRemoveTaskIfPidNotEqualToPidOfSpecifiedTask() {
        // Given
        Task task1 = createTask(PID1);
        Task task2 = createTask(PID2);
        taskDao.saveTask(task1);
        // When
        taskDao.removeTask(task2);
        // Then
        List<Task> tasks = taskDao.getAllTasks();
        Task savedTask = taskDao.getTask(PID1);
        assertEquals(1, tasks.size());
        assertTrue(tasks.contains(task1));
        assertEquals(task1, savedTask);
    }

    @Test
    public void shouldRemoveAllTasksWhenInvokeRemovingAllTasks() {
        // Given
        Task task1 = createTask(PID1);
        Task task2 = createTask(PID2);
        taskDao.saveTask(task1);
        taskDao.saveTask(task2);
        assertEquals(2, taskDao.getAllTasks().size());
        // When
        taskDao.removeAllTasks();
        // Then
        List<Task> tasks = taskDao.getAllTasks();
        assertTrue(tasks.isEmpty());
    }

    private Task createTask(int pid) {
        Task task = new Task();
        task.setPid(pid);
        return task;
    }

}
