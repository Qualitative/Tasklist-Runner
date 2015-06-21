package com.ns.gui.controller;

import java.util.List;

import com.ns.dao.TaskDao;
import com.ns.gui.DisplayMode;
import com.ns.gui.TablePanel;
import com.ns.model.Task;

public class GuiController {

    private TaskDao taskDao;
    private DisplayMode displayMode;
    private TablePanel tablePanel;

    public GuiController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void setDisplayMode(DisplayMode mode) {
        this.displayMode = mode;
        tablePanel.updateData();
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public List<Task> getTasks() {
        return taskDao.getAllTasks();
    }

    public void runCommand() {
        tablePanel.updateData();
    }

    public void setTablePanel(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

}
