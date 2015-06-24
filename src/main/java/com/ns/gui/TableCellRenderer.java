package com.ns.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1944218686121391934L;
    private static final Color LIGHT_GREEN = new Color(208, 240, 192);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(row % 2 == 0 ? LIGHT_GREEN : Color.WHITE);
        return c;
    }
}
