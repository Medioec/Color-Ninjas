package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.objects.tiles.Tile;

public class ObjectManager {
    private ArrayList<Tile> tileList;
    private ArrayList<GameEntity> entityList;

    public ObjectManager(){
        tileList = new ArrayList<Tile>();
        entityList = new ArrayList<GameEntity>();
    }

    public void render(FHeroes game){
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            tile.render(game);
        }
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            entity.render(game);
        }

    }

    public ArrayList<Tile> getTileList(){
        return tileList;
    }

    public ArrayList<GameEntity> getEntityList(){
        return entityList;
    }

    public void addToTileList(Tile tile){
        tileList.add(tile);
    }

    public void addToEntityList(GameEntity entity){
        entityList.add(entity);
    }

    // return tile if found. else return null
    public Tile getTile(int[] coord){
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            int[] fetched = tile.getGridCoords();
            if(fetched[0] == coord[0] && fetched[1] == coord[1]){
                return tile;
            }
        }
        return null;
    }

    public void clearObjects(){
        tileList.clear();
        entityList.clear();
        
    }
}
