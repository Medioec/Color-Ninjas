package com.forgottenheroes.main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenheroes.main.objects.Player;
import com.forgottenheroes.main.objects.Popup;

public class ChatbotScreen extends DisplayScreen{

    private String title;
    private String text;
    private String input;
    private String response;
    private Popup popup;
    private Popup user;
    private boolean gettingInput;
    private CurrentChat currentChat;
    private ArrayList<Color> colorsAvailable;

    private Player player1, player2;

    public ChatbotScreen(FHeroes game, Viewport viewport, OrthographicCamera camera) {
        super(game, viewport, camera);
        player1 = new Player(1);
        player2 = new Player(2);
        colorsAvailable = new ArrayList<Color>();
        colorsAvailable.add(Color.RED);
        colorsAvailable.add(Color.BLUE);
        colorsAvailable.add(Color.GREEN);
        colorsAvailable.add(Color.YELLOW);
        popup = new Popup("Hello There!", "Would you like to view the game instructions?", "Enter yes or no below, or exit to quit:");
        user = new Popup("", "", "");
        setInput("");
        user.setY(FHeroes.INIT_HEIGHT/16*4);
        user.setHeight(FHeroes.INIT_HEIGHT/32*2);
        user.setTextYOffset(21);
        gettingInput = false;
        currentChat = CurrentChat.START;
        FHeroes.getObjectManager().setChatbotScreen(this);
        FHeroes.getObjectManager().getKeyboard().setChatbot(this);
    }

    public enum CurrentChat{
        START,
        HELP,
        NAMECHECK,
        NAME1,
        NAME2,
        COLOR1,
        COLOR2
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        getViewport().apply();
		getOrthographicCamera().update();
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		game.getSpriteBatch().setProjectionMatrix(getOrthographicCamera().combined);
        game.getShapeRenderer().setProjectionMatrix(getOrthographicCamera().combined);
        game.getShapeRenderer().begin();
		game.getShapeRenderer().set(ShapeType.Filled);
		game.getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		game.getShapeRenderer().rect(0, 0, FHeroes.INIT_WIDTH, FHeroes.INIT_HEIGHT);
		game.getShapeRenderer().end();
        game.getBitmapFont().setColor(Color.WHITE);
        FHeroes.getObjectManager().getKeyboard().checkKeyPress();
        user.setText(input);
        popup.render(game);
        user.render(game);
        if(gettingInput){
            chatbotProcess(input);
            gettingInput = false;
        }
        
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
        
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
        FHeroes.getObjectManager().removeObject(popup);
        FHeroes.getObjectManager().removeObject(user);
        
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
        
    public void setGettingInput(boolean bool){
        this.gettingInput = bool;
    }

    public int chatbotProcess(String input){
        if(cmpString(input, "yes")){
            chatbotDoYes();
        } else if(cmpString(input, "no")){
            chatbotDoNo();
        } else if(cmpString(input, "back")){
            chatbotDoBack();
        } else if(cmpString(input, "next")){
            chatbotDoNext();
        } else if(isInputColor(input)){
            chatbotDoColor();
        } else if(cmpString(input, "exit")){
            chatbotDoExit();
        } else chatbotDoName();
        return 0;
    }

    public boolean cmpString(String input, String string){
        if(input.equalsIgnoreCase(string)){
            return true;
        }
        return false;
    }

    public boolean isInputColor(String input){
        if(cmpString(input, "red") || cmpString(input, "blue") || cmpString(input, "green") || cmpString(input, "yellow")){
            return true;
        }
        return false;
    }

    public void chatbotDoYes(){
        if(currentChat == CurrentChat.START){
            popup.setText("", "To add response", "Enter back or next to continue:");
            currentChat = CurrentChat.HELP;
        } else if(currentChat == CurrentChat.NAMECHECK){
            popup.setText("Player 1:", "Set Player 1 name:", "Enter a name to continue, or exit to quit:");
            currentChat = CurrentChat.NAME1;
        }
        input = "";
    }

    public void chatbotDoNo(){
        if(currentChat == CurrentChat.START){
            popup.setText("Change Player Names", "Do you wish to change player names?", "Enter yes or no to continue, or exit to quit:");
            currentChat = CurrentChat.NAMECHECK;
        } else if(currentChat == CurrentChat.NAMECHECK){
            game.setScreen(new GameScreen(game, getViewport(), getOrthographicCamera()));
            game.setGameState(GameState.GAMERUNNING);
            currentChat = CurrentChat.START;
            dispose();
        }
        input = "";
    }

    public void chatbotDoBack(){
        if(currentChat == CurrentChat.HELP){
            popup.setText("Hello There!", "Would you like to view the game instructions?", "Enter yes or no to continue, or exit to quit:");
            currentChat = CurrentChat.START;
        }
        input = "";
    }

    public void chatbotDoNext(){
        if(currentChat == CurrentChat.HELP){
            popup.setText("Change Player Names", "Do you wish to change player names?", "Enter yes or no to continue, or exit to quit:");
            currentChat = CurrentChat.NAMECHECK;
        }
        input = "";
    }

    public void chatbotDoName(){
        if(currentChat == CurrentChat.NAME1){
            player1.setName(input);
            popup.setText("Player 1:", "Set Player 1 color:", "Enter red/blue/green/yellow to continue, or exit to quit:");
            currentChat = CurrentChat.COLOR1;
        } else if(currentChat == CurrentChat.NAME2){
            String colorString = "";
            player2.setName(input);
            for(int i = 0; i < colorsAvailable.size(); i++){
                Color color = colorsAvailable.get(i);
                if(color == Color.RED){
                    colorString += "red";
                } else if(color == Color.BLUE){
                    colorString += "blue";
                } else if(color == Color.GREEN){
                    colorString += "green";
                } else if(color == Color.YELLOW){
                    colorString += "yellow";
                }
                if(i < colorsAvailable.size() - 1){
                    colorString += "/";
                }
            }
            popup.setText("Player 2:", "Set Player 2 color:", "Enter " + colorString + " to continue, or exit to quit:");
            currentChat = CurrentChat.COLOR2;
        }
        input = "";
    }

    public void chatbotDoColor(){
        Color color;
        boolean valid = false;
        if(cmpString(input, "red")){
            color = Color.RED;
        } else if(cmpString(input, "blue")){
            color = Color.BLUE;
        } else if(cmpString(input, "green")){
            color = Color.GREEN;
        } else {
            color = Color.YELLOW;
        }
        if(currentChat == CurrentChat.COLOR1){
            player1.setColor(color);
            popup.setText("Player 2:", "Set Player 2 name:", "Enter a name to continue, or exit to quit:");
            currentChat = CurrentChat.NAME2;
            colorsAvailable.remove(color);
        } else if(currentChat == CurrentChat.COLOR2){
            for(int i = 0; i < colorsAvailable.size(); i++){
                Color item = colorsAvailable.get(i);
                if(item == color){
                    valid = true;
                }
            }
            if(valid){
                player2.setColor(color);
                game.setScreen(new GameScreen(game, getViewport(), getOrthographicCamera()));
                game.setGameState(GameState.GAMERUNNING);
                currentChat = CurrentChat.START;
                dispose();
            }            
        }
        input = "";
    }

    public void chatbotDoExit(){
        Gdx.app.exit();
    }
}
