package com.forgottenheroes.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forgottenheroes.main.objects.ObjectManager;

public class FHeroes extends Game {

	private static GameState gameState;
	private static FHeroes game;

	private static ObjectManager objectManager;

	public static final int INIT_HEIGHT = 720;
	public static final int INIT_WIDTH = INIT_HEIGHT / 9 * 16;

	//sets up the game window and display main menu
	public void create() {
		game = this;
		objectManager = new ObjectManager(this);

		objectManager.setSpriteBatch(new SpriteBatch());
		objectManager.setBitmapFont(new BitmapFont());
		objectManager.setShapeRenderer(new ShapeRenderer());
		objectManager.getShapeRenderer().setAutoShapeType(true);
		objectManager.setCamera(new OrthographicCamera());
		objectManager.getCamera().setToOrtho(false, INIT_WIDTH, INIT_HEIGHT);
		objectManager.setViewport(new FitViewport(INIT_WIDTH, INIT_HEIGHT, objectManager.getCamera()));
		
		objectManager.setMainMenuScreen(new MainMenuScreen());
		
		setScreen(objectManager.getMainMenuScreen());
		setGameState(GameState.MAINMENU);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
	}
	
	//Getters and setters
	public static ObjectManager getObjectManager(){
		return objectManager;
	}

	public static void setObjectManager(ObjectManager om){
		objectManager = om;
	}

	public static FHeroes getGame() {
		return game;
	}

	public static GameState getGameState(){
		return gameState;
	}

	public static void setGameState(GameState state){
		gameState = state;
	}

	public static boolean isGameState(GameState state){
		if(getGameState() == state){
			return true;
		} else return false;
	}
}