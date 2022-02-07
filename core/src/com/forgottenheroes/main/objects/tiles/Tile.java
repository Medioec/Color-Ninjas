package com.forgottenheroes.main.objects.tiles;

import java.util.ArrayList;

import com.forgottenheroes.main.FHeroes;

public class Tile extends GridObject{
    private ArrayList<TileObject> tileObjectList;

    public Tile(int x, int y){
        super(x, y);
        tileObjectList = new ArrayList<TileObject>();
    }

    @Override
    public void render(float delta) {
        for(int i = 0; i < tileObjectList.size(); i++){
            TileObject object = tileObjectList.get(i);
            object.render(delta);
        }
    }

    public ArrayList<TileObject> getTileObjectList(){
        return tileObjectList;
    }

    public void addTileObject(TileObject tileObject){
        tileObjectList.add(tileObject);
    }

    public void removeTileObject(TileObject tileObject){
        tileObjectList.remove(tileObject);
    }

    public Floor getFloor(){
        for(int i = 0; i < tileObjectList.size(); i++){
            TileObject object = tileObjectList.get(i);
            if(object instanceof Floor){
                Floor floor = (Floor) object;
                return floor;
            }
        }
        return null;
    }

    public boolean isPassable(){
        boolean isFloor = false;
        boolean isWall = false;
        for(int i = 0; i < tileObjectList.size(); i++){
            TileObject object = tileObjectList.get(i);
            if(object != null){
                if(object instanceof Floor){
                    isFloor = true;
                }
                else if(object instanceof Wall){
                    isWall = true;
                }
            }
        }
        if(isFloor && !isWall){
            return true;
        }
        return false;
    }
}
