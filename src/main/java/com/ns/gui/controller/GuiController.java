package com.ns.gui.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ns.command.TasklistCommandBuilder;
import com.ns.configuration.CommandConfiguration;
import com.ns.dao.TaskDao;
import com.ns.excel.ExcelConverter;
import com.ns.exception.ConvertException;
import com.ns.filter.Filter;
import com.ns.filter.FilterName;
import com.ns.filter.TasklistFilterBuilder;
import com.ns.gui.ControlPanel;
import com.ns.gui.DisplayMode;
import com.ns.gui.FiltersTablePanel;
import com.ns.gui.MainWindow;
import com.ns.gui.TasklistTablePanel;
import com.ns.model.Task;
import com.ns.oxm.XmlConverter;
import com.ns.transfrormer.TaskTransformer;

public class GuiController {

    private static final Logger LOG = LoggerFactory.getLogger(GuiController.class);

    private TaskDao taskDao;
    private TaskTransformer transformer;
    private CommandConfiguration<List<Task>> remoteTasklistConfiguration;
    private CommandConfiguration<List<Task>> tasklistConfiguration;
    private CommandConfiguration<List<Task>> servicesConfiguration;
    private CommandConfiguration<List<Task>> modulesConfiguration;

    private List<String> remoteTasklistCommand;
    private List<String> tasklistCommand;
    private List<String> servicesCommand;
    private List<String> modulesCommand;

    private DisplayMode displayMode;
    private TasklistTablePanel tablePanel;
    private XmlConverter<Task> xmlConverter;
    private ExcelConverter excelConverter;
    private MainWindow mainWindow;
    private ControlPanel controlPanel;
    private FiltersTablePanel filtersTablePanel;

    private List<Task> data = Lists.newArrayList();
    private List<Task> aggregatedData = Lists.newArrayList();

    private SwingWorker<List<Task>, Void> worker;

    public GuiController(TaskDao taskDao, TaskTransformer transformer, XmlConverter<Task> xmlConverter,
            ExcelConverter excelConverter, CommandConfiguration<List<Task>> remoteTasklistConfiguration,
            CommandConfiguration<List<Task>> tasklistConfiguration,
            CommandConfiguration<List<Task>> servicesConfiguration,
            CommandConfiguration<List<Task>> modulesConfiguration, List<String> remoteTasklistCommand,
            List<String> tasklistCommand, List<String> servicesCommand, List<String> modulesCommand) {
        this.taskDao = taskDao;
        this.transformer = transformer;
        this.xmlConverter = xmlConverter;
        this.excelConverter = excelConverter;
        this.remoteTasklistConfiguration = remoteTasklistConfiguration;
        this.tasklistConfiguration = tasklistConfiguration;
        this.servicesConfiguration = servicesConfiguration;
        this.modulesConfiguration = modulesConfiguration;
        this.remoteTasklistCommand = remoteTasklistCommand;
        this.tasklistCommand = tasklistCommand;
        this.servicesCommand = servicesCommand;
        this.modulesCommand = modulesCommand;
    }

    public void setFilterTablePanel(FiltersTablePanel filtersTablePanel) {
        this.filtersTablePanel = filtersTablePanel;
    }

    public void setTablePanel(TasklistTablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setDisplayMode(DisplayMode mode) {
        this.displayMode = mode;
        if (mode == DisplayMode.GROUP_BY_NAME) {
            tablePanel.updateData(mode, aggregatedData);
        } else {
            tablePanel.updateData(mode, data);
        }
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public void runCommand() {
        final List<String> adjRemoteCommand = adjustConfigurationCommand(remoteTasklistConfiguration,
                remoteTasklistCommand, Lists.newArrayList(FilterName.STATUS, FilterName.WINDOWTITILE));
        final List<String> adjTasklistCommand = adjustConfigurationCommand(tasklistConfiguration, tasklistCommand,
                Lists.newArrayList());
        final List<String> adjServicesCommand = adjustConfigurationCommand(servicesConfiguration, servicesCommand,
                Lists.newArrayList());
        final List<String> adjModulesCommand = adjustConfigurationCommand(modulesConfiguration, modulesCommand,
                Lists.newArrayList());

        worker = new SwingWorker<List<Task>, Void>() {

            @Override
            protected List<Task> doInBackground() throws Exception {
                controlPanel.started();
                taskDao.removeAllTasks();
                String remoteSystem = controlPanel.getRemoteSystem();
                if (!StringUtils.isEmpty(remoteSystem)) {
                    remoteTasklistConfiguration.run(adjRemoteCommand);
                } else {
                    tasklistConfiguration.run(adjTasklistCommand);
                }
                servicesConfiguration.run(adjServicesCommand);
                modulesConfiguration.run(adjModulesCommand);
                data = taskDao.getAllTasks();
                return data;
            }

            @Override
            protected void done() {
                controlPanel.finished();
                aggregatedData = transformer.aggregateMemoryUsageByTaskName(data);
                setDisplayMode(displayMode);
            }
        };

        worker.execute();
    }

    public void cancelCommand() {
        worker.cancel(true);
        JOptionPane.showMessageDialog(mainWindow, "Tasklist command was interrupted.\n"
                + "Table contains partial results", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private List<String> adjustConfigurationCommand(CommandConfiguration<List<Task>> configuration,
            List<String> commands, List<FilterName> filtersToExclude) {
        TasklistCommandBuilder commandBuilder = new TasklistCommandBuilder();

        commandBuilder.setCommands(Lists.newArrayList(commands));

        String remoteSystem = controlPanel.getRemoteSystem();
        String userName = controlPanel.getUserName();
        String userPassword = controlPanel.getUserPassword();

        if (!StringUtils.isEmpty(remoteSystem)) {
            commandBuilder.withRemoteSystem(remoteSystem);
        }
        if (!StringUtils.isEmpty(userName)) {
            commandBuilder.withUser(userName);
        }
        if (!StringUtils.isEmpty(userPassword)) {
            commandBuilder.withPassword(userPassword);
        }

        List<Filter> filtersList = filtersTablePanel.getFilters();

        List<Filter> reducedFilters = filtersList.stream()
                .filter(filter -> !filtersToExclude.contains(filter.getName())).collect(Collectors.toList());
        TasklistFilterBuilder filterBuilder = new TasklistFilterBuilder();
        List<String> filters = filterBuilder.buildFiltersCommand(reducedFilters);

        return commandBuilder.withFilters(filters).build();
    }

    public void saveToXml(File file) {
        try {
            xmlConverter.convertFromListToXml(aggregatedData, file, "tasks");
        } catch (ConvertException e) {
            LOG.error("Can't export data to XML file", e);
            JOptionPane.showMessageDialog(mainWindow, "Can't export data to XML file. See logs for details", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadFromXml(File file) {
        try {
            List<Task> data = xmlConverter.convertFromXmlToList(file);
            controlPanel.clearSelection();
            tablePanel.updateData(DisplayMode.GROUP_BY_NAME, data);
        } catch (ConvertException e) {
            LOG.error("Can't import data from XML file", e);
            JOptionPane.showMessageDialog(mainWindow, "Can't import data from XML file. See logs for details", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveToExcel(File file) {
        try {
            excelConverter.generateExcelChart(data, file);
        } catch (InvalidFormatException | IOException e) {
            LOG.error("Can't export data to Excel file", e);
            JOptionPane.showMessageDialog(mainWindow, "Can't export data to Excel file. See logs for details", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
