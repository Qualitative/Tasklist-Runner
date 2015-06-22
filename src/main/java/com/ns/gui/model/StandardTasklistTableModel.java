package com.ns.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ns.model.Task;

public class StandardTasklistTableModel extends AbstractTableModel implements TasklistTableModel {

    private static final int NAME_INDEX = 0;
    private static final int PID_INDEX = 1;
    private static final int SESSION_NAME_INDEX = 2;
    private static final int SESSION_NUMBER_INDEX = 3;
    private static final int MEMORY_USAGE_INDEX = 4;

    private String[] columnNames = new String[] { "Name", "PID", "Session Name", "Session Number", "Memory Usage" };

    private List<Task> data;

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
        case NAME_INDEX:
        case SESSION_NAME_INDEX:
            return String.class;
        case PID_INDEX:
        case SESSION_NUMBER_INDEX:
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
        case PID_INDEX:
            return record.getPid();
        case SESSION_NAME_INDEX:
            return record.getSessionName();
        case SESSION_NUMBER_INDEX:
            return record.getSessionNumber();
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
