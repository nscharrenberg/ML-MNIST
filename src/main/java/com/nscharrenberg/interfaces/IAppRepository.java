package com.nscharrenberg.interfaces;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToolbar;
import com.nscharrenberg.enums.AvailablePages;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public interface IAppRepository {
    AvailablePages getSelectedPage();

    void setSelectedPage(AvailablePages selectedPage);

    String getPageTitle();

    void setPageTitle(String pageTitle);

    JFXToolbar getTopMenu();

    void setTopMenu(JFXToolbar topMenu);

    JFXDrawer getSidebarMenuDrawer();

    void setSidebarMenuDrawer(JFXDrawer sidebarMenuDrawer);

    BorderPane getBorderPane();

    void setBorderPane(BorderPane borderPane);

    Scene getScene();

    void setScene(Scene scene);

    StackPane getSidebarMenuPane();

    void setSidebarMenuPane(StackPane sidebarMenuPane);

    JFXListView<Label> getSidebarMenuItems();

    void setSidebarMenuItems(JFXListView<Label> sidebarMenuItems);
}
