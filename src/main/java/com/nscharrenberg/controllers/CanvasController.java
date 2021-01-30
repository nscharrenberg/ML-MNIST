package com.nscharrenberg.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.nscharrenberg.utils.DrawingCanvas;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class CanvasController {
    @FXML
    private AnchorPane root;

    private HBox centerBox;

    private Canvas canvas;

    private GraphicsContext gc;

    private double lastKnownWidth;
    private double lastKnownHeight;

    private JFXDrawer drawer;

    @FXML
    public void initialize() {
        centerBox = new HBox();
        this.root.getChildren().add(centerBox);
        draw();


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initToolsMenu();
                lastKnownWidth = root.getWidth()-300;
                lastKnownHeight = root.getHeight();
                canvas.setWidth(root.getWidth()-300);
                canvas.setHeight(root.getHeight());
                drawer.setPrefHeight(root.getHeight());
            }
        });
    }

    private void initToolsMenu() {
        drawer = new JFXDrawer();
        StackPane drawerMenuPane = new StackPane();

        JFXListView<Label> drawerListView = new JFXListView<>();

        Label pencilLbl = new Label("Pencil");
        pencilLbl.setId("tool_pencil");

        Label clearLbl = new Label("Clear Canvas");
        clearLbl.setId("tool_clear");

        Label saveBtn = new Label("Save");
        saveBtn.setId("tool_save");

        Label predictBtn = new Label("Predict");
        predictBtn.setId("tool_predict");

        drawerListView.getItems().addAll(pencilLbl, clearLbl, saveBtn, predictBtn);

        drawerMenuPane.getChildren().add(drawerListView);

        drawerListView.setOnMouseClicked(e -> {
            System.out.println("CLicked");
        });

        drawer.setSidePane(drawerMenuPane);
        drawer.setDefaultDrawerSize(150);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(false);
        drawer.open();

        ((BorderPane) root.getParent()).setRight(drawer);
    }

    private void draw() {
        canvas = new Canvas(500, 500);

        root.widthProperty().addListener(((observableValue, number, t1) -> {
            if (t1.doubleValue() != lastKnownWidth) {
                canvas.setWidth(t1.doubleValue());
            }
        }));

        root.heightProperty().addListener(((observableValue, number, t1) -> {
            if (t1.doubleValue() != lastKnownHeight) {
                canvas.setHeight(t1.doubleValue());
            }
        }));

        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();

        gc.setLineWidth(3);

        canvas.setOnMousePressed(event -> {
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(event -> {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });

        centerBox.getChildren().add(canvas);
    }
}
