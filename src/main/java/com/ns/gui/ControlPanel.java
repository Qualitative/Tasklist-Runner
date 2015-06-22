package com.ns.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.ns.gui.controller.GuiController;

public class ControlPanel extends JPanel {

    private ButtonGroup buttonGroup;

    private JRadioButton standardRadioButton;
    private JRadioButton verboseRadioButton;
    private JRadioButton servicesRadioButton;
    private JRadioButton modulesRadioButton;
    private JRadioButton fullRadioButton;
    private JRadioButton groupByNameRadioButton;

    private JButton refreshButton;
    private JButton exportButton;
    private JButton importButton;

    private GuiController controller;

    public ControlPanel(GuiController controller) {
        this.controller = controller;

        this.buttonGroup = new ButtonGroup();
        this.standardRadioButton = new JRadioButton("Standard", true);
        this.verboseRadioButton = new JRadioButton("Verbose");
        this.servicesRadioButton = new JRadioButton("Services");
        this.modulesRadioButton = new JRadioButton("Modules");
        this.fullRadioButton = new JRadioButton("Full");
        this.groupByNameRadioButton = new JRadioButton("Group by Name");

        this.refreshButton = new JButton("Refresh");
        this.exportButton = new JButton("Export");
        this.importButton = new JButton("Import");
    }

    public void init() {
        controller.setDisplayMode(DisplayMode.STANDARD);

        buttonGroup.add(standardRadioButton);
        buttonGroup.add(verboseRadioButton);
        buttonGroup.add(servicesRadioButton);
        buttonGroup.add(modulesRadioButton);
        buttonGroup.add(fullRadioButton);
        buttonGroup.add(groupByNameRadioButton);

        addListeners();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(standardRadioButton);
        panel.add(verboseRadioButton);
        panel.add(servicesRadioButton);
        panel.add(modulesRadioButton);
        panel.add(fullRadioButton);
        panel.add(groupByNameRadioButton);

        panel.add(refreshButton);
        panel.add(exportButton);
        panel.add(importButton);

        add(panel);
    }

    private void addListeners() {

        standardRadioButton.addItemListener(e -> {
            checkOption(e, DisplayMode.STANDARD);
        });

        verboseRadioButton.addItemListener(e -> {
            checkOption(e, DisplayMode.VERBOSE);
        });

        servicesRadioButton.addItemListener(e -> {
            checkOption(e, DisplayMode.SERVICES);
        });

        modulesRadioButton.addItemListener(e -> {
            checkOption(e, DisplayMode.MODULES);
        });

        fullRadioButton.addItemListener(e -> {
            checkOption(e, DisplayMode.FULL);
        });

        groupByNameRadioButton.addItemListener(e -> {
            checkOption(e, DisplayMode.GROUP_BY_NAME);
        });

        refreshButton.addActionListener(e -> {
            controller.runCommand();
        });

        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnedValue = fileChooser.showSaveDialog(controller.getMainWindow());
            if (returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.saveTo(file);
            }
        });

        importButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnedValue = fileChooser.showOpenDialog(controller.getMainWindow());
            if (returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.loadFrom(file);
            }
        });
    }

    private void checkOption(ItemEvent event, DisplayMode mode) {
        if (ItemEvent.SELECTED == event.getStateChange()) {
            controller.setDisplayMode(mode);
        }
    }
}
