package com.colorninjas.main.interfaces;

public interface MoveableInterface {
    abstract int getVelX();

    abstract int getVelY();

    abstract void setVelX(int velX);

    abstract void setVelY(int velY);

    abstract void updateXPos();
    
    abstract void updateYPos();

    abstract void updatePos();
}
