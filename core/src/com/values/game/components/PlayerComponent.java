package com.values.game.components;

import com.badlogic.ashley.core.Component;

/* Priority Component to help the system determine
 * which direction to move in when multiple buttons
 * are held down.
 *
 * int direction: Designates the current direction being moved towards.
 * Uses numpad annotation similar to fighting games.
 *
 * 7 8 9
 * 4 5 6
 * 1 2 3
 *
 * Each number represents a direction on an 8-way gate, with 5 being neutral/center. */
public class PlayerComponent implements Component {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    public PlayerComponent() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
}
