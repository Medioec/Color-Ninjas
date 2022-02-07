package com.forgottenheroes.main.objects.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.objects.Map;

public class Floor extends TileObject{
    private int owner;
    private Color tileColor;
    public Floor(Tile tile){
        super(tile);
        Map map = FHeroes.getObjectManager().getMap();
        setWidth(map.getGridSize());
        setHeight(map.getGridSize());
        setDisplayPriority(0);
        setPassable(true);
        owner = -1;
        tileColor = Color.WHITE;
    }
    //to replace shaperenderer shapes with actual textures
    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();
        FHeroes.getObjectManager().getShapeRenderer().setColor(tileColor);
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        FHeroes.getObjectManager().getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().setColor(Color.BLACK);
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Line);
        FHeroes.getObjectManager().getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().end();
    }
    
    public void changeOwner(int player){
        owner = player;
        switch(owner){
            case 1:
            tileColor = FHeroes.getObjectManager().getPlayerByNumber(1).getColor();
            break;
            case 2:
            tileColor = FHeroes.getObjectManager().getPlayerByNumber(2).getColor();
            break;
            default:
            tileColor = Color.WHITE;
        }
    }

    public int getOwner() {
        return owner;
    }
}
