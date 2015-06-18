package com.ns.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.ns.dao.TaskDao;
import com.ns.model.Task;

public class TablePanel extends JPanel {

    private TaskDao taskDao;

    public TablePanel(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void init() {
        List<Task> data = taskDao.getAllTasks();
        JTable table = new JTable(new VerboseTasklistTableModel(data));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        table.setFillsViewportHeight(true);
        update(table);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private void update(JTable table) {
        System.out.println("updating");

        adjustJTableRowSizes(table);
        for (int i = 0; i < table.getColumnCount(); i++) {
            adjustColumnSizes(table, i, 2);
        }
    }

    private void adjustJTableRowSizes(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int maxHeight = 0;
            for (int column = 0; column < table.getColumnCount(); column++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Object valueAt = table.getValueAt(row, column);
                Component tableCellRendererComponent = cellRenderer.getTableCellRendererComponent(table, valueAt,
                        false, false, row, column);
                int heightPreferable = tableCellRendererComponent.getPreferredSize().height;
                maxHeight = Math.max(heightPreferable, maxHeight);
            }
            table.setRowHeight(row, maxHeight);
        }

    }

    public void adjustColumnSizes(JTable table, int column, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = colModel.getColumn(column);
        int width;

        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;

        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, column);
            comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, column), false, false, r, column);
            int currentWidth = comp.getPreferredSize().width;
            width = Math.max(width, currentWidth);
        }

        width += 2 * margin;

        col.setPreferredWidth(width);
        col.setWidth(width);
    }
}
