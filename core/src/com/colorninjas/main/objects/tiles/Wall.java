package com.colorninjas.main.objects.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.colorninjas.main.CNinjas;
import com.colorninjas.main.objects.Map;

public class Wall extends TileObject{

    public Wall(Tile tile) {
        super(tile);
        Map map = CNinjas.getObjectManager().getMap();
        setWidth(map.getGridSize());
        setHeight(map.getGridSize());
        setDisplayPriority(0);
        setPassable(false);
    }
    //to replace shaperenderer shapes with actual textures
    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getShapeRenderer().begin();
        CNinjas.getObjectManager().getShapeRenderer().setColor(Color.GRAY);
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().setColor(Color.BLACK);
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Line);
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().end();
    }
    
}
