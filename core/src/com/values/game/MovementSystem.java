package com.values.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Body;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(MoveComponent.class, PlayerComponent.class, BodyComponent.class, VelocityComponent.class,
                                PositionComponent.class)
                        .get()
        );
    }

    public void update(float deltaTime) {
        for (Entity e : entities) {
            Body body = Mappers.body.get(e).body;

            if (Mappers.move.get(e).stop) {
                body.setLinearVelocity(0, 0);
                continue;
            }

            VelocityComponent velocity = Mappers.velocity.get(e);
            PositionComponent position = Mappers.position.get(e);
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

            body.setLinearVelocity((float) dx, (float) dy);

            // Keep player within bounds
            position.x = body.getPosition().x;
            if (position.x < 8) {
                body.setTransform(8, body.getPosition().y, 0);
                position.x = 8;
            } else if (position.x > 240 - 8) {
                body.setTransform(240 - 8, body.getPosition().y, 0);
                position.x = 240 - 8;
            }

            position.y = body.getPosition().y;
            if (position.y < 8) {
                body.setTransform(body.getPosition().x, 8, 0);
                position.y = 8;
            } else if (position.y > 160 - 8) {
                body.setTransform(body.getPosition().x, 160 - 8, 0);
                position.y = 160 - 8;
            }
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
