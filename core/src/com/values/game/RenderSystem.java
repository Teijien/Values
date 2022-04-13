package com.values.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private final Batch batch;
    private final Viewport view;

    public RenderSystem(Batch batch, Viewport view, int priority) {
        super(priority);
        this.batch = batch;
        this.view = view;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        batch.setProjectionMatrix(view.getCamera().combined);
        batch.begin();
        for (Entity e: entities) {
            Mappers.sprite.get(e).sprite.draw(batch);
        }
        batch.end();
    }
}
