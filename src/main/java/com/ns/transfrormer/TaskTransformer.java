package com.ns.transfrormer;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.ns.model.Task;

public class TaskTransformer {

    public List<Task> aggregateMemoryUsageByTaskName(List<Task> tasks) {

        checkNotNull(tasks);
        Map<String, Long> taskNameToMemoryMap = tasks.stream().collect(
                groupingBy(Task::getName, summingLong(Task::getMemoryUsage)));
        List<Task> groupedTasks = Lists.newArrayList();
        for (Entry<String, Long> entry : taskNameToMemoryMap.entrySet()) {
            Task task = new Task();
            task.setName(entry.getKey());
            task.setMemoryUsage(entry.getValue());
            groupedTasks.add(task);
        }

        return groupedTasks;
    }

}
