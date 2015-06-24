package com.ns.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.model.Task;
import com.ns.util.TaskParserUtils;

public class RemoteVerboseCsvLineTaskParser extends AbstractCsvLineTaskParser {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteVerboseCsvLineTaskParser.class);

    public RemoteVerboseCsvLineTaskParser(int columnsNumber) {
        super(columnsNumber);
    }

    @Override
    protected Task createTask(String[] values) {
        Task task = new Task();
        task.setName(values[0]);
        task.setPid(Integer.valueOf(values[1]));
        task.setSessionName(values[2]);
        task.setSessionNumber(Integer.valueOf(values[3]));
        task.setMemoryUsage(TaskParserUtils.getMemoryUsage(values[4]));
        task.setUserName(values[5]);
        task.setCpuTime(TaskParserUtils.getCpuTime(values[6]));
        LOG.debug("Created {}", task);
        return task;
    }

}
