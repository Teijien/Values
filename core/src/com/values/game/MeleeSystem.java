package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public class MeleeSystem extends IteratingSystem {
    private final HashMap<Entity, Sprite> originals;

    public MeleeSystem() {
        super(Family.all(PositionComponent.class, MeleeComponent.class, MoveComponent.class,
                        FacingComponent.class, SpriteComponent.class)
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

            System.out.println(face.facing);

            /* Dash is variable length due to ending once deltaTime >= attackLength.
             * Need to travel a specified distance over a length of time. */
            switch (face.facing) {
                case FacingComponent.UP:
                    position.y += 125 * deltaTime;
                    break;
                case FacingComponent.LEFT:
                    position.x -= 125 * deltaTime;
                    break;
                case FacingComponent.DOWN:
                    position.y -= 125 * deltaTime;
                    break;
                case FacingComponent.RIGHT:
                    position.x += 125 * deltaTime;
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
