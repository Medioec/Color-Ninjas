package com.forgottenheroes.main.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.forgottenheroes.main.CNinjas;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.useGL30 = true;
		new LwjglApplication(new CNinjas(), config);
		Gdx.graphics.setWindowedMode(CNinjas.INIT_WIDTH, CNinjas.INIT_HEIGHT);
	}
}
