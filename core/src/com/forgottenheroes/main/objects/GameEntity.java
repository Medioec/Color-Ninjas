package com.forgottenheroes.main.objects;

import com.forgottenheroes.main.CNinjas;
import com.forgottenheroes.main.interfaces.MoveableInterface;

public abstract class GameEntity extends GameObject implements MoveableInterface{
    private int velX;
    private int velY;

    public GameEntity(){
        super();
        velX = 0;
        velY = 0;
        CNinjas.getObjectManager().addToEntityList(this);
    }

    public GameEntity(int[] coords){
        super();
        velX = 0;
        velY = 0;
        this.setGridCoords(coords);
        CNinjas.getObjectManager().addToEntityList(this);
    }

    public GameEntity(int x, int y, int velX, int velY){
        this.setAbsoluteX(x);
        this.setAbsoluteY(y);
        this.velX = velX;
        this.velY = velY;
    }
    
    public abstract void render(float delta);

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
        setAbsoluteX(getAbsoluteX() + velX);
    }

    public void updateYPos(){
        setAbsoluteY(getAbsoluteY() + velY);
    }
}
