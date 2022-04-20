package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

public class EnemySystem extends IteratingSystem {
    private final Entity player;

    public EnemySystem(Entity player) {
        super(Family.all(PositionComponent.class, BodyComponent.class, EnemyComponent.class,
                StateComponent.class).get());
        this.player = player;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        StateComponent state = Mappers.state.get(entity);
        if (state.state == StateComponent.STUN) {
            return;
        }

        Body body = Mappers.body.get(entity).body;
        PositionComponent playerPos = Mappers.position.get(player);
        PositionComponent enemyPos = Mappers.position.get(entity);

        if (enemyPos.x < playerPos.x - 0.5f) {
            body.setLinearVelocity(50, 0);

            if (body.getPosition().y < playerPos.y - 0.5f) {
                body.setLinearVelocity(35.35f, 35.35f);
            } else if (body.getPosition().y > playerPos.y + 0.5f) {
                body.setLinearVelocity(35.35f, -35.35f);
            }
        } else if (body.getPosition().x > playerPos.x + 0.5f) {
            body.setLinearVelocity(-50, 0);

            if (body.getPosition().y < playerPos.y - 0.5f) {
                body.setLinearVelocity(-35.35f, 35.35f);
            } else if (body.getPosition().y > playerPos.y + 0.5f) {
                body.setLinearVelocity(-35.35f, -35.35f);
            }
        } else if (body.getPosition().y < playerPos.y - 0.5f) {
            body.setLinearVelocity(0, 50);
        } else if (body.getPosition().y > playerPos.y + 0.5f) {
            body.setLinearVelocity(0, -50);
        }

        enemyPos.x = body.getPosition().x;
        enemyPos.y = body.getPosition().y;
    }
}
