package com.ns.processor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.ns.dao.TaskDao;
import com.ns.model.Task;

@RunWith(MockitoJUnitRunner.class)
public class TaskListProcessorTest {

    private static final int PID1 = 1;
    private static final int PID2 = 2;

    @Mock
    private TaskDao taskDao;

    private TaskListProcessor processor;

    @Before
    public void init() {
        processor = new TaskListProcessor(taskDao);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenTaskDaoIsNull() {
        // When
        new TaskListProcessor(null);
    }

    @Test
    public void shouldSaveAllTasksWhenProcessTaskList() {
        // Given
        Task task1 = createTask(PID1);
        Task task2 = createTask(PID2);

        List<Task> tasks = Lists.newArrayList(task1, task2);
        // When
        processor.process(tasks);
        // Then
        verify(taskDao).saveTask(task1);
        verify(taskDao).saveTask(task2);
        verifyNoMoreInteractions(taskDao);
    }

    private Task createTask(int pid) {
        Task task = new Task();
        task.setPid(pid);
        return task;
    }

}
