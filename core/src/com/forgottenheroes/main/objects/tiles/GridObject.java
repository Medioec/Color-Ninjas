package com.forgottenheroes.main.objects.tiles;

import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.objects.GameObject;
import com.forgottenheroes.main.objects.Map;

public abstract class GridObject extends GameObject{

    //store coordinates of grid position as (x,y)
    private int[] coords = new int[2];

    public GridObject(int xcoord, int ycoord){
        this.setGridCoords(xcoord, ycoord);
    }

    public GridObject(Tile tile){
        int[] coords = tile.getGridCoords();
        this.setGridCoords(coords[0], coords[1]);
    }



    public abstract void render(FHeroes game);

    public int[] convertToGridCoords(int x, int y){
        Map map = FHeroes.getMap();
        int xcoord = (x - map.getX()) / map.getGridSize();
        int ycoord = (y - map.getY()) / map.getGridSize();
        int[] coord = {xcoord, ycoord};
        return coord;
    }

    public int[] getGridCoords(){
        return coords;
    }

    public int getGridXCoord(){
        return coords[0];
    }

    public int getGridYCoord(){
        return coords[1];
    }

    public void setGridCoords(int xcoord, int ycoord){
        Map map = FHeroes.getMap();
        this.coords[0] = xcoord;
        this.coords[1] = ycoord;
        this.setX(xcoord * map.getGridSize() + map.getX());
        this.setY(ycoord * map.getGridSize() + map.getY());
    }

    public void setGridCoords(int[] coords){
        Map map = FHeroes.getMap();
        this.coords = coords;
        this.setX(coords[0] * map.getGridSize() + map.getX());
        this.setY(coords[1] * map.getGridSize() + map.getY());
    }
}
