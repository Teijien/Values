package com.values.game;

import com.badlogic.ashley.core.Component;

public class FacingComponent implements Component {
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    public int facing;

    public FacingComponent(int facing) {
        this.facing = facing;
    }
}
