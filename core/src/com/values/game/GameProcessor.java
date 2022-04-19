package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameProcessor extends InputAdapter {
    private final Entity player;

    public GameProcessor(Entity player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        return toggleDirection(keycode, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean pass = toggleDirection(keycode, false);

        FacingComponent face = player.getComponent(FacingComponent.class);
        PlayerComponent p = player.getComponent(PlayerComponent.class);
        if (p.up && !(p.left || p.right)) {
            face.facing = FacingComponent.UP;
        } else if (p.left && !(p.up || p.down)) {
            face.facing = FacingComponent.LEFT;
        } else if (p.down && !(p.left || p.right)) {
            face.facing = FacingComponent.DOWN;
        } else if (p.right && !(p.up || p.down)) {
            face.facing = FacingComponent.RIGHT;
        }

        return pass;
    }

    private boolean toggleDirection(int keycode, boolean state) {
        MoveComponent direction = player.getComponent(MoveComponent.class);
        PlayerComponent p = player.getComponent(PlayerComponent.class);
        FacingComponent face = player.getComponent(FacingComponent.class);

        /* Need to refactor false priority switches to remove if statements */
        switch (keycode) {
            case Input.Keys.UP:
                direction.up = state;
                if (state) {
                    p.up = true;
                    p.down = false;
                    face.facing = FacingComponent.UP;
                } else {
                    p.up = false;
                    if (direction.down) {
                        p.down = true;
                        face.facing = FacingComponent.DOWN;
                    }
                }
                break;
            case Input.Keys.LEFT:
                direction.left = state;
                if (state) {
                    p.left = true;
                    p.right = false;
                    face.facing = FacingComponent.LEFT;
                } else {
                    p.left = false;
                    if (direction.right) {
                        p.right = true;
                        face.facing = FacingComponent.RIGHT;
                    }
                }
                break;
            case Input.Keys.DOWN:
                direction.down = state;
                if (state) {
                    p.down = true;
                    p.up = false;
                    face.facing = FacingComponent.DOWN;
                } else {
                    p.down = false;
                    if (direction.up) {
                        p.up = true;
                        face.facing = FacingComponent.UP;
                    }
                }
                break;
            case Input.Keys.RIGHT:
                direction.right = state;
                if (state) {
                    p.right = true;
                    p.left = false;
                    face.facing = FacingComponent.RIGHT;
                } else {
                    p.right = false;
                    if (direction.left) {
                        p.left = true;
                        face.facing = FacingComponent.LEFT;
                    }
                }
                break;
            case Input.Keys.SPACE:
                MeleeComponent melee = player.getComponent(MeleeComponent.class);
                melee.attack = state;
                break;
            default:
                return false;
        }
        return true;
    }
}
