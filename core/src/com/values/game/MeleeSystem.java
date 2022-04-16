package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import java.util.HashSet;

public class MeleeSystem extends IteratingSystem {
    private final HashSet<Entity> originals = new HashSet<>();  // Used to check how many entities the system applies to
    private int size = 0;

    public MeleeSystem() {
        super(Family.all(PositionComponent.class, MeleeComponent.class, MoveComponent.class,
                        FacingComponent.class, BodyComponent.class)
                .get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        // Check applicable entities
        if (!originals.contains(entity)) {
            originals.add(entity);
            size++;
            System.out.println(size);
        }

        MeleeComponent melee = Mappers.melee.get(entity);
        MoveComponent movement = Mappers.move.get(entity);
        //SpriteComponent sprite = Mappers.sprite.get(entity);

        if (melee.attack && melee.cancelable) {
            /* Prevent further melee inputs until cancelable. */
            melee.attack = false;
            melee.attacking = true;
            melee.cancelable = false;

            movement.stop = true;   // Stop movement from input

            melee.deltaTime = 0;    // Start timer

            System.out.println("Start attack");
        }

        if (melee.attacking) {
            PositionComponent position = Mappers.position.get(entity);
            FacingComponent face = Mappers.face.get(entity);
            Body body = Mappers.body.get(entity).body;

            System.out.println(face.facing);

            Filter filter = melee.hitbox.getFixtureList().get(0).getFilterData();
            filter.maskBits = 0x01;
            melee.hitbox.getFixtureList().get(0).setFilterData(filter);

            // Dash in the specified direction
            switch (face.facing) {
                case FacingComponent.UP:
                    body.applyLinearImpulse(0f, 125f, 0f, 0f, true);
                    melee.currSprite = melee.sprite;
                    melee.currSprite.setPosition(body.getPosition().x - 8, body.getPosition().y + 17 - 8);
                    melee.hitbox.setTransform(melee.currSprite.getX() + 8, melee.currSprite.getY() + 10, 0);
                    position.y = body.getPosition().y;
                    break;
                case FacingComponent.LEFT:
                    body.applyLinearImpulse(-125f, 0f, 0f, 0f, true);
                    melee.currSprite = melee.sprite;
                    melee.currSprite.setPosition(body.getPosition().x - 17 - 8, body.getPosition().y - 8);
                    melee.hitbox.setTransform(melee.currSprite.getX() + 6, melee.currSprite.getY() + 8, 0);
                    position.x = body.getPosition().x;
                    break;
                case FacingComponent.DOWN:
                    body.applyLinearImpulse(0f, -125f, 0f, 0f, true);
                    melee.currSprite = melee.sprite;
                    melee.currSprite.setPosition(body.getPosition().x - 8, body.getPosition().y - 17 - 8);
                    melee.hitbox.setTransform(melee.currSprite.getX() + 8, melee.currSprite.getY() + 6, 0);
                    position.y = body.getPosition().y;
                    break;
                case FacingComponent.RIGHT:
                    body.applyLinearImpulse(125f, 0f, 0f, 0f, true);
                    melee.currSprite = melee.sprite;
                    melee.currSprite.setPosition(body.getPosition().x + 17 - 8, body.getPosition().y - 8);
                    melee.hitbox.setTransform(melee.currSprite.getX() + 10, melee.currSprite.getY() + 8, 0);
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
                melee.currSprite = null;

                // Make hitbox inactive
                filter.maskBits = 0x00;
                melee.hitbox.getFixtureList().get(0).setFilterData(filter);
            }
        }
    }
}
