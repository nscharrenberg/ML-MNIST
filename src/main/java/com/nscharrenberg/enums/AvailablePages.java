package com.nscharrenberg.enums;

public enum AvailablePages {
    CANVAS("canvas", "Canvas"),
    TRAIN_AND_TEST("trainAndTest", "Train and Test"),
    MACHINE_LEARNING("machineLearning", "Machine Learning");

    private String id;
    private String name;

    private AvailablePages(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
