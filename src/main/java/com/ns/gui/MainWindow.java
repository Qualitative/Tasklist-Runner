package com.ns.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.ns.gui.controller.GuiController;

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

        controller.setMainWindowSize(size);
        controller.setMainWindow(this);

        MainPanel mainPanel = new MainPanel();

        TablePanel tablePanel = new TablePanel(controller);
        tablePanel.init();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        ControlPanel controlPanel = new ControlPanel(controller);
        controlPanel.init();
        mainPanel.add(controlPanel, BorderLayout.EAST);

        add(mainPanel);
        pack();

    }
}
