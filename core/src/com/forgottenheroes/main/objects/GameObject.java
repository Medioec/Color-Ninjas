package com.forgottenheroes.main.objects;

import com.badlogic.gdx.graphics.Texture;
import com.forgottenheroes.main.CNinjas;

public abstract class GameObject {

    private int absoluteX;
    private int absoluteY;
    private int relativeX;
    private int relativeY;
    private int width;
    private int height;
    private int objectID;
    private Texture texture;
    private static int nextID = 1;

    public GameObject(){
        objectID = nextID;
        nextID++;
    }

    public abstract void render(float delta);

    public int getAbsoluteX() {
        return absoluteX;
    }

    public void setAbsoluteX(int absoluteX) {
        this.absoluteX = absoluteX;
        relativeX = absoluteX - CNinjas.getxOffsetOrigin();
    }

    public int getAbsoluteY() {
        return absoluteY;
    }

    public void setAbsoluteY(int absoluteY) {
        this.absoluteY = absoluteY;
        relativeY = absoluteY - CNinjas.getyOffsetOrigin();
    }

    public int getRelativeX(){
        return relativeX;
    }

    public int getRelativeY(){
        return relativeY;
    }

    public void setRelativeX(int x){
        this.relativeX = x;
        absoluteX = relativeX + CNinjas.getxOffsetOrigin();
    }

    public void setRelativeY(int y){
        this.relativeY = y;
        absoluteY = relativeY + CNinjas.getyOffsetOrigin();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getObjectID(){
        return objectID;
    }

    public void setObjectID(int objectID){
        this.objectID = objectID;
    }

    public Texture getTexture() {
        return texture;
}

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int[] getGridCoords(){
        Map map = CNinjas.getObjectManager().getMap();
        if(map.getRelativeX() < getRelativeX() && getRelativeX() < map.getRelativeX() + map.getWidth() && map.getRelativeY() < getRelativeY() && getRelativeY() < map.getRelativeY() + map.getHeight()){
            int[] coords = new int[2];
            coords[0] = (relativeX - map.getRelativeX()) / map.getGridSize();
            coords[1] = (relativeY - map.getRelativeY()) / map.getGridSize();
            return coords;
        }
        else return null;
    }

    //input of absolute coordinates
    public static int[] getGridCoords(int x, int y){
        Map map = CNinjas.getObjectManager().getMap();
        if(map.getAbsoluteX() < x && x < map.getAbsoluteX() + map.getWidth() && map.getAbsoluteY() < y && y < map.getAbsoluteY() + map.getHeight()){
            int[] coords = new int[2];
            coords[0] = (x - map.getAbsoluteX()) / map.getGridSize();
            coords[1] = (y - map.getAbsoluteY()) / map.getGridSize();
            return coords;
        }
        else return null;
    }

    public void setGridCoords(int[] coords){
        Map map = CNinjas.getObjectManager().getMap();
        setRelativeX((coords[0] * map.getGridSize() + map.getRelativeX() + map.getGridSize() / 2));
        setRelativeY((coords[1] * map.getGridSize() + map.getRelativeY() + map.getGridSize() / 2));
    }
}
