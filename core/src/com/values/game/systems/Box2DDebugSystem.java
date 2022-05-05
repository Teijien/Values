package com.values.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DDebugSystem extends EntitySystem {
    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final Camera camera;

    public Box2DDebugSystem(Box2DDebugRenderer debugRenderer, World world, Camera camera) {
        this.debugRenderer = debugRenderer;
        this.world = world;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        debugRenderer.render(world, camera.combined);
    }
}
