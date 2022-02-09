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
		CNinjas.setxOffsetOrigin((TILEDWIDTH - WORLDWIDTH) / 2);
		CNinjas.setyOffsetOrigin((TILEDHEIGHT - WORLDHEIGHT) / 2);
		changeViewportWorldSize(WORLDWIDTH, WORLDHEIGHT);
        setCameraPosition(TILEDWIDTH, TILEDHEIGHT);
		CNinjas.getObjectManager().setMap(new Map());
		CNinjas.getObjectManager().setLeaderboard(new Leaderboard());
		CNinjas.getObjectManager().setScoreboard(new Scoreboard());
		CNinjas.getObjectManager().getMap().generateMap1();
		CNinjas.getObjectManager().getMap().resetMap(1, Reset.NEWGAME);
	}

	@Override
	public void render(float delta) {
		preRenderPrep();
		CNinjas.getObjectManager().getMapRenderer().setView(CNinjas.getObjectManager().getCamera());
		//FHeroes.getObjectManager().getMapRenderer().render();
		CNinjas.getObjectManager().getShapeRenderer().begin();
		CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
		CNinjas.getObjectManager().getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		CNinjas.getObjectManager().getShapeRenderer().rect(CNinjas.getxOffsetOrigin(), CNinjas.getyOffsetOrigin(), WORLDWIDTH, WORLDHEIGHT);
		CNinjas.getObjectManager().getShapeRenderer().end();
		CNinjas.getObjectManager().getMapRenderer().render();
		CNinjas.getObjectManager().render(delta);
	}

	@Override
	public void resize(int width, int height) {
		CNinjas.getObjectManager().getViewport().update(width, height);
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
		CNinjas.getObjectManager().getMap().getMap().dispose();
	}

	@Override
	public void preRenderPrep() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		CNinjas.getObjectManager().getViewport().apply();
		CNinjas.getObjectManager().getCamera().update();
		CNinjas.getObjectManager().getSpriteBatch().setProjectionMatrix(CNinjas.getObjectManager().getCamera().combined);
		CNinjas.getObjectManager().getShapeRenderer().setProjectionMatrix(CNinjas.getObjectManager().getCamera().combined);
	}

    @Override
    public void changeViewportWorldSize(float width, float height) {
        CNinjas.getObjectManager().getViewport().setWorldSize(width, height);
    }

    @Override
    public void setCameraPosition(float width, float height) {
        CNinjas.getObjectManager().getCamera().setToOrtho(false, width, height);
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