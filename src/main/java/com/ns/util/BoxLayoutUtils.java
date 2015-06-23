package com.ns.util;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class BoxLayoutUtils {

    public static void setGroupAlignmentX(float alignment, JComponent... components) {
        for (JComponent component : components) {
            component.setAlignmentX(alignment);
        }
    }

    public static void setGroupAlignmentY(float alignment, JComponent... components) {
        for (JComponent component : components) {
            component.setAlignmentY(alignment);
        }
    }

    public static JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    public static JPanel createHorizontalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

}
