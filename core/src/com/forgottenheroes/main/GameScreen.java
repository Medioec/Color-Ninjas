package com.forgottenheroes.main;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.interfaces.ScreenInterface;
import com.forgottenheroes.main.objects.Leaderboard;
import com.forgottenheroes.main.objects.Map;
import com.forgottenheroes.main.objects.Scoreboard;

public class GameScreen implements Screen, ScreenInterface {

	private static final int WORLDHEIGHT = 720;
    private static final int WORLDWIDTH = WORLDHEIGHT/9*16;
	private static final int TILEDWIDTH = 27 * 16 * 4;
	private static final int TILEDHEIGHT = 13 * 16 * 4;

	public GameScreen() {
		FHeroes.setxOffsetOrigin((TILEDWIDTH - WORLDWIDTH) / 2);
		FHeroes.setyOffsetOrigin((TILEDHEIGHT - WORLDHEIGHT) / 2);
		changeViewportWorldSize(WORLDWIDTH, WORLDHEIGHT);
        setCameraPosition(TILEDWIDTH, TILEDHEIGHT);
		FHeroes.getObjectManager().setMap(new Map());
		FHeroes.getObjectManager().setLeaderboard(new Leaderboard());
		FHeroes.getObjectManager().setScoreboard(new Scoreboard());
		FHeroes.getObjectManager().getMap().generateMap1();
		//FHeroes.getObjectManager().getMap().resetMap(1, Reset.NEWGAME);
	}

	@Override
	public void render(float delta) {
		preRenderPrep();
		FHeroes.getObjectManager().getShapeRenderer().begin();
		FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
		FHeroes.getObjectManager().getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		FHeroes.getObjectManager().getShapeRenderer().rect(FHeroes.getxOffsetOrigin(), FHeroes.getyOffsetOrigin(), WORLDWIDTH, WORLDHEIGHT);
		FHeroes.getObjectManager().getShapeRenderer().end();
		FHeroes.getObjectManager().render(delta);
	}

	@Override
	public void resize(int width, int height) {
		FHeroes.getObjectManager().getViewport().update(width, height);
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

	@Override
	public void preRenderPrep() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		FHeroes.getObjectManager().getViewport().apply();
		FHeroes.getObjectManager().getCamera().update();
		FHeroes.getObjectManager().getSpriteBatch().setProjectionMatrix(FHeroes.getObjectManager().getCamera().combined);
		FHeroes.getObjectManager().getShapeRenderer().setProjectionMatrix(FHeroes.getObjectManager().getCamera().combined);
	}

    @Override
    public void changeViewportWorldSize(float width, float height) {
        FHeroes.getObjectManager().getViewport().setWorldSize(width, height);
    }

    @Override
    public void setCameraPosition(float width, float height) {
        FHeroes.getObjectManager().getCamera().setToOrtho(false, width, height);
    }

	public static int getWORLDHEIGHT() {
		return WORLDHEIGHT;
	}

	public static int getWORLDWIDTH() {
		return WORLDWIDTH;
	}

	public static int getTILEDHEIGHT() {
		return TILEDHEIGHT;
	}

	public static int getTILEDWIDTH() {
		return TILEDWIDTH;
	}
}