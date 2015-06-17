package com.ns.command;

import java.util.List;

import com.google.common.collect.Lists;

// TODO: provide more complex logic for checking constraints
public class TasklistCommandBuilder {

    private List<String> commands = Lists.newArrayList("tasklist");
    private boolean withRemoteSystem = false;
    private boolean withUser = false;
    private boolean withModules = false;
    private boolean withServices = false;
    private boolean verbose = false;
    private boolean noHeaders = false;
    private OutputFormat format = OutputFormat.TABLE;

    public TasklistCommandBuilder withRemoteSystem(String remotSystem) {
        commands.add("/S");
        commands.add(remotSystem);
        withRemoteSystem = true;
        return this;
    }

    public TasklistCommandBuilder withUser(String user) {
        if (!withRemoteSystem) {
            return this;
        }
        commands.add("/U");
        commands.add(user);
        return this;
    }

    public TasklistCommandBuilder withDomainAndUser(String domain, String user) {
        if (!withRemoteSystem) {
            return this;
        }
        commands.add("/U");
        commands.add(domain + "\\" + user);
        return this;
    }

    public TasklistCommandBuilder withPassword(String password) {
        if (!withUser) {
            return this;
        }
        commands.add("/P");
        commands.add(password);
        return this;
    }

    public TasklistCommandBuilder withModules() {
        if (withServices || verbose) {
            return this;
        }
        withModules = true;
        commands.add("/M");
        return this;
    }

    public TasklistCommandBuilder withServices() {
        if (withModules || verbose) {
            return this;
        }
        withServices = true;
        commands.add("/SVC");
        return this;
    }

    public TasklistCommandBuilder withVerbose() {
        if (withModules || withServices) {
            return this;
        }
        verbose = true;
        commands.add("/V");
        return this;
    }

    public TasklistCommandBuilder withFilters(List<String> filters) {
        commands.addAll(filters);
        return this;
    }

    public TasklistCommandBuilder withFormat(OutputFormat format) {
        if (noHeaders && format == OutputFormat.LIST) {
            return this;
        }
        this.format = format;
        commands.add("/FO");
        commands.add(format.name());
        return this;
    }

    public TasklistCommandBuilder withNoHeaders() {
        if (format != OutputFormat.TABLE && format != OutputFormat.CSV) {
            return this;
        }
        noHeaders = true;
        commands.add("/NH");
        return this;
    }

    public void clear() {
        commands.clear();
    }

    public List<String> build() {
        return commands;
    }

}
