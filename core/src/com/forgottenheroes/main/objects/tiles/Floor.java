package com.forgottenheroes.main.objects.tiles;

import javax.xml.namespace.QName;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.CNinjas;
import com.forgottenheroes.main.objects.Map;
import com.forgottenheroes.main.objects.Player.PlayerColor;

public class Floor extends TileObject{
    private int owner;
    private Color tileColor;
    public Floor(Tile tile){
        super(tile);
        Map map = CNinjas.getObjectManager().getMap();
        setWidth(map.getGridSize());
        setHeight(map.getGridSize());
        setDisplayPriority(0);
        setPassable(true);
        owner = -1;
        tileColor = new Color(1,1,1,0);
    }
    //to replace shaperenderer shapes with actual textures
    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getShapeRenderer().begin();
        Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        CNinjas.getObjectManager().getShapeRenderer().setColor(tileColor);
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().setColor(Color.BLACK);
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Line);
        //FHeroes.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().end();
    }
    
    public void changeOwner(int player){
        owner = player;
        PlayerColor playerColor;
        if(player != 0){
            playerColor = CNinjas.getObjectManager().getPlayerByNumber(player).getPlayerColor();
        }
        else {
            playerColor = PlayerColor.NONE;
        }
        switch(playerColor){
            case RED:
                tileColor = new Color(1, 0, 0, 0.3f);
                break;
            case BLUE:
                tileColor = new Color(0, 0, 1, 0.3f);
                break;
            case GREEN:
                tileColor = new Color(0, 1, 0, 0.3f);
                break;
            case YELLOW:
                tileColor = new Color(1, 1, 0, 0.3f);
                break;
            default:
                tileColor = new Color(1,1,1,0);
                break;
        }
    }

    public int getOwner() {
        return owner;
    }
}
