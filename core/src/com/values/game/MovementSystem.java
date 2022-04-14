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
            if (Mappers.move.get(e).stop) {
                break;
            }

            VelocityComponent velocity = Mappers.velocity.get(e);
            PositionComponent position = Mappers.position.get(e);
            PlayerComponent player = Mappers.player.get(e);
            Body body = Mappers.body.get(e).body;

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

            position.x = body.getPosition().x;
            position.y = body.getPosition().y;
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
