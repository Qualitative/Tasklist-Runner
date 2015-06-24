package com.ns.gui.model;

import java.time.Duration;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ns.model.Task;
import com.ns.util.TaskParserUtils;

public class VerboseTasklistTableModel extends AbstractTableModel implements TasklistTableModel {

    private static final long serialVersionUID = -4946400519407776358L;

    private static final int NAME_INDEX = 0;
    private static final int PID_INDEX = 1;
    private static final int SESSION_NAME_INDEX = 2;
    private static final int SESSION_NUMBER_INDEX = 3;
    private static final int MEMORY_USAGE_INDEX = 4;
    private static final int STATUS_INDEX = 5;
    private static final int USER_NAME_INDEX = 6;
    private static final int CPU_TIME_INDEX = 7;
    private static final int WINDOW_TITLE_INDEX = 8;

    private String[] columnNames = new String[] { "Name", "PID", "Session Name", "Session Number", "Memory Usage",
            "Status", "User Name", "Cpu Time", " Window Title" };

    private List<Task> data;

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
        case NAME_INDEX:
        case SESSION_NAME_INDEX:
        case STATUS_INDEX:
        case USER_NAME_INDEX:
        case WINDOW_TITLE_INDEX:
            return String.class;
        case PID_INDEX:
        case SESSION_NUMBER_INDEX:
        case MEMORY_USAGE_INDEX:
            return Long.class;
        case CPU_TIME_INDEX:
            return Duration.class;
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
        case STATUS_INDEX:
            return record.getStatus();
        case USER_NAME_INDEX:
            return record.getUserName();
        case CPU_TIME_INDEX:
            return TaskParserUtils.getDurationString(record.getCpuTime());
        case WINDOW_TITLE_INDEX:
            return record.getWindowTitle();
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
