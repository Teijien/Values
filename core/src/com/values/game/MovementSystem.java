package com.values.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(PositionComponent.class, VelocityComponent.class, MoveComponent.class,
                                PlayerComponent.class)
                        .get()
        );
    }

    public void update(float deltaTime) {
        for (Entity e : entities) {
            if (Mappers.move.get(e).stop) {
                break;
            }

            PositionComponent position = Mappers.position.get(e);
            VelocityComponent velocity = Mappers.velocity.get(e);
            PlayerComponent player = Mappers.player.get(e);

            double dx;
            double dy;

            dx = determineDir(player.left, player.right, velocity.x);
            dy = determineDir(player.down, player.up, velocity.y);

            if ((player.left || player.right) && (player.up || player.down)) {
                double vx = Math.sqrt(velocity.x * velocity.x / 2);
                double vy = Math.sqrt(velocity.y * velocity.y / 2);
                dx = determineDir(player.left, player.right, vx);
                dy = determineDir(player.down, player.up, vy);
            }

            position.x += dx * deltaTime;
            position.y += dy * deltaTime;
        }
    }

    /* Determine which direction to move in given flags given a negative and positive movement flag */
    private double determineDir(boolean neg, boolean pos, double velocity) {
        if (neg) {
            return -velocity;
        } else if (pos) {
            return velocity;
        }
        return 0;
    }
}
