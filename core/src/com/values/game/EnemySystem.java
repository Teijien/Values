package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class EnemySystem extends IteratingSystem {
    private final Entity player;

    public EnemySystem(Entity player) {
        super(Family.all(PositionComponent.class, BodyComponent.class, VelocityComponent.class,
                EnemyComponent.class).get());
        this.player = player;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {

    }
}
