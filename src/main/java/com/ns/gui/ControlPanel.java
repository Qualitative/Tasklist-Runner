package com.ns.gui;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {

    private JCheckBox verboseCheckBox = new JCheckBox("Verbose");
    private JCheckBox servicesCheckBox = new JCheckBox("Services");
    private JCheckBox modulesCheckBox = new JCheckBox("Modules");

    public void init() {
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(verboseCheckBox);
        verticalBox.add(servicesCheckBox);
        verticalBox.add(modulesCheckBox);
        add(verticalBox);
    }
}
