package com.ns.util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableUtils {

    public static void adjustSizes(JTable table) {
        adjustRowSizes(table);
        for (int i = 0; i < table.getColumnCount(); i++) {
            adjustColumnSizes(table, i, 2);
        }
    }

    private static void adjustRowSizes(JTable table) {
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

    private static void adjustColumnSizes(JTable table, int column, int margin) {
        DefaultTableColumnModel model = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = model.getColumn(column);
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
