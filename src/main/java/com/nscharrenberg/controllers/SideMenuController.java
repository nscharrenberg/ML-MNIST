package com.nscharrenberg.controllers;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "/fxml/SideMenu.fxml", title = "Drawing Recognition")
public class SideMenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    @ActionTrigger("canvas")
    private Label canvas;

    @FXML
    @ActionTrigger("trainAndTest")
    private Label trainAndTest;

    @FXML
    @ActionTrigger("machineLearning")
    private Label machineLearning;

    @FXML
    private JFXListView<Label> sideList;

    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();

        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            new Thread(()->{
                Platform.runLater(()->{
                    if (newVal != null) {
                        try {
                            contentFlowHandler.handle(newVal.getId());
                        } catch (VetoException | FlowException exc) {
                            exc.printStackTrace();
                        }
                    }
                });
            }).start();
        });

        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        bindNodeToController(canvas, CanvasController.class, contentFlow, contentFlowHandler);
        bindNodeToController(trainAndTest, CanvasController.class, contentFlow, contentFlowHandler);
        bindNodeToController(machineLearning, CanvasController.class, contentFlow, contentFlowHandler);
    }
}
