package com.nscharrenberg.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Cell<State> {
    private final int column;

    private final int row;

    private ObjectProperty<EventHandler<MouseEvent>> onClick = new SimpleObjectProperty<>();
    private ObjectProperty<EventHandler<MouseEvent>> onMouseOver = new SimpleObjectProperty<>();

    private ObjectProperty<State> state = new SimpleObjectProperty<>();

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public State getState() {
        return state.get();
    }

    public ReadOnlyObjectProperty<State> stateProperty() {
        return state;
    }

    public void changeState(State state) {
        this.state.set(state);
    }

    public EventHandler<MouseEvent> getOnMouseOver() {
        return onMouseOver.get();
    }

    public ObjectProperty<EventHandler<MouseEvent>> onMouseOverProperty() {
        return onMouseOver;
    }

    public void setOnMouseOver(EventHandler<MouseEvent> onMouseOver) {
        this.onMouseOver.set(onMouseOver);
    }

    public EventHandler<MouseEvent> getOnClick() {
        return onClick.get();
    }

    public ObjectProperty<EventHandler<MouseEvent>> onClickProperty() {
        return onClick;
    }

    public void setOnClick(EventHandler<MouseEvent> onClick) {
        this.onClick.set(onClick);
    }

    @Override
    public String toString() {
        return String.format("cell[\"%s\",\"%s\"]", this.column, this.row);
    }
}
