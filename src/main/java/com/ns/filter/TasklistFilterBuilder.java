package com.ns.filter;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.ns.model.Status;

public class TasklistFilterBuilder {

    public static final String FI = "/FI";

    private List<String> filters = Lists.newArrayList();

    public TasklistFilterBuilder addStatusFilter(Map<LogicOperator, List<Status>> criteria, boolean remote) {
        if (remote) {
            return this;
        }
        addFilterCommands(FilterName.STATUS, criteria);
        return this;
    }

    public TasklistFilterBuilder addImageNameFilter(Map<LogicOperator, List<String>> criteria) {
        addFilterCommands(FilterName.IMAGENAME, criteria);
        return this;
    }

    public TasklistFilterBuilder addPidFilter(Map<LogicOperator, List<Integer>> criteria) {
        addFilterCommands(FilterName.PID, criteria);
        return this;
    }

    public TasklistFilterBuilder addSessionFilter(Map<LogicOperator, List<Integer>> criteria) {
        addFilterCommands(FilterName.SESSION, criteria);
        return this;
    }

    public TasklistFilterBuilder addSessionNameFilter(Map<LogicOperator, List<String>> criteria) {
        addFilterCommands(FilterName.SESSIONNAME, criteria);
        return this;
    }

    public TasklistFilterBuilder addCpuTimeFilter(Map<LogicOperator, List<Duration>> criteria) {
        addFilterCommands(FilterName.CPUTIME, criteria);
        return this;
    }

    public TasklistFilterBuilder addMemoryUsageFilter(Map<LogicOperator, List<Long>> criteria) {
        addFilterCommands(FilterName.MEMUSAGE, criteria);
        return this;
    }

    public TasklistFilterBuilder addUsernameFilter(Map<LogicOperator, List<String>> criteria) {
        addFilterCommands(FilterName.USERNAME, criteria);
        return this;
    }

    public TasklistFilterBuilder addServicesFilter(Map<LogicOperator, List<String>> criteria) {
        addFilterCommands(FilterName.SERVICES, criteria);
        return this;
    }

    public TasklistFilterBuilder addWindowTitleFilter(Map<LogicOperator, List<String>> criteria, boolean remote) {
        if (remote) {
            return this;
        }
        addFilterCommands(FilterName.WINDOWTITILE, criteria);
        return this;
    }

    public TasklistFilterBuilder addModulesFilter(Map<LogicOperator, List<String>> criteria) {
        addFilterCommands(FilterName.MODULES, criteria);
        return this;
    }

    public void clear() {
        filters.clear();
    }

    public List<String> build() {
        return filters;
    }

    private <T> void addFilterCommands(FilterName filterName, Map<LogicOperator, List<T>> criteria) {
        List<String> filterArguments = buildFilterArguments(filterName, criteria);
        for (String argument : filterArguments) {
            filters.add(FI);
            filters.add(argument);
        }
    }

    private <T> List<String> buildFilterArguments(FilterName filterName, Map<LogicOperator, List<T>> criteria) {
        List<String> arguments = Lists.newArrayList();
        for (Entry<LogicOperator, List<T>> entry : criteria.entrySet()) {
            for (T object : entry.getValue()) {
                StringBuilder argumentBuilder = new StringBuilder();
                argumentBuilder.append("\"");
                argumentBuilder.append(filterName);
                argumentBuilder.append(" ");
                argumentBuilder.append(entry.getKey().getShortForm());
                argumentBuilder.append(" ");
                argumentBuilder.append(object.toString());
                argumentBuilder.append("\"");
                arguments.add(argumentBuilder.toString());
            }
        }
        return arguments;
    }
}
