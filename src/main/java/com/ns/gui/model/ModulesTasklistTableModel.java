package com.ns.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ns.model.Task;

public class ModulesTasklistTableModel extends AbstractTableModel implements TasklistTableModel {

    private static final long serialVersionUID = 4947341614837589363L;

    private static final int NAME_INDEX = 0;
    private static final int PID_INDEX = 1;
    private static final int MODULES_INDEX = 2;

    private String[] columnNames = new String[] { "Name", "PID", "Modules" };

    private List<Task> data;

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
        case NAME_INDEX:
        case MODULES_INDEX:
            return String.class;
        case PID_INDEX:
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
        case MODULES_INDEX:
            return record.getModules();
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
