package com.ns.gui;

import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createHorizontalStrut;
import static javax.swing.Box.createVerticalStrut;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.ns.filter.Filter;
import com.ns.filter.FilterName;
import com.ns.filter.LogicOperator;
import com.ns.gui.controller.GuiController;
import com.ns.gui.model.FilterTableModel;
import com.ns.model.Status;
import com.ns.util.BoxLayoutUtils;
import com.ns.util.GuiTools;

public class FiltersTablePanel extends JPanel {

    private static final long serialVersionUID = -2435481099528404821L;

    private JButton addFilterButton;
    private JButton removeFilterButton;

    private JTable filterTable;
    private FilterTableModel tableModel;

    private GuiController controller;

    public FiltersTablePanel(GuiController controller) {
        this.controller = controller;

        addFilterButton = new JButton("Add");
        removeFilterButton = new JButton("Remove");

        filterTable = new JTable();
        tableModel = new FilterTableModel();
    }

    public void init() {
        setLayout(new GridLayout(1, 1));
        JPanel contentPanelV = BoxLayoutUtils.createVerticalPanel();
        JPanel buttonPanel = BoxLayoutUtils.createHorizontalPanel();

        addListeners();

        GuiTools.createRecommendedMargin(addFilterButton, removeFilterButton);
        GuiTools.makeSameSize(addFilterButton, removeFilterButton);
        GuiTools.addComponentsTo(buttonPanel, addFilterButton, createHorizontalStrut(5), removeFilterButton,
                createHorizontalGlue());

        filterTable.setModel(tableModel);

        setUpFilterNameColumn(filterTable, filterTable.getColumnModel().getColumn(0));
        setUpLogicOperatorColumn(filterTable, filterTable.getColumnModel().getColumn(1));

        filterTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        filterTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(filterTable);

        GuiTools.addComponentsTo(contentPanelV, buttonPanel, createVerticalStrut(2), scrollPane);

        add(contentPanelV);
    }

    private void addListeners() {
        addFilterButton.addActionListener(e -> {
            Filter filter = new Filter(FilterName.STATUS, LogicOperator.EQUALS, Status.RUNNING.getName());
            tableModel.addRow(filter);
            selectRow(filterTable.getRowCount());
        });

        removeFilterButton.addActionListener(e -> {
            int selectedRow = filterTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                selectRow(0);
            }
        });
    }

    private void setUpFilterNameColumn(JTable table, TableColumn filterNameColumn) {
        JComboBox<FilterName> comboBox = new JComboBox<FilterName>();
        for (FilterName name : FilterName.values()) {
            comboBox.addItem(name);
        }
        filterNameColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    private void setUpLogicOperatorColumn(JTable table, TableColumn logicOperatorColumn) {
        JComboBox<LogicOperator> comboBox = new JComboBox<LogicOperator>();
        for (LogicOperator name : LogicOperator.values()) {
            comboBox.addItem(name);
        }
        logicOperatorColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    private void selectRow(int row) {
        int count = filterTable.getRowCount();

        if (count == 0) {
            return;
        }

        if (row >= count) {
            row = count - 1;
        }

        if (row < 0) {
            row = 0;
        }

        filterTable.setRowSelectionInterval(row, row);
    }

}
