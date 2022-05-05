package com.values.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.List;

public class HitboxComponent implements Component {
    List<Body> hitboxes;

    public HitboxComponent(List<Body> hitboxes) {
        this.hitboxes = hitboxes;
    }
}
