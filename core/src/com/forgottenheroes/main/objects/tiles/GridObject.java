package com.forgottenheroes.main.objects.tiles;

import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.GameScreen;
import com.forgottenheroes.main.objects.GameObject;
import com.forgottenheroes.main.objects.Map;

public abstract class GridObject extends GameObject{

    //store coordinates of grid position as (x,y)
    private int[] coords = new int[2];

    public GridObject(int xcoord, int ycoord){
        super();
        this.setGridCoords(xcoord, ycoord);
    }

    public GridObject(Tile tile){
        super();
        int[] coords = tile.getGridCoords();
        this.setGridCoords(coords[0], coords[1]);
    }

    public abstract void render(float delta);

    public static int[] convertToGridCoords(int x, int y){
        Map map = FHeroes.getObjectManager().getMap();
        int xcoord = (x - map.getRelativeX()) / map.getGridSize();
        int ycoord = (y - map.getRelativeY()) / map.getGridSize();
        int[] coord = {xcoord, ycoord};
        return coord;
    }
    
    public int getGridXCoord(){
        return coords[0];
    }

    public int getGridYCoord(){
        return coords[1];
    }

    @Override
    public int[] getGridCoords(){
        return coords;
    }

    public void setGridCoords(int xcoord, int ycoord){
        Map map = FHeroes.getObjectManager().getMap();
        this.coords[0] = xcoord;
        this.coords[1] = ycoord;
        setRelativeX(xcoord * map.getGridSize() + map.getRelativeX());
        setRelativeY(ycoord * map.getGridSize() + map.getRelativeY());
        setAbsoluteX(FHeroes.getxOffsetOrigin() + getRelativeX());
        setAbsoluteY(FHeroes.getyOffsetOrigin() + getRelativeY());
    }

    @Override
    public void setGridCoords(int[] coords){
        Map map = FHeroes.getObjectManager().getMap();
        this.coords = coords;
        setRelativeX(coords[0] * map.getGridSize() + map.getRelativeX());
        setRelativeY(coords[1] * map.getGridSize() + map.getRelativeY());
        setAbsoluteX(FHeroes.getxOffsetOrigin() + getRelativeX());
        setAbsoluteY(FHeroes.getyOffsetOrigin() + getRelativeY());
    }
}
