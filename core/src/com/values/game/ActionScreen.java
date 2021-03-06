package com.values.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.values.game.components.*;
import com.values.game.systems.*;

import java.util.HashSet;

public class ActionScreen implements Screen {
    public static final int PLAYER_BODY = 0b00000001;
    public static final int PLAYER_HITBOX = 0b00000010;
    public static final int ENEMY_BODY = 0b00000100;
    public static final int ENEMY_HITBOX = 0b00001000;

    private final ValuesGame game;
    private Engine engine;
    private final OrthogonalTiledMapRenderer renderer;

    // Setup logic is put into constructor, due to no create() method.
    public ActionScreen(final ValuesGame game, Engine engine) {
        this.game = game;
        this.engine = engine;

        TiledMap map = new TmxMapLoader().load("floor.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

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
        player.add(new StateComponent());
        player.add(new CollisionComponent(new HashSet<Entity>()));

        // Setup player body
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(8f, 8f);

        Body playerBody = world.createBody(playerDef);
        playerBody.setUserData(player);
        player.add(new BodyComponent(playerBody));

        CircleShape circle = new CircleShape();
        circle.setRadius(8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = PLAYER_BODY;
        fixtureDef.filter.maskBits = ENEMY_HITBOX;

        Fixture playerFix = playerBody.createFixture(fixtureDef);
        playerFix.setUserData(player);

        BodyDef hitbox = new BodyDef();
        hitbox.type = BodyDef.BodyType.KinematicBody;
        hitbox.allowSleep = false;

        // Attack definition
        Body playerHitbox = world.createBody(hitbox);
        playerHitbox.setUserData(player);

        FixtureDef hitboxDef = new FixtureDef();
        hitboxDef.shape = circle;
        hitboxDef.isSensor = true;
        hitboxDef.filter.categoryBits = PLAYER_HITBOX;
        hitboxDef.filter.maskBits = ENEMY_BODY;

        playerHitbox.createFixture(hitboxDef);

        player.add(new MeleeComponent(playerHitbox));

        for (int i = 0; i < 1; i++) {
            Entity enemy = createEnemy(new Sprite(sprite), world);
            Body body = Mappers.body.get(enemy).body;
            body.setTransform(50 * (i + 1), body.getPosition().y, 0);
            engine.addEntity(enemy);
        }

        // Add everything to the engine
        engine.addEntity(player);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new MeleeSystem());
        engine.addSystem(new SpritePositionSystem(1));
        engine.addSystem(new RenderSystem(new SpriteBatch(), game.getView(), 2));
        engine.addSystem(new Box2DSystem(world));
        engine.addSystem(new Box2DDebugSystem(new Box2DDebugRenderer(), world, game.getView().getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new EnemySystem(player));
        engine.addSystem(new StateSystem());

        Gdx.input.setInputProcessor(new GameProcessor(player));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.06f, 0.07f, 0.15f, 1);

        game.getView().getCamera().update();

        renderer.setView((OrthographicCamera) game.getView().getCamera());
        renderer.render();

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

    private Entity createEnemy(Sprite sprite, World world) {
        Entity enemy = new Entity();

        // Enemy components
        enemy.add(new PositionComponent(50, 50));
        enemy.add(new VelocityComponent(75, 75));
        enemy.add(new MoveComponent());
        enemy.add(new FacingComponent(2));
        enemy.add(new SpriteComponent(new Sprite(sprite)));
        enemy.add(new CollisionComponent(new HashSet<Entity>()));
        enemy.add(new EnemyComponent());
        enemy.add(new StateComponent());

        // Create the enemy body
        BodyDef enemyDef = new BodyDef();
        enemyDef.type = BodyDef.BodyType.DynamicBody;
        enemyDef.position.set(58, 58);
        enemyDef.linearDamping = 8f;
        Body enemyBody = world.createBody(enemyDef);
        enemyBody.setUserData(enemy);
        //enemyBody.createFixture(fixtureDef);

        // ShapeDef for fixtures
        CircleShape circle = new CircleShape();
        circle.setRadius(8);

        // Set enemy body collision filter
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = ENEMY_BODY;
        fixtureDef.filter.maskBits = PLAYER_HITBOX;
        enemyBody.createFixture(fixtureDef);

        // FixtureDef for enemy hitbox
        FixtureDef hitboxDef = new FixtureDef();
        hitboxDef.shape = circle;
        hitboxDef.isSensor = true;
        hitboxDef.filter.categoryBits = ENEMY_HITBOX;
        hitboxDef.filter.maskBits = PLAYER_BODY;

        Fixture f = enemyBody.createFixture(hitboxDef);
        f.setUserData(enemy);

        enemy.add(new BodyComponent(enemyBody));

        return enemy;
    }
}
