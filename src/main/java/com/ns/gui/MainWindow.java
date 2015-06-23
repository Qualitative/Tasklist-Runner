package com.ns.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ns.gui.controller.GuiController;
import com.ns.util.BoxLayoutUtils;
import com.ns.util.GuiTools;

public class MainWindow extends JFrame {

    private GuiController controller;

    public MainWindow(GuiController controller) {
        super("Tasklist-Runner");
        this.controller = controller;
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width / 1.2);
        int height = (int) (screenSize.height / 1.2);
        Dimension size = new Dimension(width, height);
        setSize(size);
        setPreferredSize(size);
        setLocationRelativeTo(null);

        controller.setMainWindow(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        TasklistTablePanel tablePanel = new TasklistTablePanel(controller);
        tablePanel.init();

        FiltersTablePanel filterPanel = new FiltersTablePanel(controller);
        filterPanel.init();

        JPanel tablesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = .7;

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 7;
        c.gridwidth = 1;
        tablesPanel.add(tablePanel, c);

        c.weighty = .3;
        c.gridx = 0;
        c.gridy = 7;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        tablesPanel.add(filterPanel, c);


//        GuiTools.addComponentsTo(tablesPanel, tablePanel, filterPanel);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        ControlPanel controlPanel = new ControlPanel(controller);
        controlPanel.init();
        mainPanel.add(controlPanel, BorderLayout.EAST);

        add(mainPanel);
        pack();

    }
}
