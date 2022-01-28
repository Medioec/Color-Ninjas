package com.forgottenheroes.main.objects.tiles;

import java.util.ArrayList;

import com.forgottenheroes.main.FHeroes;

public class Tile extends GridObject{
    private ArrayList<GridObject> gridObjectList;

    public Tile(int x, int y){
        super(x, y);
        gridObjectList = new ArrayList<GridObject>();
    }

    @Override
    public void render(FHeroes game) {
        for(int i = 0; i < gridObjectList.size(); i++){
            GridObject object = gridObjectList.get(i);
            object.render(game);
        }
    }

    public ArrayList<GridObject> getGridObjectList(){
        return gridObjectList;
    }

    public void addGridObject(GridObject gridObject){
        gridObjectList.add(gridObject);
    }

    public void removeGridObject(GridObject gridObject){
        gridObjectList.remove(gridObject);
    }

    public boolean isPassable(){
        boolean isFloor = false;
        boolean isWall = false;
        for(int i = 0; i < gridObjectList.size(); i++){
            GridObject object = gridObjectList.get(i);
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
