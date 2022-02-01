package com.forgottenheroes.main;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenheroes.main.objects.Leaderboard;
import com.forgottenheroes.main.objects.Map;
import com.forgottenheroes.main.objects.Scoreboard;

public class GameScreen extends DisplayScreen {

	public GameScreen(final FHeroes game, Viewport viewport, OrthographicCamera camera) {
		super(game, viewport, camera);
		//game.setShapeRenderer(new ShapeRenderer());
		//game.getShapeRenderer().setAutoShapeType(true);
		FHeroes.getObjectManager().setGameScreen(this);
		FHeroes.getObjectManager().setMap(new Map());
		FHeroes.getObjectManager().setLeaderboard(new Leaderboard());
		FHeroes.getObjectManager().setScoreboard(new Scoreboard());
		FHeroes.getObjectManager().getMap().generateMap1();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.5f, 0);
		getViewport().apply();
		// tell the camera to update its matrices.
		getOrthographicCamera().update();
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.getSpriteBatch().setProjectionMatrix(getOrthographicCamera().combined);
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		game.getShapeRenderer().setProjectionMatrix(getOrthographicCamera().combined);
		game.getShapeRenderer().begin();
		game.getShapeRenderer().set(ShapeType.Filled);
		game.getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		game.getShapeRenderer().rect(0, 0, FHeroes.INIT_WIDTH, FHeroes.INIT_HEIGHT);
		game.getShapeRenderer().end();
		FHeroes.getObjectManager().render(game);
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