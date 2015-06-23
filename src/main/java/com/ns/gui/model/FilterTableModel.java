package com.ns.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.google.common.collect.Lists;
import com.ns.filter.Filter;
import com.ns.filter.FilterName;
import com.ns.filter.LogicOperator;

public class FilterTableModel extends AbstractTableModel {

    private static final int NAME_INDEX = 0;
    private static final int OPERATOR_INDEX = 1;
    private static final int VALUE_INDEX = 2;

    private String[] columnNames = new String[] { "Name", "Operator", "Value" };

    private List<Filter> data = Lists.newArrayList();

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Filter filter = data.get(rowIndex);
        switch (columnIndex) {
        case NAME_INDEX:
            return filter.getName();
        case OPERATOR_INDEX:
            return filter.getOperator();
        case VALUE_INDEX:
            return filter.getValue();
        default:
            return new Object();
        }
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        Filter filter = data.get(row);
        switch (col) {
        case NAME_INDEX:
            filter.setName((FilterName) value);
            break;
        case OPERATOR_INDEX:
            filter.setOperator((LogicOperator) value);
            break;
        case VALUE_INDEX:
            filter.setValue((String) value);
            break;
        }
        fireTableCellUpdated(row, col);
    }

    public void setData(List<Filter> filters) {
        data = filters;
    }

    public void removeRow(int selectedRow) {
        data.remove(selectedRow);
        fireTableDataChanged();
    }

    public void addRow(Filter filter) {
        data.add(filter);
        fireTableDataChanged();
    }

}
