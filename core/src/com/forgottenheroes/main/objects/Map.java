package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.objects.Player.PlayerNumber;
import com.forgottenheroes.main.objects.tiles.Floor;
import com.forgottenheroes.main.objects.tiles.Tile;
import com.forgottenheroes.main.objects.tiles.Wall;

public class Map extends GameObject{

    private int vSize; //horizontal size (in squares)
    private int hSize; //vertical size
    private ObjectManager objectManager;
    private static int gridSize;

    public Map(ObjectManager objectManager){
        this.objectManager = objectManager;
    }

    @Override
    public void render(FHeroes game){
        
    }

    // return tile if found. else return null
    public Tile getTile(int[] coord){
        return objectManager.getTile(coord);
    }

    public ArrayList<Tile> getTileList(){
        return objectManager.getTileList();
    }

    public void addToTileList(Tile tile){
        objectManager.addToTileList(tile);
    }

    public void centerMap(){
        this.setY( (FHeroes.INIT_HEIGHT - this.getHeight() ) / 2 );
        this.setX( (FHeroes.INIT_WIDTH - this.getWidth() ) / 2 );
    }

    public void generateMap1(){
        gridSize = 64;
        setHSize(9);
        setVSize(9);
        int[] player1Coords = {0, 0};
        int[] player2Coords = {8, 8};
        int[][] wallList = { {1, 1}, {1, 2}, {1, 3}, {2, 1}, {3,1}, {7, 1}, {7, 2}, {7, 3}, {6, 1}, {5, 1}, 
            {1, 7}, {2, 7}, {3, 7}, {1, 6}, {1, 5}, {7, 7}, {7, 6}, {7, 5}, {6, 7}, {5, 7} };
        centerMap();
        for(int i = 0; i < getVSize(); i++){
            for(int j = 0; j < getHSize(); j++){
                Tile tile = new Tile(i, j);
                tile.addTileObject(new Floor(tile));
                objectManager.addToTileList(tile);
                for(int[] coord:wallList){
                    if(coord[0] == i && coord[1] == j){
                        tile.addTileObject(new Wall(tile));
                    }
                }
            }
        }
        Player player1 = new Player(player1Coords, Color.RED, PlayerNumber.PLAYER1);
        Player player2 = new Player(player2Coords, Color.BLUE, PlayerNumber.PLAYER2);
    }

    public int getVSize(){
        return vSize;
    }

    public void setVSize(int vSize){
        this.vSize = vSize;
        this.setHeight(vSize * gridSize);
    }

    public int getHSize(){
        return hSize;
    }

    public void setHSize(int hSize){
        this.hSize = hSize;
        this.setWidth(hSize * gridSize);
    }

    public int getGridSize(){
        return gridSize;
    }

    public void setGridSize(int size){
        gridSize = size;
    }

    public boolean isPassable(int[] coords){
        Tile tile = FHeroes.getObjectManager().getTile(coords);
        if(tile != null){
            return tile.isPassable();
        }
        return false;
    }
}
