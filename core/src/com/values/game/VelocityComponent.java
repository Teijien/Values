package com.values.game;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public double x;
    public double y;

    public VelocityComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
