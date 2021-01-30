package com.nscharrenberg.interfaces;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public interface ICanvasRepository {
    JFXDrawer getSidebarMenuDrawer();

    void setSidebarMenuDrawer(JFXDrawer sidebarMenuDrawer);

    StackPane getSidebarMenuPane();

    void setSidebarMenuPane(StackPane sidebarMenuPane);

    JFXListView<VBox> getSidebarMenuItems();

    void setSidebarMenuItems(JFXListView<VBox> sidebarMenuItems);

    Canvas getCanvas();

    void setCanvas(Canvas canvas);

    GraphicsContext getGc();

    void setGc(GraphicsContext gc);

    HBox getPaneHBox();

    void setPaneHBox(HBox paneHBox);

    AnchorPane getRoot();

    void setRoot(AnchorPane root);
}
