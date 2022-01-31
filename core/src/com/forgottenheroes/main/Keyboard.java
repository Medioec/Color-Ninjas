package com.forgottenheroes.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.forgottenheroes.main.objects.Player;
import com.forgottenheroes.main.objects.Player.PlayerNumber;

public class Keyboard {

    private FHeroes game;

    public Keyboard(FHeroes game){
        this.game = game;
    }

    public void checkKeyPress(){
        Player player1 = FHeroes.getObjectManager().getPlayerByNumber(PlayerNumber.PLAYER1);
        Player player2 = FHeroes.getObjectManager().getPlayerByNumber(PlayerNumber.PLAYER2);
        player1.initVel();
        player2.initVel();
        if(game.getGameState() == GameState.GAMERUNNING){
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
            if(Gdx.input.isKeyJustPressed(Keys.SLASH)){
                player1.performAttack(player1.getCurrentDirection());
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
            if(Gdx.input.isKeyJustPressed(Keys.V)){
                player2.performAttack(player2.getCurrentDirection());
            }
            player1.updateDirection();
            player2.updateDirection();
        }
    }
    
}
