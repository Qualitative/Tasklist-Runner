package com.ns.transfrormer;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

import java.util.List;
import java.util.Map;

import com.ns.model.Task;

public class TaskTransformer {

    public Map<String, Long> aggregateMemoryUsageByTaskName(List<Task> tasks) {
        checkNotNull(tasks);
        return tasks.stream().collect(groupingBy(Task::getName, summingLong(Task::getMemoryUsage)));
    }

}
