package com.nscharrenberg.utils;

import processing.core.PApplet;

public class DrawingCanvas extends PApplet {
    public void setup() {
        size(500, 400);
        background(255, 255, 255);
    }

    public void draw() {
        ellipse(200, 200, 50, 50);
    }
}
