package com.forgottenheroes.main.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.forgottenheroes.main.FHeroes;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		new LwjglApplication(new FHeroes(), config);
		Gdx.graphics.setWindowedMode(FHeroes.INIT_WIDTH, FHeroes.INIT_HEIGHT);
	}
}
