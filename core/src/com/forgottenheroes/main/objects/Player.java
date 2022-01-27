package com.forgottenheroes.main.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class Player extends GameEntity{
    private int moveSpeed;
    private Color color;
    private PlayerNumber pNumber;
    
    public static enum PlayerNumber{
        PLAYER1,
        PLAYER2;
    }

    public Player(int[] spawncoords, Color color, PlayerNumber pNumber){
        super(spawncoords);
        setGridCoords(spawncoords);
        setHeight(40);
        setWidth(40);
        moveSpeed = 5;
        this.color = color;
        this.pNumber = pNumber;
    }

    @Override
    public void render(FHeroes game) {
        initVel();
        switch(pNumber) {
            case PLAYER1:
                if(Gdx.input.isKeyPressed(Keys.UP)){
                    addVelXY(0, moveSpeed);
                }
                if(Gdx.input.isKeyPressed(Keys.DOWN)){
                    addVelXY(0, -moveSpeed);
                }
                if(Gdx.input.isKeyPressed(Keys.LEFT)){
                    addVelXY(-moveSpeed, 0);
                }
                if(Gdx.input.isKeyPressed(Keys.RIGHT)){
                    addVelXY(moveSpeed, 0);
                }
            break;
            case PLAYER2:
                if(Gdx.input.isKeyPressed(Keys.W)){
                    addVelXY(0, moveSpeed);
                }
                if(Gdx.input.isKeyPressed(Keys.S)){
                    addVelXY(0, -moveSpeed);
                }
                if(Gdx.input.isKeyPressed(Keys.A)){
                    addVelXY(-moveSpeed, 0);
                }
                if(Gdx.input.isKeyPressed(Keys.D)){
                    addVelXY(moveSpeed, 0);
                }
            break;
        }
        
        if(isValidXMovement()) updateXPos();
        if(isValidYMovement()) updateYPos();
        game.getShapeRenderer().setColor(color);
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().circle(getX(), getY(), getHeight() / 2);

    }
    
    public boolean isValidXMovement(){
        int newX = getX() + getVelX();
        int newY = getY();
        int[] coords = getGridCoords(newX, newY);
        if(coords != null) return FHeroes.getMap().isPassable(coords);
        else return false;
    }

    public boolean isValidYMovement(){
        int newX = getX();
        int newY = getY() + getVelY();
        int[] coords = getGridCoords(newX, newY);
        if(coords != null) return FHeroes.getMap().isPassable(coords);
        else return false;
    }
}
