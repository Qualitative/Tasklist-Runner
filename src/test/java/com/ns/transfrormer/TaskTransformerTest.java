package com.ns.transfrormer;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.ns.model.Task;

public class TaskTransformerTest {

    private static final int MEMORY_USAGE_1 = 1;
    private static final int MEMORY_USAGE_2 = 2;
    private static final int MEMORY_USAGE_3 = 3;
    private static final int OTHER_MEMORY_USAGE = 10;

    private static final int AGGREGATED_MEMORY_USAGE = MEMORY_USAGE_1 + MEMORY_USAGE_2 + MEMORY_USAGE_3;

    private static final String NAME = "name";
    private static final String OTHER_NAME = "otherName";

    private TaskTransformer transformer;

    @Before
    public void init() {
        transformer = new TaskTransformer();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenTaskListIsNullWhenAggregateMemoryMethodInvoked() {
        transformer.aggregateMemoryUsageByTaskName(null);
    }

    @Test
    public void shouldReturnNameToMemoryMapWhenAggregateMemoryMethodInvoked() {
        // Given
        Task task1 = createTask(NAME, MEMORY_USAGE_1);
        Task task2 = createTask(NAME, MEMORY_USAGE_2);
        Task task3 = createTask(OTHER_NAME, OTHER_MEMORY_USAGE);
        Task task4 = createTask(NAME, MEMORY_USAGE_3);
        List<Task> tasks = Lists.newArrayList(task1, task2, task3, task4);
        // When
        List<Task> result = transformer.aggregateMemoryUsageByTaskName(tasks);
        // Then
        assertEquals(2, result.size());

        Task firstTask = result.get(0);
        assertEquals(NAME, firstTask.getName());
        assertEquals(AGGREGATED_MEMORY_USAGE, (long) firstTask.getMemoryUsage());

        Task secondTask = result.get(1);
        assertEquals(OTHER_NAME, secondTask.getName());
        assertEquals(OTHER_MEMORY_USAGE, (long) secondTask.getMemoryUsage());
    }

    private Task createTask(String name, long memoryUsage) {
        Task task = new Task();
        task.setName(name);
        task.setMemoryUsage(memoryUsage);
        return task;
    }

}
