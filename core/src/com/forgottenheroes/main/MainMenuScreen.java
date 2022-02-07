package com.forgottenheroes.main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.bullet.dynamics.SWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenheroes.main.interfaces.ScreenInterface;
import com.forgottenheroes.main.objects.MultilineTextBox;
import com.forgottenheroes.main.objects.Player;
import com.forgottenheroes.main.objects.TextBox;

public class MainMenuScreen implements Screen, ScreenInterface{

    private final int MENUHEIGHT = 360;
    private final int MENUWIDTH = MENUHEIGHT/9*16;
    private final int RESPONSEDURATION = 3000;
    private String input;
    private String response;
    private long responseSetTime;
    private MultilineTextBox textbox;
    private TextBox inputBox;
    private TextBox responseBox;
    private boolean inputReady;
    private boolean startGame;
    private CurrentChat currentChat;
    private ArrayList<Color> colorsAvailable;

    private Player player1, player2;
    private Chatbot chatbot;

    public MainMenuScreen() {
        changeViewportWorldSize(MENUWIDTH, MENUHEIGHT);
        setCameraPosition(MENUWIDTH, MENUHEIGHT);
        player1 = new Player(1);
        player2 = new Player(2);
        colorsAvailable = new ArrayList<Color>();
        colorsAvailable.add(Color.RED);
        colorsAvailable.add(Color.BLUE);
        colorsAvailable.add(Color.GREEN);
        colorsAvailable.add(Color.YELLOW);
        displayDefault();
        inputBox = new TextBox();
        inputBox.setY(MENUHEIGHT/16*1);
        inputBox.addTextLine("", 1, Color.WHITE);
        inputBox.setFlexWidth(true);

        responseBox = new TextBox();
        responseBox.setBgEnabled(false);
        responseBox.setY(MENUHEIGHT/16*3);
        responseBox.addTextLine("", 1, Color.RED);
        responseBox.setFlexWidth(true);
        
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
        changeViewportWorldSize(MENUWIDTH, MENUHEIGHT);
        setCameraPosition(MENUWIDTH, MENUHEIGHT);
        FHeroes.getObjectManager().getShapeRenderer().begin();
		FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
		FHeroes.getObjectManager().getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		FHeroes.getObjectManager().getShapeRenderer().rect(0, 0, MENUWIDTH, MENUHEIGHT);
		FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getBitmapFont().setColor(Color.WHITE);
        FHeroes.getObjectManager().render(delta);
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
        FHeroes.getObjectManager().getViewport().update(width, height);
        
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
        FHeroes.getObjectManager().removeObject(textbox);
        FHeroes.getObjectManager().removeObject(inputBox);
        FHeroes.getObjectManager().removeObject(responseBox);
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
            textbox.setY(MENUHEIGHT/16*8);
        } 
        else{
            int oldX = box.getX();
            int oldY = box.getY();
            FHeroes.getObjectManager().removeObject(box);
            textbox = new MultilineTextBox();
            textbox.setX(oldX);
            textbox.setY(oldY);
        }
    }
    

    public void displayDefault(){
        createTextBox(textbox);
        textbox.setY(MENUHEIGHT/16*5);
        textbox.addTextLine("Forgotten Heroes", 1.4f);
        textbox.addTextLine("Enter <help> to view game instructions.");
        textbox.addTextLine("To change player name:");
        textbox.addTextLine("<name> [player1/player2] [name]", TextBox.DEFAULT_FONT_SIZE, new Color(0.6f, 0.6f, 0.6f, 1f));
        textbox.addTextLine("To change player color:");
        textbox.addTextLine("<color> [player1/player2] [color]", TextBox.DEFAULT_FONT_SIZE, new Color(0.6f, 0.6f, 0.6f, 1f));
        textbox.addTextLine("Available colors: red, green, blue, yellow.");
        textbox.addTextLine("Enter <start> to start game, <exit> to quit.");
        currentChat = CurrentChat.START;
    }

    public void displayHelpScreen(){
        createTextBox(textbox);
        textbox.addTextLine("How to play:");
        textbox.addTextLine("Defeat the opposing player by reducing their health to 0.");
        textbox.addTextLine("Use the WASD and arrow keys to move.");
        textbox.addTextLine("Collect powerups and do damage to gain score.");
        textbox.addTextLine("At the end of the round, player with the most score wins");
        textbox.addTextLine("Enter <back> to return, <exit> to quit:");
        currentChat = CurrentChat.HELP;
    }

    public void changePlayerName(int player, String name){
        FHeroes.getObjectManager().getPlayerByNumber(player).setName(name);
    }

    public boolean changePlayerColor(int player, Color color){
        for(int i = 0; i < colorsAvailable.size(); i++){
            if(color == colorsAvailable.get(i)){
				if(color != FHeroes.getObjectManager().getPlayerByNumber(player).getColor()){
					colorsAvailable.add(FHeroes.getObjectManager().getPlayerByNumber(player).getColor());
				}
                FHeroes.getObjectManager().getPlayerByNumber(player).setColor(color);
                colorsAvailable.remove(i);
                return true;
            }
        }
        return false;
    }

    public void startGame(){
        GameScreen gamescreen = new GameScreen();
        FHeroes.getGame().setScreen(gamescreen);
        FHeroes.getObjectManager().setGameScreen(gamescreen);
        FHeroes.setGameState(GameState.GAMERUNNING);
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

    @Override
    public void preRenderPrep() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        FHeroes.getObjectManager().getViewport().apply();
		FHeroes.getObjectManager().getCamera().update();
        Gdx.graphics.getGL30().glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
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
}
