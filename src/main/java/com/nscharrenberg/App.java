package com.nscharrenberg;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.nscharrenberg.controllers.CanvasController;
import com.nscharrenberg.controllers.IController;
import com.nscharrenberg.controllers.MainController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static JFXDecorator decorator;

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxml, IController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        loader.setController(controller);
        ((JFXDecorator) scene.getRoot()).setContent(loader.load());
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = 800;
        double height = 600;

        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() / 1.1;
            height = bounds.getHeight() / 1.1;
        } catch (Exception e) {}

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/Main.fxml"));
        MainController mainController = new MainController();
        loader.setController(mainController);
        JFXDecorator decorator = new JFXDecorator(stage, loader.load());
        decorator.setCustomMaximize(true);

        stage.setTitle("Drawing Recognition");


        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.add(JFoenixResources.load("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }


}