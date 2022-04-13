package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.Set;

/* Code used from https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/ */
public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        // Only worries about player collisions
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent cc = Mappers.collision.get(entity);

        Set<Entity> collidedEntity = cc.entity;
        if (!collidedEntity.isEmpty()) {
            collidedEntity.clear();
        }
    }
}
