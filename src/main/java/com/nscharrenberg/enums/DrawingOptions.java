package com.nscharrenberg.enums;

public enum DrawingOptions {
    PENCIL("tool_pencil", "Pencil", "fas-pencil-alt"),
    ERASED("tool_eraser", "Erased", "fas-eraser"),
    EXPORTER("tool_exporter", "Save", "fas-save"),
    PREDICT("tool_predict", "Predict", "fas-chart-line");

    private String id;
    private String name;
    private String iconName;

    DrawingOptions(String id, String name, String iconName) {
        this.id = id;
        this.name = name;
        this.iconName = iconName;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.iconName;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
