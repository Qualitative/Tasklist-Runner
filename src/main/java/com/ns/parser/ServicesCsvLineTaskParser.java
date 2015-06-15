package com.ns.parser;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.model.Task;

public class ServicesCsvLineTaskParser extends AbstractCsvLineTaskParser {

    private static final Logger LOG = LoggerFactory.getLogger(ServicesCsvLineTaskParser.class);

    public ServicesCsvLineTaskParser(int columnsNumber) {
        super(columnsNumber);
    }

    @Override
    protected Task createTask(String[] values) {
        Task task = new Task();
        task.setName(values[0]);
        task.setPid(Integer.valueOf(values[1]));
        task.setServices(Arrays.asList(values[2].split(",")));
        LOG.debug("Created {}", task);
        return task;
    }

}
