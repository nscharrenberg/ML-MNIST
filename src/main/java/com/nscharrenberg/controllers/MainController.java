package com.nscharrenberg.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToolbar;
import com.nscharrenberg.App;
import com.nscharrenberg.enums.AvailablePages;
import com.nscharrenberg.factory.IFactory;
import javafx.application.Platform;
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

    public void setView(Node node) {
        App.factory.getAppRepository().getBorderPane().setCenter(node);
    }

    private void borderPaneSizeListener() {
        App.factory.getAppRepository().getBorderPane().sceneProperty().addListener(((observableValue, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                newScene.windowProperty().addListener(((observableValue1, oldWindow, newWindow) -> {
                    if (oldWindow == null & newWindow != null) {
                        getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
                            App.factory.getAppRepository().getBorderPane().setPrefWidth(newVal.doubleValue());
                        });

                        getStage().heightProperty().addListener((obs, oldVal, newVal) -> {
                            App.factory.getAppRepository().getBorderPane().setPrefHeight(newVal.doubleValue());
                        });
                    }
                }));
            }
        }));
    }

    @FXML
    private void initialize() {
        App.factory.getAppRepository().setBorderPane(root);
        App.factory.getAppRepository().setTopMenu(new JFXToolbar());
        App.factory.getAppRepository().setSidebarMenuDrawer(new JFXDrawer());
        App.factory.getAppRepository().setSidebarMenuPane(new StackPane());
        App.factory.getAppRepository().setSidebarMenuItems(new JFXListView<>());
        loadLayout();
    }

    private void loadLayout() {
        borderPaneSizeListener();
        initTopMenu();
        initSideMenu();
    }

    private Stage getStage() {
        return (Stage) App.factory.getAppRepository().getBorderPane().getScene().getWindow();
    }

    private void initTopMenu() {
        App.factory.getAppRepository().getTopMenu().setLeftItems(new Label(App.factory.getAppRepository().getPageTitle()));

        this.root.setTop(App.factory.getAppRepository().getTopMenu());
    }

    private void initSideMenu() {

        for (AvailablePages page : AvailablePages.values()) {
            Label tempLbl = new Label(page.getName());
            tempLbl.setId(page.getId());

            App.factory.getAppRepository().getSidebarMenuItems().getItems().add(tempLbl);
        }

        App.factory.getAppRepository().getSidebarMenuItems().setOnMouseClicked(e -> {
            try {
                openNewView(App.factory.getAppRepository().getSidebarMenuItems().getSelectionModel().getSelectedItem().getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        App.factory.getAppRepository().getSidebarMenuPane().getChildren().add(App.factory.getAppRepository().getSidebarMenuItems());
        App.factory.getAppRepository().getSidebarMenuItems().setPadding(new Insets(20, 0, 0, 0));

        App.factory.getAppRepository().getSidebarMenuDrawer().setSidePane(App.factory.getAppRepository().getSidebarMenuPane());
        App.factory.getAppRepository().getSidebarMenuDrawer().setDefaultDrawerSize(200);
        App.factory.getAppRepository().getSidebarMenuDrawer().setOverLayVisible(false);
        App.factory.getAppRepository().getSidebarMenuDrawer().setResizableOnDrag(false);
        App.factory.getAppRepository().getSidebarMenuDrawer().open();

        App.factory.getAppRepository().getBorderPane().setLeft(App.factory.getAppRepository().getSidebarMenuDrawer());
    }

    private void openNewView(String selectedItem) throws IOException {
        if (selectedItem.equals(AvailablePages.TRAIN_AND_TEST.getId())) {
            App.factory.getAppRepository().setSelectedPage(AvailablePages.TRAIN_AND_TEST);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ui/TrainAndTest.fxml"));
            TrainAndTestController trainAndTestController = new TrainAndTestController();
            loader.setController(trainAndTestController);

            App.factory.getAppRepository().getBorderPane().setCenter(loader.load());
            App.factory.getAppRepository().getBorderPane().setRight(null);

        } else if (selectedItem.equals(AvailablePages.MACHINE_LEARNING.getId())) {
            App.factory.getAppRepository().setSelectedPage(AvailablePages.MACHINE_LEARNING);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ui/MachineLearning.fxml"));
            MachineLearningController machineLearningController = new MachineLearningController();
            loader.setController(machineLearningController);
            App.factory.getAppRepository().getBorderPane().setCenter(loader.load());

            App.factory.getAppRepository().getBorderPane().setRight(null);
        } else {
            App.factory.getAppRepository().setSelectedPage(AvailablePages.CANVAS);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/ui/Canvas.fxml"));
            CanvasController canvasController = new CanvasController();
            loader.setController(canvasController);

            App.factory.getAppRepository().getBorderPane().setCenter(loader.load());
        }

        App.factory.getAppRepository().getTopMenu().setLeftItems(new Label(App.factory.getAppRepository().getPageTitle()));
    }
}
