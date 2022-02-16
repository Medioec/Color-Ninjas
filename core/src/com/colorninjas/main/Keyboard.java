package com.colorninjas.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.TimeUtils;
import com.colorninjas.main.objects.Player;

public class Keyboard implements InputProcessor{

    private long initialPress;
    private long lastDelete;
    private final int BACKSPACEDELAY = 200;
    private final int DELETESPEED = 30;

    public Keyboard(){
    }

    public void checkKeyPress(){

        Player player1 = CNinjas.getObjectManager().getPlayerByNumber(1);
        Player player2 = CNinjas.getObjectManager().getPlayerByNumber(2);
        if(player1 != null && player2 != null){
            player1.initVel();
            player2.initVel();
        }
        if(CNinjas.getGameState() == GameState.GAMERUNNING){
            if(!player1.checkPlayerDefeated()){
                if(Gdx.input.isKeyPressed(Keys.UP)){
                    player1.addVelXY(0, player1.getMoveSpeed());
                }
                if(Gdx.input.isKeyPressed(Keys.DOWN)){
                    player1.addVelXY(0, -player1.getMoveSpeed());
                }
                if(Gdx.input.isKeyPressed(Keys.LEFT)){
                    player1.addVelXY(-player1.getMoveSpeed(), 0);
                }
                if(Gdx.input.isKeyPressed(Keys.RIGHT)){
                    player1.addVelXY(player1.getMoveSpeed(), 0);
                }
                if(!player1.isAttacking()){
                    if(Gdx.input.isKeyPressed(Keys.SLASH)){
                        player1.performAttack(player1.getCurrentDirection());
                    }
                }
                player1.updateDirection();
            }
            if(!player2.checkPlayerDefeated()){
                if(Gdx.input.isKeyPressed(Keys.W)){
                    player2.addVelXY(0, player2.getMoveSpeed());
                }
                if(Gdx.input.isKeyPressed(Keys.S)){
                    player2.addVelXY(0, -player2.getMoveSpeed());
                }
                if(Gdx.input.isKeyPressed(Keys.A)){
                    player2.addVelXY(-player2.getMoveSpeed(), 0);
                }
                if(Gdx.input.isKeyPressed(Keys.D)){
                    player2.addVelXY(player2.getMoveSpeed(), 0);
                }
                if(!player2.isAttacking()){
                    if(Gdx.input.isKeyPressed(Keys.V)){
                        player2.performAttack(player2.getCurrentDirection());
                    }
                }
                player2.updateDirection();
            }
        }
        else if(CNinjas.isGameState(GameState.GAMEOVER)){
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                CNinjas.getObjectManager().clearObjects();
                MainMenuScreen mainmenu = new MainMenuScreen();
                CNinjas.getGame().setScreen(mainmenu);
                CNinjas.getObjectManager().setMainMenuScreen(mainmenu);
                CNinjas.setGameState(GameState.MAINMENU);
                CNinjas.getObjectManager().getGameScreen().dispose();
            } else if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
                CNinjas.getObjectManager().startNewGame();
            }
        }
        else if(CNinjas.isGameState(GameState.MAINMENU)) {
            if(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)){
                initialPress = TimeUtils.millis();
                String buffer = CNinjas.getObjectManager().getMainMenuScreen().getInput();
                if(buffer.length() > 0){
                    buffer = buffer.substring(0, buffer.length() - 1);
                    CNinjas.getObjectManager().getMainMenuScreen().setInput(buffer);
                    CNinjas.getObjectManager().getMainMenuScreen().updateInput();
                }
            }
            if(Gdx.input.isKeyPressed(Keys.BACKSPACE) && (TimeUtils.millis() - initialPress > BACKSPACEDELAY)){
                String buffer = CNinjas.getObjectManager().getMainMenuScreen().getInput();
                if(buffer.length() > 0 && TimeUtils.millis() - lastDelete > DELETESPEED){
                    buffer = buffer.substring(0, buffer.length() - 1);
                    CNinjas.getObjectManager().getMainMenuScreen().setInput(buffer);
                    CNinjas.getObjectManager().getMainMenuScreen().updateInput();
                    lastDelete = TimeUtils.millis();
                }
            }
            else if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                CNinjas.getObjectManager().getMainMenuScreen().setInputReady(true);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(CNinjas.isGameState(GameState.MAINMENU) && (Character.isLetterOrDigit(character) || character == ' ')){
            String buffer = CNinjas.getObjectManager().getMainMenuScreen().getInput();
            buffer += character;
            if(buffer.length() > 30){
                buffer = buffer.substring(0, buffer.length() - 1);
            }
            CNinjas.getObjectManager().getMainMenuScreen().setInput(buffer);
            CNinjas.getObjectManager().getMainMenuScreen().updateInput();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
