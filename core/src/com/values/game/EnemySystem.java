package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

public class EnemySystem extends IteratingSystem {
    private final Entity player;

    public EnemySystem(Entity player) {
        super(Family.all(PositionComponent.class, BodyComponent.class, EnemyComponent.class,
                StateComponent.class, FacingComponent.class).get());
        this.player = player;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        StateComponent state = Mappers.state.get(entity);
        if (state.state == StateComponent.STUN) {
            return;
        }

        // Logic for collision with hitbox, which should overlap the body
        /*
        CollisionComponent collision = Mappers.collision.get(entity);
        for (Entity e : collision.entity) {
            if (Mappers.body.has(e)) {
                Body body = Mappers.body.get(e).body;
                if (face.facing == FacingComponent.UP) {
                    body.applyLinearImpulse(0, 0, 125, 0, true);
                    System.out.println("Hit");
                } else if (face.facing == FacingComponent.RIGHT) {
                    body.applyLinearImpulse(125, 0, 0, 0, true);
                }
            }
            collision.entity.remove(e);
        }
        */
        FacingComponent face = Mappers.face.get(entity);

        Body body = Mappers.body.get(entity).body;
        PositionComponent playerPos = Mappers.position.get(player);
        PositionComponent enemyPos = Mappers.position.get(entity);

        if (enemyPos.x < playerPos.x - 0.5f) {
            body.setLinearVelocity(50, 0);
            face.facing = FacingComponent.RIGHT;

            if (body.getPosition().y < playerPos.y - 0.5f) {
                body.setLinearVelocity(35.35f, 35.35f);
                face.facing = FacingComponent.UP;
            } else if (body.getPosition().y > playerPos.y + 0.5f) {
                body.setLinearVelocity(35.35f, -35.35f);
                face.facing = FacingComponent.LEFT;
            }
        } else if (body.getPosition().x > playerPos.x + 0.5f) {
            body.setLinearVelocity(-50, 0);
            face.facing = FacingComponent.LEFT;

            if (body.getPosition().y < playerPos.y - 0.5f) {
                body.setLinearVelocity(-35.35f, 35.35f);
                face.facing = FacingComponent.UP;
            } else if (body.getPosition().y > playerPos.y + 0.5f) {
                body.setLinearVelocity(-35.35f, -35.35f);
                face.facing = FacingComponent.DOWN;
            }
        } else if (body.getPosition().y < playerPos.y - 0.5f) {
            body.setLinearVelocity(0, 50);
            face.facing = FacingComponent.UP;
        } else if (body.getPosition().y > playerPos.y + 0.5f) {
            body.setLinearVelocity(0, -50);
            face.facing = FacingComponent.DOWN;
        }

        enemyPos.x = body.getPosition().x;
        enemyPos.y = body.getPosition().y;
    }
}
