package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.HashMap;

public class MeleeSystem extends IteratingSystem {
    private final HashMap<Entity, Sprite> originals;

    public MeleeSystem() {
        super(Family.all(PositionComponent.class, MeleeComponent.class, MoveComponent.class,
                        FacingComponent.class, SpriteComponent.class, BodyComponent.class)
                .get());
        originals = new HashMap<>();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        MeleeComponent melee = Mappers.melee.get(entity);
        MoveComponent movement = Mappers.move.get(entity);
        SpriteComponent sprite = Mappers.sprite.get(entity);

        if (melee.attack && melee.cancelable) {
            /* Prevent further melee inputs until cancelable. */
            melee.attack = false;
            melee.attacking = true;
            melee.cancelable = false;

            movement.stop = true;   // Stop movement

            melee.deltaTime = 0;    // Start timer

            originals.put(entity, new Sprite(sprite.sprite));
            sprite.sprite = melee.sprite;
        }

        if (melee.attacking) {
            PositionComponent position = Mappers.position.get(entity);
            FacingComponent face = Mappers.face.get(entity);
            Body body = Mappers.body.get(entity).body;

            System.out.println(face.facing);

            /* Dash is variable length due to ending once deltaTime >= attackLength.
             * Need to travel a specified distance over a length of time. */
            switch (face.facing) {
                case FacingComponent.UP:
                    body.applyLinearImpulse(0f, 125f, 0f, 0f, true);
                    position.y = body.getPosition().y;
                    break;
                case FacingComponent.LEFT:
                    body.applyLinearImpulse(-125f, 0f, 0f, 0f, true);
                    position.x = body.getPosition().x;
                    break;
                case FacingComponent.DOWN:
                    body.applyLinearImpulse(0f, -125f, 0f, 0f, true);
                    position.y = body.getPosition().y;
                    break;
                case FacingComponent.RIGHT:
                    body.applyLinearImpulse(125f, 0f, 0f, 0f, true);
                    position.x = body.getPosition().x;
                    break;
            }

            melee.deltaTime += deltaTime;
            System.out.println(melee.deltaTime);

            /* Resume taking input if finished */
            if (melee.deltaTime >= melee.attackLength) {
                melee.attacking = false;
                melee.cancelable = true;
                movement.stop = false;
                sprite.sprite = originals.get(entity);
            }
        }
    }
}
