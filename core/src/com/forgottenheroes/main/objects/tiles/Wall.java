package com.forgottenheroes.main.objects.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.objects.Map;

public class Wall extends GridObject{

    public Wall(Tile tile) {
        super(tile);
        Map map = FHeroes.getMap();
        setWidth(map.getGridSize());
        setHeight(map.getGridSize());
    }
    //to replace shaperenderer shapes with actual textures
    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().setColor(Color.GRAY);
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().setColor(Color.BLACK);
        game.getShapeRenderer().set(ShapeType.Line);
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
    }
    
}
