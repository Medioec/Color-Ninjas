package com.forgottenheroes.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {

	final FHeroes game;
	private GameScreen gameScreen;
	private Viewport viewport;
	private OrthographicCamera camera;

	public MainMenuScreen(final FHeroes game, OrthographicCamera camera, Viewport viewport) {
		this.game = game;
		this.camera = camera;
		this.viewport = viewport;
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

        viewport.apply();
		camera.update();
		game.getSpriteBatch().setProjectionMatrix(camera.combined);
		game.getSpriteBatch().begin();
		game.getBitmapFont().getData().setScale(3);
		game.getBitmapFont().draw(game.getSpriteBatch(), "Menu Placeholder", 100, 600);
		game.getSpriteBatch().end();

		if (Gdx.input.isTouched()) {
			
            gameScreen = new GameScreen(game, camera, viewport);
            game.setScreen(gameScreen);
			game.setGameState(GameState.GAMERUNNING);
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}