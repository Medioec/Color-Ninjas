package com.forgottenheroes.main.objects;

import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.interfaces.MoveableInterface;

public abstract class GameEntity extends GameObject implements MoveableInterface{
    private int velX;
    private int velY;

    public GameEntity(){
        super();
        velX = 0;
        velY = 0;
        FHeroes.getObjectManager().addToEntityList(this);
    }

    public GameEntity(int[] coords){
        super();
        velX = 0;
        velY = 0;
        this.setGridCoords(coords);
        FHeroes.getObjectManager().addToEntityList(this);
    }

    public GameEntity(int x, int y, int velX, int velY){
        this.setRelativeX(x);
        this.setRelativeY(y);
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
        setRelativeX(getRelativeX() + velX - FHeroes.getxOffsetOrigin());;
    }

    public void updateYPos(){
        setRelativeY(getRelativeY() + velY - FHeroes.getyOffsetOrigin());
    }
}
