package com.forgottenheroes.main.objects.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.objects.Map;

public class Wall extends TileObject{

    public Wall(Tile tile) {
        super(tile);
        Map map = FHeroes.getObjectManager().getMap();
        setWidth(map.getGridSize());
        setHeight(map.getGridSize());
        setDisplayPriority(0);
        setPassable(false);
    }
    //to replace shaperenderer shapes with actual textures
    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();
        FHeroes.getObjectManager().getShapeRenderer().setColor(Color.GRAY);
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        FHeroes.getObjectManager().getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().setColor(Color.BLACK);
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Line);
        FHeroes.getObjectManager().getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().end();
    }
    
}
