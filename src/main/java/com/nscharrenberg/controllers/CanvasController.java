package com.nscharrenberg.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.nscharrenberg.App;
import com.nscharrenberg.enums.DrawingOptions;
import com.nscharrenberg.repositories.AppRepository;
import com.nscharrenberg.utils.DrawingCanvas;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasController {
    @FXML
    private AnchorPane root;

    private double lastKnownWidth;
    private double lastKnownHeight;

    @FXML
    public void initialize() {
        App.factory.getCanvasRepository().setRoot(root);
        App.factory.getCanvasRepository().setPaneHBox(new HBox());
        this.root.getChildren().add(App.factory.getCanvasRepository().getPaneHBox());
        draw();


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initToolsMenu();
                lastKnownWidth = App.factory.getCanvasRepository().getRoot().getWidth() - AppRepository.DRAWER_MENU_WIDTH;
                lastKnownHeight = App.factory.getCanvasRepository().getRoot().getHeight();
                App.factory.getCanvasRepository().getCanvas().setWidth(App.factory.getCanvasRepository().getRoot().getWidth() - AppRepository.DRAWER_MENU_WIDTH);
                App.factory.getCanvasRepository().getCanvas().setHeight(App.factory.getCanvasRepository().getRoot().getHeight());
                App.factory.getCanvasRepository().getSidebarMenuDrawer().setPrefHeight(App.factory.getCanvasRepository().getRoot().getHeight());
            }
        });
    }

    private void initToolsMenu() {
        App.factory.getCanvasRepository().setSidebarMenuDrawer(new JFXDrawer());
        App.factory.getCanvasRepository().setSidebarMenuPane(new StackPane());

        App.factory.getCanvasRepository().setSidebarMenuItems(new JFXListView<>());

        for (DrawingOptions option : DrawingOptions.values()) {
            FontIcon icon = new FontIcon(option.getIcon());
            icon.setIconSize(35);

            VBox vbox = new VBox();
            vbox.getChildren().add(icon);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(25, 0, 25, 0));
            vbox.setId(option.getId());

            App.factory.getCanvasRepository().getSidebarMenuItems().getItems().add(vbox);
        }

        App.factory.getCanvasRepository().getSidebarMenuPane().getChildren().add(App.factory.getCanvasRepository().getSidebarMenuItems());

        App.factory.getCanvasRepository().getSidebarMenuDrawer().setSidePane(App.factory.getCanvasRepository().getSidebarMenuPane());
        App.factory.getCanvasRepository().getSidebarMenuDrawer().setDefaultDrawerSize(150);
        App.factory.getCanvasRepository().getSidebarMenuDrawer().setDirection(JFXDrawer.DrawerDirection.RIGHT);
        App.factory.getCanvasRepository().getSidebarMenuDrawer().setOverLayVisible(false);
        App.factory.getCanvasRepository().getSidebarMenuDrawer().setResizableOnDrag(false);
        App.factory.getCanvasRepository().getSidebarMenuDrawer().open();

        if (App.factory.getAppRepository().getBorderPane().getRight() == null) {
            App.factory.getAppRepository().getBorderPane().setRight(App.factory.getCanvasRepository().getSidebarMenuDrawer());
        }

        App.factory.getCanvasRepository().getSidebarMenuItems().setOnMouseClicked(e -> {
            String selected = App.factory.getCanvasRepository().getSidebarMenuItems().getSelectionModel().getSelectedItem().getId();

            if (selected.equals(DrawingOptions.ERASED.getId())) {
                App.factory.getCanvasRepository().setSelectedDrawingOption(DrawingOptions.ERASED);

                clearCanvas();
            } else if (selected.equals(DrawingOptions.EXPORTER.getId())) {
                App.factory.getCanvasRepository().setSelectedDrawingOption(DrawingOptions.EXPORTER);

                saveImage();
            } else if (selected.equals(DrawingOptions.PREDICT.getId())) {
                App.factory.getCanvasRepository().setSelectedDrawingOption(DrawingOptions.PREDICT);
            } else {
                App.factory.getCanvasRepository().setSelectedDrawingOption(DrawingOptions.PENCIL);
            }
        });

        VBox defaultOption = App.factory.getCanvasRepository().getSidebarMenuItems().getItems().stream().filter(v -> {
            return v.getId().equals(DrawingOptions.PENCIL.getId());
        }).findFirst().orElse(null);

        App.factory.getCanvasRepository().getSidebarMenuItems().getSelectionModel().select(defaultOption);
    }

    private void draw() {
        App.factory.getCanvasRepository().setCanvas(new Canvas(500, 500));

        App.factory.getCanvasRepository().getRoot().widthProperty().addListener(((observableValue, number, t1) -> {
            if (t1.doubleValue() != lastKnownWidth) {
                App.factory.getCanvasRepository().getCanvas().setWidth(t1.doubleValue());
            }
        }));

        App.factory.getCanvasRepository().getRoot().heightProperty().addListener(((observableValue, number, t1) -> {
            if (t1.doubleValue() != lastKnownHeight) {
                App.factory.getCanvasRepository().getCanvas().setHeight(t1.doubleValue());
            }
        }));

        App.factory.getCanvasRepository().setGc(App.factory.getCanvasRepository().getCanvas().getGraphicsContext2D());

        App.factory.getCanvasRepository().getGc().setStroke(Color.BLACK);
        App.factory.getCanvasRepository().getGc().setLineWidth(5);

        App.factory.getCanvasRepository().getGc().fill();

        App.factory.getCanvasRepository().getGc().setLineWidth(3);

        App.factory.getCanvasRepository().getCanvas().setOnMousePressed(event -> {
            if (App.factory.getCanvasRepository().getSelectedDrawingOption() == null) {
                App.factory.getCanvasRepository().setSelectedDrawingOption(DrawingOptions.PENCIL);
            }

            if (App.factory.getCanvasRepository().getSelectedDrawingOption().equals(DrawingOptions.PENCIL)) {
                App.factory.getCanvasRepository().getGc().beginPath();
                App.factory.getCanvasRepository().getGc().moveTo(event.getX(), event.getY());
                App.factory.getCanvasRepository().getGc().stroke();
            }
        });

        App.factory.getCanvasRepository().getCanvas().setOnMouseDragged(event -> {
            if (App.factory.getCanvasRepository().getSelectedDrawingOption() == null) {
                App.factory.getCanvasRepository().setSelectedDrawingOption(DrawingOptions.PENCIL);
            }

            if (App.factory.getCanvasRepository().getSelectedDrawingOption().equals(DrawingOptions.PENCIL)) {
                App.factory.getCanvasRepository().getGc().lineTo(event.getX(), event.getY());
                App.factory.getCanvasRepository().getGc().stroke();
            }
        });

        App.factory.getCanvasRepository().getPaneHBox().getChildren().add(App.factory.getCanvasRepository().getCanvas());
    }

    private void clearCanvas() {
        App.factory.getCanvasRepository().getGc().clearRect(0, 0, App.factory.getCanvasRepository().getCanvas().getWidth(), App.factory.getCanvasRepository().getCanvas().getHeight());
    }

    private void saveImage() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(App.factory.getAppRepository().getStage());

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(((int) App.factory.getCanvasRepository().getCanvas().getWidth()), ((int) App.factory.getCanvasRepository().getCanvas().getHeight()));
                App.factory.getCanvasRepository().getCanvas().snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);

                JFXSnackbar bar = new JFXSnackbar(App.factory.getAppRepository().getBorderPane());
                App.factory.getAppRepository().enqueueNotification("Image has been saved.");
            } catch (IOException ex) {
                Logger.getLogger(CanvasController.class.getName()).log(Level.SEVERE, null, ex);

                App.factory.getAppRepository().enqueueNotification("Unable to save Image!");
            }
        }
    }
}
