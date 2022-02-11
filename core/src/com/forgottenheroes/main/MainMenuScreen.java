package com.forgottenheroes.main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.interfaces.ScreenInterface;
import com.forgottenheroes.main.objects.MultilineTextBox;
import com.forgottenheroes.main.objects.Player;
import com.forgottenheroes.main.objects.TextBox;
import com.forgottenheroes.main.objects.Player.PlayerColor;

public class MainMenuScreen implements Screen, ScreenInterface{

    private static final int MENUHEIGHT = 720;
    private final static int MENUWIDTH = MENUHEIGHT/9*16;
    private static final int TILEDWIDTH = 27 * 16 * 4;
	private static final int TILEDHEIGHT = 13 * 16 * 4;
    private final int RESPONSEDURATION = 3000;
    private String input;
    private String response;
    private long responseSetTime;
    private MultilineTextBox textbox;
    private TextBox inputBox;
    private TextBox responseBox;
    private TextBox player1Box;
    private TextBox player2Box;
    private boolean inputReady;
    private boolean startGame;

    private CurrentChat currentChat;
    private ArrayList<PlayerColor> colorsAvailable;
    private Player player1, player2;
    private Chatbot chatbot;

    public MainMenuScreen() {
        CNinjas.setxOffsetOrigin((TILEDWIDTH - MENUWIDTH)/2);
        CNinjas.setyOffsetOrigin((TILEDHEIGHT - MENUHEIGHT)/2);
        CNinjas.getObjectManager().setMainMenuScreen(this);
        changeViewportWorldSize(MENUWIDTH, MENUHEIGHT);
        setCameraPosition(TILEDWIDTH, TILEDHEIGHT);
        prepareBG();
        player1 = new Player(1);
        player2 = new Player(2);
        colorsAvailable = new ArrayList<>();
        colorsAvailable.add(PlayerColor.GREEN);
        colorsAvailable.add(PlayerColor.YELLOW);
        displayDefault();
        inputBox = new TextBox();
        inputBox.setRelativeY(MENUHEIGHT/16*1);
        inputBox.addTextLine("", 2, Color.WHITE);
        inputBox.setFlexWidth(true);

        responseBox = new TextBox();
        responseBox.setBgEnabled(false);
        responseBox.setRelativeY(MENUHEIGHT/16*3);
        responseBox.addTextLine("", 2, Color.RED);
        responseBox.setFlexWidth(true);

        player1Box = new TextBox();
        player1Box.setBgEnabled(false);
        player1Box.setFlexWidth(true);
        player1Box.setRelativeY(MENUHEIGHT/16*7);
        player1Box.setRelativeX((MENUWIDTH/16*2) - player1Box.getWidth()/2);
        player1Box.addTextLine(player1.getName(), 2, player1.getColor());
        CNinjas.getObjectManager().removeObject(player1Box);

        player2Box = new TextBox();
        player2Box.setBgEnabled(false);
        player2Box.setFlexWidth(true);
        player2Box.setRelativeY(MENUHEIGHT/16*7);
        player2Box.setRelativeX((MENUWIDTH/16*14) - player2Box.getWidth()/2);
        player2Box.addTextLine(player2.getName(), 2, player2.getColor());
        CNinjas.getObjectManager().removeObject(player2Box);

        
        input = "";
        response = "";
        inputReady = false;
        startGame = false;
        currentChat = CurrentChat.START;
        chatbot = new Chatbot();
        chatbot.start();
    }

    public enum CurrentChat{
        START,
        HELP
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        preRenderPrep();
        CNinjas.getObjectManager().getShapeRenderer().begin();
		CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
		CNinjas.getObjectManager().getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		CNinjas.getObjectManager().getShapeRenderer().rect(0, 0, MENUWIDTH, MENUHEIGHT);
		CNinjas.getObjectManager().getShapeRenderer().end();
        CNinjas.getObjectManager().getMapRenderer().render();
        CNinjas.getObjectManager().getBitmapFont().setColor(Color.WHITE);
        CNinjas.getObjectManager().render(delta);
        CNinjas.getObjectManager().getSpriteBatch().begin();
        CNinjas.getObjectManager().getSpriteBatch().draw(player1.getWalkdownArray().get(0), player1Box.getAbsoluteX() + player1Box.getWidth()/2 - 64/2, player1Box.getAbsoluteY() + MENUHEIGHT/16*2, 64, 64);
        CNinjas.getObjectManager().getSpriteBatch().draw(player2.getWalkdownArray().get(0), player2Box.getAbsoluteX() + player2Box.getWidth()/2 - 64/2, player2Box.getAbsoluteY() + MENUHEIGHT/16*2, 64, 64);
        CNinjas.getObjectManager().getSpriteBatch().end();
        player1Box.render(delta);
        player2Box.render(delta);
        checkTemporalResponse();
        if(inputReady){
            chatbot.getLock().lock();
            try{
                chatbot.getInputReady().signal();
            }
            finally {
                chatbot.getLock().unlock();
            }
            //chatbotProcess(input);
            //inputReady = false;
        }
        if(startGame == true){
            try {
                chatbot.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startGame();
        }
    }

    @Override
    public void resize(int width, int height) {
        CNinjas.getObjectManager().getViewport().update(width, height);
        
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        CNinjas.getObjectManager().removeObject(textbox);
        CNinjas.getObjectManager().removeObject(inputBox);
        CNinjas.getObjectManager().removeObject(responseBox);
        CNinjas.getObjectManager().removeObject(player1Box);
        CNinjas.getObjectManager().removeObject(player2Box);
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isInputReady() {
        return inputReady;
    }
        
    public void setInputReady(boolean bool){
        this.inputReady = bool;
    }

    public CurrentChat getCurrentChat() {
        return currentChat;
    }

    public void setCurrentChat(CurrentChat currentChat) {
        this.currentChat = currentChat;
    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }

    public void updateResponse(){
        responseBox.updateText(response, Color.RED);
    }

    public void createTextBox(TextBox box){
        if(box == null){
            textbox = new MultilineTextBox();
            textbox.setRelativeY(MENUHEIGHT/16*8);
        } 
        else{
            int oldX = box.getRelativeX();
            int oldY = box.getRelativeY();
            CNinjas.getObjectManager().removeObject(box);
            textbox = new MultilineTextBox();
            textbox.setRelativeX(oldX);
            textbox.setRelativeY(oldY);
        }
    }

    public int getMENUHEIGHT() {
        return MENUHEIGHT;
    }

    public int getMENUWIDTH() {
        return MENUWIDTH;
    }
    

    public void displayDefault(){
        createTextBox(textbox);
        textbox.setRelativeY(MENUHEIGHT/16*5);
        textbox.addTextLine("Color Ninjas", 3f);
        textbox.addTextLine("Enter <help> to view game instructions.", 2f);
        textbox.addTextLine("To change player name:", 2f);
        textbox.addTextLine("<name> [player1/player2] [name]", 2f, new Color(0.6f, 0.6f, 0.6f, 1f));
        textbox.addTextLine("To change player color:", 2f);
        textbox.addTextLine("<color> [player1/player2] [color]", 2f, new Color(0.6f, 0.6f, 0.6f, 1f));
        textbox.addTextLine("Available colors: red, green, blue, yellow.", 2f);
        textbox.addTextLine("Enter <start> to start game, <exit> to quit.", 2f);
        currentChat = CurrentChat.START;
    }

    public void displayHelpScreen(){
        createTextBox(textbox);
        textbox.addTextLine("How to play:", 2f);
        textbox.addTextLine("Defeat the opposing player by reducing their health to 0.", 2f);
        textbox.addTextLine("Use the WASD and arrow keys to move.", 2f);
        textbox.addTextLine("Collect powerups and do damage to gain score.", 2f);
        textbox.addTextLine("At the end of the round, player with the most score wins", 2f);
        textbox.addTextLine("Enter <back> to return, <exit> to quit:", 2f);
        currentChat = CurrentChat.HELP;
    }

    public void changePlayerName(int player, String name){
        Player playerobject = CNinjas.getObjectManager().getPlayerByNumber(player);
        playerobject.setName(name);
        switch(player){
            case 1:
            player1Box.updateText(playerobject.getName(), playerobject.getColor());
            break;
            case 2:
            player2Box.updateText(playerobject.getName(), playerobject.getColor());
            break;
            default:
            break;
        }
    }

    public boolean changePlayerColor(int player, PlayerColor color){
        for(int i = 0; i < colorsAvailable.size(); i++){
            if(color == colorsAvailable.get(i)){
				if(color != CNinjas.getObjectManager().getPlayerByNumber(player).getPlayerColor()){
					colorsAvailable.add(CNinjas.getObjectManager().getPlayerByNumber(player).getPlayerColor());
				}
                Player playerobject = CNinjas.getObjectManager().getPlayerByNumber(player);
                playerobject.setPlayerColor(color);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                       // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                       playerobject.prepareAnimation(color);
                    }
                 });
                //playerobject.prepareAnimation(color);
                switch(color){
                    case BLUE:
                    playerobject.setColor(Color.BLUE);
                        break;
                    case GREEN:
                    playerobject.setColor(Color.GREEN);
                        break;
                    case NONE:
                        break;
                    case RED:
                    playerobject.setColor(Color.RED);
                        break;
                    case YELLOW:
                    playerobject.setColor(Color.YELLOW);
                        break;
                    default:
                        break;
                    
                }
                switch(player){
                    case 1:
                    player1Box.updateText(playerobject.getName(), playerobject.getColor());
                    break;
                    case 2:
                    player2Box.updateText(playerobject.getName(), playerobject.getColor());
                    break;
                    default:
                    break;
                }
                colorsAvailable.remove(i);
                return true;
            }
        }
        return false;
    }

    public void startGame(){
        GameScreen gamescreen = new GameScreen();
        CNinjas.getGame().setScreen(gamescreen);
        CNinjas.getObjectManager().setGameScreen(gamescreen);
        CNinjas.setGameState(GameState.GAMERUNNING);
        currentChat = CurrentChat.START;
        this.dispose();
    }

    public void updateInput(){
        inputBox.updateText(input);
    }

    public void setTemporalResponse(String response){
        this.response = response;
        responseSetTime = TimeUtils.millis();
    }

    public void checkTemporalResponse(){
        if(responseSetTime != -1 && TimeUtils.millis() - responseSetTime > RESPONSEDURATION){
            response = "";
            updateResponse();
            responseSetTime = -1;
        }
    }

    public void updatePlayer1Box(String player1Name, Color color){
        player1Box.updateText(player1Name, color);
    }

    public void updatePlayer2Box(String player2Name, Color color){
        player2Box.updateText(player2Name, color);
    }

    public void prepareBG(){
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("map/mainmenu.tmx");
        int tiledMapGridSize = 16;
        int gridSize = 64;
        float scaleFactor = gridSize / tiledMapGridSize;
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, scaleFactor, CNinjas.getObjectManager().getSpriteBatch());
        CNinjas.getObjectManager().setMapRenderer(renderer);
        CNinjas.getObjectManager().getMapRenderer().setView(CNinjas.getObjectManager().getCamera());
    }

    @Override
    public void preRenderPrep() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        CNinjas.getObjectManager().getViewport().apply();
		CNinjas.getObjectManager().getCamera().update();
        Gdx.graphics.getGL30().glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
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
}
