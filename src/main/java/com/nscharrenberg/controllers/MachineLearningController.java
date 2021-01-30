package com.nscharrenberg.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MachineLearningController {
    @FXML
    private AnchorPane root;

    @FXML
    public void initialize() {
        this.root.getChildren().add(new Label("Machine Learning Page"));
    }
}
