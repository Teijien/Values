package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Set;

/* Code used from https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/ */
public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, BodyComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Fetch the list of entities collided with
        CollisionComponent cc = Mappers.collision.get(entity);
        Set<Entity> collidedEntity = cc.entity;

        for (Entity e : collidedEntity) {   // Check each entity collided with
            if (Mappers.face.has(e)) {  // Check if the current collidedEntity has a FacingComponent
                Body body = Mappers.body.get(entity).body;
                int face = Mappers.face.get(e).facing;

                // Push the Entity in a direction based on where it's facing
                if (face == FacingComponent.UP) {
                    body.applyLinearImpulse(0f, 200f, 0f, 0f, true);
                } else if (face == FacingComponent.LEFT) {
                    body.applyLinearImpulse(-200f, 0f, 0f, 0f, true);
                } else if (face == FacingComponent.DOWN) {
                    body.applyLinearImpulse(0f, -200f, 0f, 0f, true);
                } else if (face == FacingComponent.RIGHT) {
                    body.applyLinearImpulse(200f, 0f, 0f, 0f, true);
                }

                StateComponent state = Mappers.state.get(entity);
                state.state = StateComponent.STUN;

                System.out.println("Pushed");
            }

            collidedEntity.remove(e);   // This prevents collision logic more than once
        }
    }
}
