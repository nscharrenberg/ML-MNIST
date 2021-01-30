package com.nscharrenberg.repositories;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.nscharrenberg.interfaces.ICanvasRepository;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class CanvasRepository implements ICanvasRepository {
    public static final double DRAWING_MENU_WIDTH = 150;

    private AnchorPane root;
    private HBox paneHBox;
    private JFXDrawer sidebarMenuDrawer;
    private StackPane sidebarMenuPane;
    private JFXListView<VBox> sidebarMenuItems;

    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public JFXDrawer getSidebarMenuDrawer() {
        return sidebarMenuDrawer;
    }

    @Override
    public void setSidebarMenuDrawer(JFXDrawer sidebarMenuDrawer) {
        this.sidebarMenuDrawer = sidebarMenuDrawer;
    }

    @Override
    public StackPane getSidebarMenuPane() {
        return sidebarMenuPane;
    }

    @Override
    public void setSidebarMenuPane(StackPane sidebarMenuPane) {
        this.sidebarMenuPane = sidebarMenuPane;
    }

    @Override
    public JFXListView<VBox> getSidebarMenuItems() {
        return sidebarMenuItems;
    }

    @Override
    public void setSidebarMenuItems(JFXListView<VBox> sidebarMenuItems) {
        this.sidebarMenuItems = sidebarMenuItems;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public GraphicsContext getGc() {
        return gc;
    }

    @Override
    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public HBox getPaneHBox() {
        return paneHBox;
    }

    @Override
    public void setPaneHBox(HBox paneHBox) {
        this.paneHBox = paneHBox;
    }

    @Override
    public AnchorPane getRoot() {
        return root;
    }

    @Override
    public void setRoot(AnchorPane root) {
        this.root = root;
    }
}
