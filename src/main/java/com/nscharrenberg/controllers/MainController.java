package com.nscharrenberg.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToolbar;
import com.nscharrenberg.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane root;

    private JFXDrawer drawer;

    private JFXToolbar toolbar;

    private StackPane drawerMenuPane;

    public void setView(Node node) {
        root.setCenter(node);
    }

    private void borderPaneSizeListener() {
        root.sceneProperty().addListener(((observableValue, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                newScene.windowProperty().addListener(((observableValue1, oldWindow, newWindow) -> {
                    if (oldWindow == null & newWindow != null) {
                        getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
                            root.setPrefWidth(newVal.doubleValue());
                        });

                        getStage().heightProperty().addListener((obs, oldVal, newVal) -> {
                            root.setPrefHeight(newVal.doubleValue());
                        });
                    }
                }));
            }
        }));
    }

    @FXML
    private void initialize() {
        loadLayout();
    }

    private void loadLayout() {
        borderPaneSizeListener();
        initTopMenu();
        initSideMenu();
    }

    private Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    private void initTopMenu() {
        toolbar = new JFXToolbar();
        toolbar.setLeftItems(new Label("Drawing Recognition"));

        this.root.setTop(toolbar);
    }

    private void initSideMenu() {
        drawer = new JFXDrawer();
        drawerMenuPane = new StackPane();

        JFXListView<Label> drawerListView = new JFXListView<>();

        Label canvasLbl = new Label("Canvas");
        canvasLbl.setId("canvas");

        Label trainAndTestLbl = new Label("Train and Test");
        trainAndTestLbl.setId("trainAndTest");

        Label machineLearningLbl = new Label("Machine Learning");
        machineLearningLbl.setId("machineLearning");

        drawerListView.getItems().addAll(canvasLbl, trainAndTestLbl, machineLearningLbl);

        drawerListView.setOnMouseClicked(e -> {
            try {
                openNewView(drawerListView.getSelectionModel().getSelectedItem().getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        drawerMenuPane.getChildren().add(drawerListView);

        drawer.setSidePane(drawerMenuPane);
        drawer.setDefaultDrawerSize(200);
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(false);
        drawer.open();

        this.root.setLeft(drawer);
    }

    private void openNewView(String selectedItem) throws IOException {
        if (selectedItem.equals("trainAndTest")) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ui/TrainAndTest.fxml"));
            TrainAndTestController trainAndTestController = new TrainAndTestController();
            loader.setController(trainAndTestController);

            this.root.setCenter(loader.load());
        } else if (selectedItem.equals("machineLearning")) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ui/MachineLearning.fxml"));
            MachineLearningController machineLearningController = new MachineLearningController();
            loader.setController(machineLearningController);
            this.root.setCenter(loader.load());
        } else {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ui/Canvas.fxml"));
            CanvasController canvasController = new CanvasController();
            loader.setController(canvasController);

            this.root.setCenter(loader.load());
        }
    }

    public JFXToolbar getToolbar() {
        return this.toolbar;
    }

    public JFXDrawer getDrawer() {
        return this.drawer;
    }

    public StackPane getDrawerMenuPane() {
        return this.drawerMenuPane;
    }
}
