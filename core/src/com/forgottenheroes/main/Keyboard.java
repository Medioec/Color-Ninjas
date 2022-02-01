package com.forgottenheroes.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.forgottenheroes.main.objects.Player;
import com.forgottenheroes.main.objects.Player.PlayerNumber;

public class Keyboard implements InputProcessor{

    private FHeroes game;
    private ChatbotScreen chatbot;

    public Keyboard(FHeroes game){
        this.game = game;
    }

    public void checkKeyPress(){
        
        if(game.getGameState() == GameState.GAMERUNNING){
            Player player1 = FHeroes.getObjectManager().getPlayerByNumber(1);
            Player player2 = FHeroes.getObjectManager().getPlayerByNumber(2);
            player1.initVel();
            player2.initVel();
            if(!player1.isAttacking() && !player1.checkPlayerDefeated()){
                if(Gdx.input.isKeyPressed(Keys.SLASH)){
                    player1.performAttack(player1.getCurrentDirection());
                }
                if(Gdx.input.isKeyPressed(Keys.UP)){
                    player1.addVelXY(0, player1.getMoveSpeed());
                    //player1.updateDirection();
                }
                if(Gdx.input.isKeyPressed(Keys.DOWN)){
                    player1.addVelXY(0, -player1.getMoveSpeed());
                    //player1.updateDirection();
                }
                if(Gdx.input.isKeyPressed(Keys.LEFT)){
                    player1.addVelXY(-player1.getMoveSpeed(), 0);
                    //player1.updateDirection();
                }
                if(Gdx.input.isKeyPressed(Keys.RIGHT)){
                    player1.addVelXY(player1.getMoveSpeed(), 0);
                    //player1.updateDirection();
                }
            }
            if(!player2.isAttacking() && !player2.checkPlayerDefeated()){
                if(Gdx.input.isKeyPressed(Keys.V)){
                    player2.performAttack(player2.getCurrentDirection());
                }
                if(Gdx.input.isKeyPressed(Keys.W)){
                    player2.addVelXY(0, player2.getMoveSpeed());
                    //player2.updateDirection();
                }
                if(Gdx.input.isKeyPressed(Keys.S)){
                    player2.addVelXY(0, -player2.getMoveSpeed());
                    //player2.updateDirection();
                }
                if(Gdx.input.isKeyPressed(Keys.A)){
                    player2.addVelXY(-player2.getMoveSpeed(), 0);
                    //player2.updateDirection();
                }
                if(Gdx.input.isKeyPressed(Keys.D)){
                    player2.addVelXY(player2.getMoveSpeed(), 0);
                    ///player2.updateDirection();
                }
            }
            player1.updateDirection();
            player2.updateDirection();
        }
        else if(game.isGameState(GameState.GAMEOVER)){
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                FHeroes.getObjectManager().removeObject(FHeroes.getObjectManager().getPopup());
                game.setScreen(new ChatbotScreen(game, game.getViewport(), game.getOrthoGraphicCamera()));
                game.setGameState(GameState.CHATBOT);
                FHeroes.getObjectManager().removeEntity(FHeroes.getObjectManager().getPlayerByNumber(1));
                FHeroes.getObjectManager().removeEntity(FHeroes.getObjectManager().getPlayerByNumber(2));
                FHeroes.getObjectManager().getGameScreen().dispose();
            } else if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
                FHeroes.getObjectManager().startNewGame();
            }
        }
        else if(game.isGameState(GameState.CHATBOT)) {
            if(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)){
                String buffer = chatbot.getInput();
                if(buffer.length() > 0){
                    buffer = buffer.substring(0, buffer.length() - 1);
                    chatbot.setInput(buffer);
                }
                
            }
            else if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                chatbot.setGettingInput(true);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(game.isGameState(GameState.CHATBOT) && !Gdx.input.isKeyJustPressed(Keys.BACKSPACE) && !Gdx.input.isKeyJustPressed(Keys.ENTER)){
            String buffer = chatbot.getInput();
            buffer += character;
            if(buffer.length() > 40){
                buffer = buffer.substring(0, buffer.length() - 1);
            }
            chatbot.setInput(buffer);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }
    
    public ChatbotScreen getChatbot() {
        return chatbot;
    }

    public void setChatbot(ChatbotScreen chatbot) {
        this.chatbot = chatbot;
    }
}
