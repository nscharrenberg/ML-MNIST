package com.nscharrenberg.enums;

import javafx.scene.paint.Color;

public enum Colors {
    EMPTY(0, Color.WHITE),
    BLACK(1, Color.BLACK);

    private int value;
    private double red;
    private double green;
    private double blue;

    Colors(int value, double r, double g, double b) {
        this.value = value;
        this.red = r;
        this.blue = b;
        this.green = g;
    }

    Colors(int value, Color color) {
        this.value = value;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }


    public int getValue() {
        return value;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }
}
