package com.ns.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.model.Status;
import com.ns.model.Task;
import com.ns.util.TaskParserUtils;

public class VerboseCsvLineTaskParser extends AbstractCsvLineTaskParser {

    private static final Logger LOG = LoggerFactory.getLogger(VerboseCsvLineTaskParser.class);

    public VerboseCsvLineTaskParser(int columnsNumber) {
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
        task.setStatus(Status.valueOfIgnoreCase(values[5]));
        task.setUserName(values[6]);
        task.setCpuTime(TaskParserUtils.getCpuTime(values[7]));
        task.setWindowTitle(values[8]);
        LOG.debug("Created {}", task);
        return task;
    }

}
