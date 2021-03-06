package com.ns.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ns.model.Task;

public class ModulesCsvLineTaskParser extends AbstractCsvLineTaskParser {

    private static final Logger LOG = LoggerFactory.getLogger(ModulesCsvLineTaskParser.class);

    public ModulesCsvLineTaskParser(int columnsNumber) {
        super(columnsNumber);
    }

    @Override
    protected Task createTask(String[] values) {
        Task task = new Task();
        task.setName(values[0]);
        task.setPid(Integer.valueOf(values[1]));
        task.setModules(Lists.newArrayList(values[2].split(",")));
        LOG.debug("Created {}", task);
        return task;
    }


}
