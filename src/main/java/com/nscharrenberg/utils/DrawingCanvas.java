package com.nscharrenberg.utils;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import java.awt.event.MouseEvent;

public class DrawingCanvas extends AnchorPane {
    private Canvas canvas;
    private GraphicsContext gc;

    public DrawingCanvas() {
        Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
        this.setWidth(bounds.getWidth()  / 1.1);
        this.setHeight(bounds.getHeight()  / 1.1);
        canvas = new Canvas(this.getWidth(), this.getHeight());
        gc =canvas.getGraphicsContext2D();
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        canvas.setOnMousePressed(event -> {
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(event -> {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });

        this.getChildren().add(canvas);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
    }

}
