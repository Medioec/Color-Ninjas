package com.forgottenheroes.main.objects.tiles;

public abstract class TileObject extends GridObject{

    private int priority; //highest priority displayed on top
    private boolean isPassable;

    public TileObject(Tile tile) {
        super(tile);
    }

    //objects with higher priority gets displayed on top
    public void setDisplayPriority(int n){
        priority = n;
    }

    public int getDisplayPriority(){
        return priority;
    }

    public void setPassable(boolean bool){
        isPassable = bool;
    }
    
    public boolean isPassable(){
        return isPassable;
    }
}
