package com.forgottenheroes.main.objects;

import com.forgottenheroes.main.FHeroes;

public abstract class GameEntity extends GameObject{
    private int velX;
    private int velY;

    public GameEntity(){
        velX = 0;
        velY = 0;
        FHeroes.getObjectManager().addToEntityList(this);
    }

    public GameEntity(int[] coords){
        velX = 0;
        velY = 0;
        this.setGridCoords(coords);
        FHeroes.getObjectManager().addToEntityList(this);
    }
    
    public abstract void render(FHeroes game);

    public void initVel(){
        velX = velY = 0;
    }
    
    public void setVelX(int velX){
        this.velX = velX;
    }

    public int getVelX(){
        return velX;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }

    public int getVelY(){
        return velY;
    }

    public void addVelXY(int addX, int addY){
        velX += addX;
        velY += addY;
    }

    public void updateXPos(){
        setX(getX() + velX);;
    }

    public void updateYPos(){
        setY(getY() + velY);
    }
}
