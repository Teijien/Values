package com.values.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ValuesGame extends Game {
	public static final float WORLD_WIDTH = 240;
	public static final float WORLD_HEIGHT = 160;

	public Viewport view;

	@Override
	public void create () {
		OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

		view = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		this.setScreen(new ActionScreen(this, new Engine()));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		changeScreenSize(width, height);
	}
	
	@Override
	public void dispose () {
		view = null;
	}

	public void changeScreenSize(int width, int height) {
		view.update(width, height);

		/* Camera needs to be reset due to the stage moving the camera.
		 * Also helps fix graphical glitches when resizing window on PC. */
		view.getCamera().position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		view.getCamera().update();
	}

	public Viewport getView() {
		return view;
	}
}
