package com.ns.gui;

import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createHorizontalStrut;
import static javax.swing.Box.createVerticalStrut;

import java.awt.event.ItemEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.ns.gui.controller.GuiController;
import com.ns.util.BoxLayoutUtils;
import com.ns.util.GuiTools;

public class ControlPanel extends JPanel {

    private static final long serialVersionUID = -8500247375823145627L;

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
    private JButton exportToExcelButton;

    private GuiController controller;

    private JCheckBox useRemoteSystem;
    private JCheckBox useUserName;
    private JCheckBox usePassword;
    private JTextField remoteSystemField;
    private JTextField userNameField;
    private JTextField passwordField;

    public ControlPanel(GuiController controller) {
        this.controller = controller;

        this.useRemoteSystem = new JCheckBox("Remote PC:", false);
        this.useUserName = new JCheckBox("User name:", false);
        this.usePassword = new JCheckBox("User password:", false);
        this.remoteSystemField = new JTextField(20);
        this.userNameField = new JTextField(20);
        this.passwordField = new JTextField(20);

        this.buttonGroup = new ButtonGroup();
        this.standardRadioButton = createRadioButton("Standard", DisplayMode.STANDARD);
        this.verboseRadioButton = createRadioButton("Verbose", DisplayMode.VERBOSE);
        this.servicesRadioButton = createRadioButton("Services", DisplayMode.SERVICES);
        this.modulesRadioButton = createRadioButton("Modules", DisplayMode.MODULES);
        this.fullRadioButton = createRadioButton("Full", DisplayMode.FULL);
        this.groupByNameRadioButton = createRadioButton("Group by Name", DisplayMode.GROUP_BY_NAME);

        this.refreshButton = new JButton("Refresh");
        this.exportButton = new JButton("Export");
        this.importButton = new JButton("Import");
        this.exportToExcelButton = new JButton("To Excel");
    }

    public void init() {
        controller.setDisplayMode(DisplayMode.STANDARD);

        standardRadioButton.setSelected(true);

        addListeners();

        buttonGroup.add(standardRadioButton);
        buttonGroup.add(verboseRadioButton);
        buttonGroup.add(servicesRadioButton);
        buttonGroup.add(modulesRadioButton);
        buttonGroup.add(fullRadioButton);
        buttonGroup.add(groupByNameRadioButton);

        JPanel contentPanel = BoxLayoutUtils.createVerticalPanel();

        JPanel remotePanelH = BoxLayoutUtils.createHorizontalPanel();
        JPanel remotePanelV = BoxLayoutUtils.createVerticalPanel();

        useRemoteSystem.setEnabled(true);
        remoteSystemField.setEnabled(false);
        useUserName.setEnabled(false);
        userNameField.setEnabled(false);
        usePassword.setEnabled(false);
        passwordField.setEnabled(false);

        GuiTools.makeSameSize(useRemoteSystem, remoteSystemField, useUserName, userNameField, usePassword,
                passwordField);

        GuiTools.fixTextFieldSize(remoteSystemField);
        GuiTools.fixTextFieldSize(userNameField);
        GuiTools.fixTextFieldSize(passwordField);

        GuiTools.addComponentsTo(remotePanelV, useRemoteSystem, createVerticalStrut(2), remoteSystemField,
                createVerticalStrut(2), useUserName, createVerticalStrut(2), userNameField, createVerticalStrut(2),
                usePassword, createVerticalStrut(2), passwordField);

        GuiTools.addComponentsTo(remotePanelH, remotePanelV, createHorizontalGlue());

        BoxLayoutUtils.setGroupAlignmentX(0f, useRemoteSystem, remoteSystemField, useUserName, userNameField,
                usePassword, passwordField);

        JPanel displayModePanelH = BoxLayoutUtils.createHorizontalPanel();
        JPanel displayModePanelV = BoxLayoutUtils.createVerticalPanel();
        GuiTools.createRecommendedMargin(refreshButton);
        GuiTools.makeSameSize(standardRadioButton, verboseRadioButton, servicesRadioButton, modulesRadioButton,
                fullRadioButton, groupByNameRadioButton, refreshButton);
        GuiTools.addComponentsTo(displayModePanelV, standardRadioButton, verboseRadioButton, servicesRadioButton,
                modulesRadioButton, fullRadioButton, groupByNameRadioButton, refreshButton);

        JPanel xmlButtonPanel = BoxLayoutUtils.createHorizontalPanel();
        GuiTools.createRecommendedMargin(exportButton, importButton);
        GuiTools.makeSameSize(exportButton, importButton);
        GuiTools.addComponentsTo(xmlButtonPanel, exportButton, createHorizontalStrut(5), importButton,
                createHorizontalGlue());

        JPanel excelButtonPanel = BoxLayoutUtils.createHorizontalPanel();
        GuiTools.createRecommendedMargin(exportToExcelButton);
        GuiTools.addComponentsTo(excelButtonPanel, exportToExcelButton, createHorizontalGlue());

        GuiTools.addComponentsTo(displayModePanelH, displayModePanelV, createHorizontalGlue());

        GuiTools.addComponentsTo(contentPanel, remotePanelH, displayModePanelH, createVerticalStrut(20),
                xmlButtonPanel, createVerticalStrut(10), excelButtonPanel);

        add(contentPanel);
    }

    private JRadioButton createRadioButton(String caption, DisplayMode mode) {
        JRadioButton radioButton = new JRadioButton(caption);
        radioButton.addItemListener(e -> {
            checkOption(e, mode);
        });
        return radioButton;
    }

    private void addListeners() {

        useRemoteSystem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                remoteSystemField.setEnabled(true);
                useUserName.setEnabled(true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                remoteSystemField.setEnabled(false);
                useUserName.setSelected(false);
                useUserName.setEnabled(false);
                userNameField.setEnabled(false);
                userNameField.setText("");
                usePassword.setSelected(false);
                usePassword.setEnabled(false);
                passwordField.setEnabled(false);
                passwordField.setText("");
            }
        });

        useUserName.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                userNameField.setEnabled(true);
                usePassword.setEnabled(true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                userNameField.setText("");
                userNameField.setEnabled(false);
                usePassword.setSelected(false);
                usePassword.setEnabled(false);
                passwordField.setEnabled(false);
                passwordField.setText("");
            }
        });

        usePassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordField.setEnabled(true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                passwordField.setEnabled(false);
                passwordField.setText("");
            }
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

        exportToExcelButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnedValue = fileChooser.showSaveDialog(controller.getMainWindow());
            if (returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.saveToExcel(file);
            }
        });
    }

    private void checkOption(ItemEvent event, DisplayMode mode) {
        if (ItemEvent.SELECTED == event.getStateChange()) {
            controller.setDisplayMode(mode);
        }
    }
}
