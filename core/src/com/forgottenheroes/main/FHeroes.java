package com.forgottenheroes.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenheroes.main.objects.ObjectManager;

public class FHeroes extends Game {

	private GameState gameState;

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private Viewport viewport;
	private OrthographicCamera camera;

	private static ObjectManager objectManager;

	public static final int INIT_HEIGHT = 720;
	public static final int INIT_WIDTH = INIT_HEIGHT / 9 * 16;

	//sets up the game window and display main menu
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, INIT_WIDTH, INIT_HEIGHT);
		viewport = new FitViewport(INIT_WIDTH, INIT_HEIGHT);
		objectManager = new ObjectManager(this);
		objectManager.setMainMenuScreen(new MainMenuScreen(this, camera, viewport));
		objectManager.setChatbotScreen(new ChatbotScreen(this, viewport, camera));
		//objectManager.getMainMenuScreen().resize(INIT_WIDTH, INIT_HEIGHT);
		//setScreen(objectManager.getMainMenuScreen());
		setScreen(objectManager.getChatbotScreen());
		//setGameState(GameState.MAINMENU);
		setGameState(GameState.CHATBOT);
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

	public boolean isGameState(GameState state){
		if(getGameState() == state){
			return true;
		} else return false;
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