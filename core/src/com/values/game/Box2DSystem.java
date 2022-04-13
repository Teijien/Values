package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/* Code used from https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/ */
public class Box2DSystem extends EntitySystem {
    private static final float MAX_TIME_STEP = 1/60f;
    private static float accumulator = 0f;
    private final World world;

    public Box2DSystem(World world) {
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        accumulator += deltaTime;

        if (accumulator < MAX_TIME_STEP) {
            return;
        }

        world.step(MAX_TIME_STEP, 8, 3);
        accumulator -= MAX_TIME_STEP;

        ImmutableArray<Entity> bodies = getEngine().getEntitiesFor(Family.all(BodyComponent.class).get());
        for (Entity entity : bodies) {
            PositionComponent entityPos = Mappers.position.get(entity);
            BodyComponent body = Mappers.body.get(entity);
            Vector2 position = body.body.getPosition();

            entityPos.x = position.x;
            entityPos.y = position.y;
        }
    }
}
