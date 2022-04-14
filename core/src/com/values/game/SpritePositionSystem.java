package com.values.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpritePositionSystem extends IteratingSystem {
    public SpritePositionSystem(int priority) {
        super(Family.all(SpriteComponent.class, PositionComponent.class).get(), priority);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Sprite sprite = Mappers.sprite.get(entity).sprite;
        PositionComponent position = Mappers.position.get(entity);

        sprite.setX(position.x - 8);
        sprite.setY(position.y - 8);
    }
}
