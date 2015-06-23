package com.ns.gui.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.ns.configuration.CommandConfiguration;
import com.ns.dao.TaskDao;
import com.ns.excel.ExcelConverter;
import com.ns.exception.ConvertException;
import com.ns.gui.DisplayMode;
import com.ns.gui.MainWindow;
import com.ns.gui.TasklistTablePanel;
import com.ns.model.Task;
import com.ns.oxm.XmlConverter;
import com.ns.transfrormer.TaskTransformer;

public class GuiController {

    private TaskDao taskDao;
    private TaskTransformer transformer;
    private List<CommandConfiguration<List<Task>>> configurations;

    private DisplayMode displayMode;
    private TasklistTablePanel tablePanel;
    private XmlConverter<Task> xmlConverter;
    private ExcelConverter excelConverter;
    private MainWindow mainWindow;

    public GuiController(TaskDao taskDao, TaskTransformer transformer, XmlConverter<Task> xmlConverter,
            ExcelConverter excelConverter, List<CommandConfiguration<List<Task>>> configurations) {
        this.taskDao = taskDao;
        this.transformer = transformer;
        this.xmlConverter = xmlConverter;
        this.excelConverter = excelConverter;
        this.configurations = configurations;
    }

    public void setDisplayMode(DisplayMode mode) {
        this.displayMode = mode;
        List<Task> data = taskDao.getAllTasks();
        if (mode == DisplayMode.GROUP_BY_NAME) {
            data = transformer.aggregateMemoryUsageByTaskName(data);
        }
        tablePanel.updateData(mode, data);
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public List<Task> getTasks() {
        return taskDao.getAllTasks();
    }

    public void runCommand() {
        taskDao.removeAllTasks();
        for (CommandConfiguration<List<Task>> configuration : configurations) {
            configuration.run();
        }
        setDisplayMode(displayMode);
    }

    public void setTablePanel(TasklistTablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public void saveTo(File file) {
        List<Task> data = taskDao.getAllTasks();
        data = transformer.aggregateMemoryUsageByTaskName(data);
        try {
            xmlConverter.convertFromListToXml(data, file, "tasks");
        } catch (ConvertException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadFrom(File file) {
        try {
            List<Task> data = xmlConverter.convertFromXmlToList(file);
            tablePanel.updateData(DisplayMode.GROUP_BY_NAME, data);
        } catch (ConvertException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void saveToExcel(File file) {
        List<Task> tasks = taskDao.getAllTasks();
        try {
            excelConverter.generateExcelChart(tasks, file);
        } catch (InvalidFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
