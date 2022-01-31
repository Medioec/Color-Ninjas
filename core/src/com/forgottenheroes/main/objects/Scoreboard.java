package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class Scoreboard extends GameObject{

    private ArrayList<Player> players;

    public Scoreboard(){
        setWidth(300);
        setHeight(200);
        setX(FHeroes.INIT_WIDTH - getWidth());
        setY(FHeroes.INIT_HEIGHT / 32 * 12);
    }

    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().begin();
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 0.1f));
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().end();
    }

    public void addPlayerToScoreBoard(Player player){
        players.add(player);
    }

    public void sortPlayersByScore(){

    } 
    
}
