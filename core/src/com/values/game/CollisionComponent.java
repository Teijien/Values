package com.values.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.Set;

/* Consider changing to a HashSet to track multiple collisions */
public class CollisionComponent implements Component {
    public Set<Entity> entity;

    public CollisionComponent(Set<Entity> entity) {
        this.entity = entity;
    }
}
