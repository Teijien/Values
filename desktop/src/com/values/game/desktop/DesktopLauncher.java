package com.values.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.values.game.ValuesGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Values");
		config.setWindowedMode(720, 480);
		config.useVsync(false);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new ValuesGame(), config);
	}
}
