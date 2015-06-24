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
import javax.swing.JPasswordField;
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
    private JButton cancelButton;
    private JButton exportToXmlButton;
    private JButton importFromXmlButton;
    private JButton exportToExcelButton;

    private GuiController controller;

    private JCheckBox useRemoteSystem;
    private JCheckBox useUserName;
    private JCheckBox usePassword;
    private JTextField remoteSystemField;
    private JTextField userNameField;
    private JPasswordField passwordField;

    private JFileChooser fileChooser;
    private FileTypeFilter xmlFilter;
    private FileTypeFilter xlsxFilter;

    public ControlPanel(GuiController controller) {
        this.controller = controller;

        this.useRemoteSystem = new JCheckBox("Remote PC:", false);
        this.useUserName = new JCheckBox("User name:", false);
        this.usePassword = new JCheckBox("User password:", false);
        this.remoteSystemField = new JTextField(20);
        this.userNameField = new JTextField(20);
        this.passwordField = new JPasswordField(20);

        this.buttonGroup = new ButtonGroup();
        this.standardRadioButton = createRadioButton("Standard", DisplayMode.STANDARD);
        this.verboseRadioButton = createRadioButton("Verbose", DisplayMode.VERBOSE);
        this.servicesRadioButton = createRadioButton("Services", DisplayMode.SERVICES);
        this.modulesRadioButton = createRadioButton("Modules", DisplayMode.MODULES);
        this.fullRadioButton = createRadioButton("Full", DisplayMode.FULL);
        this.groupByNameRadioButton = createRadioButton("Group by Name", DisplayMode.GROUP_BY_NAME);

        this.refreshButton = new JButton("Refresh");
        this.cancelButton = new JButton("Cancel");
        this.exportToXmlButton = new JButton("To XML ...");
        this.importFromXmlButton = new JButton("From XML ...");
        this.exportToExcelButton = new JButton("To Excel ...");

        this.fileChooser = new JFileChooser();
        this.xmlFilter = new FileTypeFilter(".xml", "eXtensible Markup Language Documents");
        this.xlsxFilter = new FileTypeFilter(".xlsx", "Microsoft Excel Documents");

    }

    public void init() {
        controller.setControlPanel(this);
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

        GuiTools.createRecommendedMargin(refreshButton, cancelButton);
        GuiTools.makeSameSize(refreshButton, cancelButton);
        GuiTools.makeSameSize(standardRadioButton, verboseRadioButton, servicesRadioButton, modulesRadioButton,
                fullRadioButton, groupByNameRadioButton);
        GuiTools.addComponentsTo(displayModePanelV, standardRadioButton, verboseRadioButton, servicesRadioButton,
                modulesRadioButton, fullRadioButton, groupByNameRadioButton);

        JPanel controlButtonPanel = BoxLayoutUtils.createHorizontalPanel();
        GuiTools.addComponentsTo(controlButtonPanel, refreshButton, createHorizontalStrut(5), cancelButton,
                createHorizontalGlue());

        JPanel xmlButtonPanel = BoxLayoutUtils.createHorizontalPanel();
        GuiTools.createRecommendedMargin(exportToXmlButton, importFromXmlButton, exportToExcelButton);
        GuiTools.makeSameSize(exportToXmlButton, importFromXmlButton, exportToExcelButton);
        GuiTools.addComponentsTo(xmlButtonPanel, exportToXmlButton, createHorizontalStrut(5), importFromXmlButton,
                createHorizontalGlue());

        JPanel excelButtonPanel = BoxLayoutUtils.createHorizontalPanel();
        GuiTools.addComponentsTo(excelButtonPanel, exportToExcelButton, createHorizontalGlue());

        GuiTools.addComponentsTo(displayModePanelH, displayModePanelV, createHorizontalGlue());

        GuiTools.addComponentsTo(contentPanel, remotePanelH, displayModePanelH, createHorizontalStrut(5),
                controlButtonPanel, createVerticalStrut(20), xmlButtonPanel, createVerticalStrut(10), excelButtonPanel);

        add(contentPanel);
    }

    public void clearSelection() {
        buttonGroup.clearSelection();
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
                usePassword.setSelected(false);
                usePassword.setEnabled(false);
                passwordField.setEnabled(false);
            }
        });

        useUserName.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                userNameField.setEnabled(true);
                usePassword.setEnabled(true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                userNameField.setEnabled(false);
                usePassword.setSelected(false);
                usePassword.setEnabled(false);
                passwordField.setEnabled(false);
            }
        });

        usePassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordField.setEnabled(true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                passwordField.setEnabled(false);
            }
        });

        refreshButton.addActionListener(e -> {
            controller.runCommand();
        });

        cancelButton.addActionListener(e -> {
            controller.cancelCommand();
        });

        exportToXmlButton.addActionListener(e -> {
            fileChooser.addChoosableFileFilter(xmlFilter);
            fileChooser.setFileFilter(xmlFilter);
            int returnedValue = fileChooser.showSaveDialog(controller.getMainWindow());
            if (returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                File updatedFile = addExtensionIfAbsent(file, xmlFilter.getExtension());
                controller.saveToXml(updatedFile);
            }
            fileChooser.removeChoosableFileFilter(xmlFilter);
        });

        importFromXmlButton.addActionListener(e -> {
            fileChooser.addChoosableFileFilter(xmlFilter);
            fileChooser.setFileFilter(xmlFilter);
            int returnedValue = fileChooser.showOpenDialog(controller.getMainWindow());
            if (returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.loadFromXml(file);
            }
            fileChooser.removeChoosableFileFilter(xmlFilter);
        });

        exportToExcelButton.addActionListener(e -> {
            fileChooser.addChoosableFileFilter(xlsxFilter);
            fileChooser.setFileFilter(xlsxFilter);
            int returnedValue = fileChooser.showSaveDialog(controller.getMainWindow());
            if (returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                File updatedFile = addExtensionIfAbsent(file, xlsxFilter.getExtension());
                controller.saveToExcel(updatedFile);
            }
            fileChooser.removeChoosableFileFilter(xlsxFilter);
        });
    }

    private File addExtensionIfAbsent(File file, String extension) {
        String path = file.getAbsolutePath().toLowerCase();
        if (!path.endsWith(extension.toLowerCase())) {
            return new File(path + extension);
        } else {
            return file;
        }

    }

    private void checkOption(ItemEvent event, DisplayMode mode) {
        if (ItemEvent.SELECTED == event.getStateChange()) {
            controller.setDisplayMode(mode);
        }
    }

    public String getRemoteSystem() {
        if (useRemoteSystem.isSelected()) {
            return remoteSystemField.getText();
        } else {
            return null;
        }
    }

    public String getUserName() {
        if (useUserName.isSelected()) {
            return userNameField.getText();
        } else {
            return null;
        }
    }

    public String getUserPassword() {
        if (usePassword.isSelected()) {
            return new String(passwordField.getPassword());
        } else {
            return null;
        }
    }

    public void started() {
        cancelButton.setEnabled(true);
        refreshButton.setEnabled(false);
    }

    public void finished() {
        cancelButton.setEnabled(false);
        refreshButton.setEnabled(true);
    }

}
