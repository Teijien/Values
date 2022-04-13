package com.values.game;

import com.badlogic.ashley.core.Component;

public class MoveComponent implements Component {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean stop;

    public MoveComponent() {
        up = false;
        down = false;
        left = false;
        right = false;
        stop = false;
    }
}
