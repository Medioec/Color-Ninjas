package com.forgottenheroes.main.objects;

import com.forgottenheroes.main.FHeroes;

public abstract class GameObject {

    private int x;
    private int y;
    private int width;
    private int height;
    private int objectID;
    private static int nextID = 1;

    public GameObject(){
        objectID = nextID;
        nextID++;
    }

    public abstract void render(FHeroes game);

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
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

    public int[] getGridCoords(){
        Map map = FHeroes.getMap();
        if(map.getX() < x && x < map.getX() + map.getWidth() && map.getY() < y && y < map.getY() + map.getHeight()){
            int[] coords = new int[2];
            coords[0] = (x - map.getX()) / map.getGridSize();
            coords[1] = (y - map.getY()) / map.getGridSize();
            return coords;
        }
        else return null;
    }

    public int[] getGridCoords(int x, int y){
        Map map = FHeroes.getMap();
        if(map.getX() < x && x < map.getX() + map.getWidth() && map.getY() < y && y < map.getY() + map.getHeight()){
            int[] coords = new int[2];
            coords[0] = (x - map.getX()) / map.getGridSize();
            coords[1] = (y - map.getY()) / map.getGridSize();
            return coords;
        }
        else return null;
    }

    public void setGridCoords(int[] coords){
        Map map = FHeroes.getMap();
        x = coords[0] * map.getGridSize() + FHeroes.getMap().getX() + map.getGridSize() / 2;
        y = coords[1] * map.getGridSize() + FHeroes.getMap().getY() + map.getGridSize() / 2;
    }
}