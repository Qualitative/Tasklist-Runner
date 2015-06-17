package com.ns;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ns.command.OutputFormat;
import com.ns.command.TasklistCommandBuilder;
import com.ns.configuration.CommandConfiguration;
import com.ns.filter.TasklistFilterBuilder;
import com.ns.filter.LogicOperator;
import com.ns.model.Status;
import com.ns.model.Task;

@SuppressWarnings({ "resource", "unchecked" })
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.debug("Start application");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "spring/command-execution-service-context.xml");
        CommandConfiguration<List<Task>> tasklist = (CommandConfiguration<List<Task>>) applicationContext
                .getBean("tasklistConfiguration");
        TasklistFilterBuilder filterBuilder = new TasklistFilterBuilder();
        Map<LogicOperator, List<Status>> statusCriteria = Maps.newLinkedHashMap();
        statusCriteria.put(LogicOperator.EQUALS, Lists.newArrayList(Status.RUNNING));
        TasklistCommandBuilder commandBuilder = new TasklistCommandBuilder();
        List<String> filters = filterBuilder.addStatusFilter(statusCriteria, false).build();
        List<String> command = commandBuilder.withFormat(OutputFormat.CSV).withFilters(filters).withVerbose().withNoHeaders()
                .build();
        tasklist.run(command);
    }
}
