package com.ns.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ns.model.Task;

public class GroupByNameTasklistTableModel extends AbstractTableModel implements TasklistTableModel {

    private static final long serialVersionUID = -7339568877258826585L;

    private static final int NAME_INDEX = 0;
    private static final int MEMORY_USAGE_INDEX = 1;

    private String[] columnNames = new String[] { "Name", "Memory Usage" };

    private List<Task> data;

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
        case NAME_INDEX:
            return String.class;
        case MEMORY_USAGE_INDEX:
            return Long.class;
        default:
            return Object.class;
        }
    }

    public Object getValueAt(int row, int column) {
        Task record = data.get(row);
        switch (column) {
        case NAME_INDEX:
            return record.getName();
        case MEMORY_USAGE_INDEX:
            return record.getMemoryUsage();
        default:
            return new Object();
        }
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public void setData(List<Task> data) {
        this.data = data;
    }

}
