package com.values.game;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
    public static final int STUN = 0;
    public static final int WALK = 1;


    public int state = WALK;
}
