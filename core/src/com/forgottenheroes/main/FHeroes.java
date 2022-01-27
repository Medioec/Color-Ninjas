package com.forgottenheroes.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenheroes.main.objects.Map;
import com.forgottenheroes.main.objects.ObjectManager;

public class FHeroes extends Game {

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private static Map map;
	private static ObjectManager objectManager;
	private GameState gameState;
	private Viewport viewport;
	private OrthographicCamera camera;
	private MainMenuScreen mainMenuScreen;
	public static final int INIT_HEIGHT = 720;
	public static final int INIT_WIDTH = INIT_HEIGHT / 9 * 16;

	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, INIT_WIDTH, INIT_HEIGHT);
		viewport = new FitViewport(INIT_WIDTH, INIT_HEIGHT);
		mainMenuScreen = new MainMenuScreen(this, camera, viewport);
		mainMenuScreen.resize(INIT_WIDTH, INIT_HEIGHT);
		this.setScreen(mainMenuScreen);
		setGameState(GameState.MAINMENU);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}




	//Getters and setters
	public SpriteBatch getSpriteBatch(){
		return batch;
	}

	public BitmapFont getBitmapFont(){
		return font;
	}

	public ShapeRenderer getShapeRenderer(){
		return shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer){
		this.shapeRenderer = shapeRenderer;
	}

	public static Map getMap(){
		return map;
	}

	public static void setMap(Map map){
		FHeroes.map = map;
	}

	public static ObjectManager getObjectManager(){
		return objectManager;
	}

	public static void setObjectManager(ObjectManager om){
		objectManager = om;
	}

	public GameState getGameState(){
		return gameState;
	}

	public void setGameState(GameState gameState){
		this.gameState = gameState;
	}

	public Viewport getViewport(){
		return viewport;
	}

	public void setViewPort(Viewport viewport){
		this.viewport = viewport;
	}

	public OrthographicCamera getOrthoGraphicCamera(){
		return camera;
	}

	public void setOrthographicCamera(OrthographicCamera camera){
		this.camera = camera;
	}
}