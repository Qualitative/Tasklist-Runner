package com.ns.filter;

import java.util.List;

import com.google.common.collect.Lists;

public class TasklistFilterBuilder {

    public static final String FI = "/FI";

    private List<String> filters = Lists.newArrayList();

    public void clear() {
        filters.clear();
    }

    public List<String> buildFiltersCommand(List<Filter> filtersList) {
        List<String> filterArguments = buildFilterArguments(filtersList);
        for (String argument : filterArguments) {
            filters.add(FI);
            filters.add(argument);
        }

        return filters;
    }

    private <T> List<String> buildFilterArguments(List<Filter> filters) {
        List<String> arguments = Lists.newArrayList();
        for (Filter filter : filters) {
            StringBuilder argumentBuilder = new StringBuilder();
            argumentBuilder.append("\"");
            argumentBuilder.append(filter.getName());
            argumentBuilder.append(" ");
            argumentBuilder.append(filter.getOperator().getShortForm());
            argumentBuilder.append(" ");
            argumentBuilder.append(filter.getValue());
            argumentBuilder.append("\"");
            arguments.add(argumentBuilder.toString());
        }
        return arguments;
    }
}
