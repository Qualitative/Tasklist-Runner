package com.ns.gui;

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
        int width = (int) (screenSize.width / 1.1);
        int height = (int) (screenSize.height / 1.1);
        Dimension size = new Dimension(width, height);
        setSize(size);
        setPreferredSize(size);
        setLocationRelativeTo(null);

        MainPanel mainPanel = new MainPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        TablePanel tablePanel = new TablePanel(controller);
        tablePanel.init();
        mainPanel.add(tablePanel);

        ControlPanel controlPanel = new ControlPanel(controller);
        controlPanel.init();
        mainPanel.add(controlPanel);

        add(mainPanel);
        pack();

    }
}
