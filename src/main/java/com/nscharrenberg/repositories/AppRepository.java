package com.nscharrenberg.repositories;

import com.jfoenix.controls.*;
import com.nscharrenberg.enums.AvailablePages;
import com.nscharrenberg.interfaces.IAppRepository;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppRepository implements IAppRepository {
    public final static String APP_TITLE = "Drawing Recognition";
    public final static double DRAWER_MENU_WIDTH = 300;

    private AvailablePages selectedPage = AvailablePages.CANVAS;
    private String pageTitle = getFullPageTitle(AvailablePages.CANVAS.getName());

    private JFXToolbar topMenu;
    private JFXDrawer sidebarMenuDrawer;
    private StackPane sidebarMenuPane;
    private JFXListView<Label> sidebarMenuItems;
    private BorderPane borderPane;
    private Scene scene;
    private Stage stage;
    private JFXSnackbar snackbar;

    @Override
    public AvailablePages getSelectedPage() {
        return selectedPage;
    }

    @Override
    public void setSelectedPage(AvailablePages selectedPage) {
        this.selectedPage = selectedPage;

        setPageTitle(selectedPage.getName());
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }

    @Override
    public void setPageTitle(String pageTitle) {
        this.pageTitle = getFullPageTitle(pageTitle);
    }

    private String getFullPageTitle(String pageTitle) {
        return String.format("%s - %s", APP_TITLE, pageTitle);
    }

    @Override
    public JFXToolbar getTopMenu() {
        return topMenu;
    }

    @Override
    public void setTopMenu(JFXToolbar topMenu) {
        this.topMenu = topMenu;
    }

    @Override
    public JFXDrawer getSidebarMenuDrawer() {
        return sidebarMenuDrawer;
    }

    @Override
    public void setSidebarMenuDrawer(JFXDrawer sidebarMenuDrawer) {
        this.sidebarMenuDrawer = sidebarMenuDrawer;
    }

    @Override
    public BorderPane getBorderPane() {
        return borderPane;
    }

    @Override
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
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
    public JFXListView<Label> getSidebarMenuItems() {
        return sidebarMenuItems;
    }

    @Override
    public void setSidebarMenuItems(JFXListView<Label> sidebarMenuItems) {
        this.sidebarMenuItems = sidebarMenuItems;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public JFXSnackbar getSnackbar() {
        return snackbar;
    }

    @Override
    public void setSnackbar(JFXSnackbar snackbar) {
        this.snackbar = snackbar;
    }

    @Override
    public void enqueueNotification(String message) {
        this.snackbar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message)));
    }
}
