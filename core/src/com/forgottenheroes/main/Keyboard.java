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
        Player player1 = FHeroes.getObjectManager().getPlayerByNumber(1);
        Player player2 = FHeroes.getObjectManager().getPlayerByNumber(2);
        player1.initVel();
        player2.initVel();
        if(game.getGameState() == GameState.GAMERUNNING){
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
            if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
                FHeroes.getObjectManager().startNewGame();
            }
        }
    }
    
}
