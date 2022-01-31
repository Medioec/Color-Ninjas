package com.forgottenheroes.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends DisplayScreen {

	private GameScreen gameScreen;

	public MainMenuScreen(final FHeroes game, OrthographicCamera camera, Viewport viewport) {
		super(game, viewport, camera);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

        getViewport().apply();
		getOrthographicCamera().update();
		game.getSpriteBatch().setProjectionMatrix(getOrthographicCamera().combined);
		game.getSpriteBatch().begin();
		game.getBitmapFont().getData().setScale(3);
		game.getBitmapFont().draw(game.getSpriteBatch(), "Menu Placeholder", 100, 600);
		game.getSpriteBatch().end();

		if (Gdx.input.isTouched()) {
			
            gameScreen = new GameScreen(game, getViewport(), getOrthographicCamera());
            game.setScreen(gameScreen);
			game.setGameState(GameState.GAMERUNNING);
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
        getViewport().update(width, height);
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