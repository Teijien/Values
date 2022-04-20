package com.values.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashSet;

public class ActionScreen implements Screen {
    private final ValuesGame game;
    private Engine engine;

    // Setup logic is put into constructor, due to no create() method.
    public ActionScreen(final ValuesGame game, Engine engine) {
        this.game = game;
        this.engine = engine;

        World world = new World(new Vector2(), true);
        world.setContactListener(new B2DContactListener());

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("twewy-rindo.png")), 16, 16);

        // Player setup
        Entity player = new Entity();
        player.add(new PositionComponent(0, 0));
        player.add(new VelocityComponent(75, 75));
        player.add(new MoveComponent());
        player.add(new SpriteComponent(sprite));
        player.add(new PlayerComponent());
        player.add(new FacingComponent(2));

        // Setup player body
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(8f, 8f);

        Body playerBody = world.createBody(playerDef);
        player.add(new BodyComponent(playerBody));

        CircleShape circle = new CircleShape();
        circle.setRadius(8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = 0x01;
        fixtureDef.filter.maskBits = 0x02;

        playerBody.createFixture(fixtureDef);

        BodyDef hitbox = new BodyDef();
        hitbox.type = BodyDef.BodyType.KinematicBody;
        hitbox.allowSleep = false;

        Body playerHitbox = world.createBody(hitbox);
        playerHitbox.setUserData(player);

        // Attack definition
        FixtureDef hitboxDef = new FixtureDef();
        hitboxDef.shape = circle;
        hitboxDef.isSensor = true;
        hitboxDef.filter.categoryBits = 0x02;
        hitboxDef.filter.maskBits = 0x00;

        playerHitbox.createFixture(hitboxDef);

        player.add(new MeleeComponent(playerHitbox));

        // Enemy setup
        Entity enemy = new Entity();
        enemy.add(new PositionComponent(50, 50));
        enemy.add(new VelocityComponent(75, 75));
        enemy.add(new MoveComponent());
        enemy.add(new FacingComponent(2));
        enemy.add(new SpriteComponent(new Sprite(sprite)));
        enemy.add(new CollisionComponent(new HashSet<Entity>()));
        enemy.add(new EnemyComponent());

        BodyDef enemyDef = new BodyDef();
        enemyDef.type = BodyDef.BodyType.DynamicBody;
        enemyDef.position.set(58, 58);
        enemyDef.linearDamping = 8f;
        Body enemyBody = world.createBody(enemyDef);
        enemyBody.setUserData(enemy);
        enemyBody.createFixture(fixtureDef);
        enemy.add(new BodyComponent(enemyBody));

        // Add everything to the engine
        engine.addEntity(player);
        engine.addEntity(enemy);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new MeleeSystem());
        engine.addSystem(new SpritePositionSystem(1));
        engine.addSystem(new RenderSystem(new SpriteBatch(), game.getView(), 2));
        engine.addSystem(new Box2DSystem(world));
        engine.addSystem(new Box2DDebugSystem(new Box2DDebugRenderer(), world, game.getView().getCamera()));
        engine.addSystem(new CollisionSystem());

        Gdx.input.setInputProcessor(new GameProcessor(player));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.06f, 0.07f, 0.15f, 1);

        game.getView().getCamera().update();

        engine.update(delta);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        game.changeScreenSize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        engine = null;
    }
}
