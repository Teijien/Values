package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Set;

/* Code used from https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/ */
public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        // Only worries about player collisions
        super(Family.all(CollisionComponent.class, BodyComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent cc = Mappers.collision.get(entity);

        Set<Entity> collidedEntity = cc.entity; // Store the collided entities in the entity's collision list

        for (Entity e : collidedEntity) {
            if (Mappers.melee.has(e) && Mappers.face.has(e)) {
                Body body = Mappers.body.get(entity).body;
                int face = Mappers.face.get(e).facing;

                if (face == FacingComponent.UP) {
                    body.applyLinearImpulse(0f, 125f, 0f, 0f, true);
                } else if (face == FacingComponent.LEFT) {
                    body.applyLinearImpulse(-125f, 0f, 0f, 0f, true);
                } else if (face == FacingComponent.DOWN) {
                    body.applyLinearImpulse(0f, -125f, 0f, 0f, true);
                } else if (face == FacingComponent.RIGHT) {
                    body.applyLinearImpulse(125f, 0f, 0f, 0f, true);
                }

                StateComponent state = Mappers.state.get(entity);
                state.state = StateComponent.STUN;
            }
        }

        collidedEntity.clear();
    }
}
