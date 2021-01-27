package com.nscharrenberg.controllers;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTooltip;
import com.nscharrenberg.datafx.ExtendedAnimatedFlowContainer;
import com.nscharrenberg.utils.DrawingCanvas;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

@ViewController(value = "/fxml/ui/Canvas.fxml", title = "Drawing Recognition")
public class CanvasController {
    
}
