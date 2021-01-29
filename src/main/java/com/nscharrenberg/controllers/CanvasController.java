package com.nscharrenberg.controllers;

import com.nscharrenberg.utils.DrawingCanvas;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.annotation.PostConstruct;

public class CanvasController {
    @FXML
    private AnchorPane root;

    private Canvas canvas;

    private GraphicsContext gc;

    private double lastKnownWidth;
    private double lastKnownHeight;

    @FXML
    public void initialize() {
        draw();
    }

    private void draw() {
        canvas = new Canvas(500, 500);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lastKnownWidth = root.getWidth();
                lastKnownHeight = root.getHeight();
                canvas.setWidth(root.getWidth());
                canvas.setHeight(root.getHeight());
            }
        });

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

        root.getChildren().add(canvas);
    }
}
