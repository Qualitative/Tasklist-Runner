package com.ns.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class GuiTools {

    public static void createRecommendedMargin(JButton... buttons) {
        for (JButton button : buttons) {
            Insets margin = button.getMargin();
            margin.left = 12;
            margin.right = 12;
            button.setMargin(margin);
        }
    }

    public static void makeSameSize(JComponent... components) {
        int[] sizes = new int[components.length];

        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = components[i].getPreferredSize().width;
        }

        int maxSizePos = maximumElementPosition(sizes);
        Dimension maxSize = components[maxSizePos].getPreferredSize();

        for (int i = 0; i < components.length; i++) {
            components[i].setPreferredSize(maxSize);
            components[i].setMinimumSize(maxSize);
            components[i].setMaximumSize(maxSize);
        }
    }

    public static void fixTextFieldSize(JTextField field) {
        Dimension size = field.getPreferredSize();
        size.width = field.getMaximumSize().width;
        field.setMaximumSize(size);
    }

    public static void addComponentsTo(JComponent component, Component... components) {
        for (Component comp : components) {
            component.add(comp);
        }
    }

    private static int maximumElementPosition(int[] array) {
        int maxPos = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxPos])
                maxPos = i;
        }
        return maxPos;
    }
}
