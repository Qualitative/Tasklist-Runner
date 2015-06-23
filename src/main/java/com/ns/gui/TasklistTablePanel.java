package com.ns.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.google.common.collect.ImmutableMap;
import com.ns.gui.controller.GuiController;
import com.ns.gui.model.GroupByNameTasklistTableModel;
import com.ns.gui.model.ModulesTasklistTableModel;
import com.ns.gui.model.ServicesTasklistTableModel;
import com.ns.gui.model.StandardTasklistTableModel;
import com.ns.gui.model.TasklistTableModel;
import com.ns.gui.model.FullTasklistTableModel;
import com.ns.gui.model.VerboseTasklistTableModel;
import com.ns.model.Task;
import com.ns.util.TableUtils;

public class TasklistTablePanel extends JPanel {

    private GuiController controller;

    private JTable table = new JTable();

    private Map<DisplayMode, TasklistTableModel> modeToModelMap = ImmutableMap
            .<DisplayMode, TasklistTableModel> builder().put(DisplayMode.STANDARD, new StandardTasklistTableModel())
            .put(DisplayMode.VERBOSE, new VerboseTasklistTableModel())
            .put(DisplayMode.SERVICES, new ServicesTasklistTableModel())
            .put(DisplayMode.MODULES, new ModulesTasklistTableModel())
            .put(DisplayMode.FULL, new FullTasklistTableModel())
            .put(DisplayMode.GROUP_BY_NAME, new GroupByNameTasklistTableModel()).build();

    public TasklistTablePanel(GuiController controller) {
        this.controller = controller;
    }

    public void init() {
        setLayout(new GridLayout(1, 1));
        controller.setTablePanel(this);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFillsViewportHeight(true);

        TableUtils.adjustSizes(table);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public void updateData(DisplayMode mode, List<Task> data) {
        TasklistTableModel model = modeToModelMap.get(mode);

        if (model == null) {
            throw new IllegalStateException("There is no table model for display mode: " + mode);
        }

        model.setData(data);
        table.setModel(model);
        TableUtils.adjustSizes(table);
    }
}
