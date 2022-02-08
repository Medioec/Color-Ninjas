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
        setRelativeX(FHeroes.INIT_WIDTH - getWidth());
        setRelativeY(FHeroes.INIT_HEIGHT / 32 * 12);
        players = new ArrayList<Player>();
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 0.1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getSpriteBatch().begin();
        FHeroes.getObjectManager().getBitmapFont().setColor(new Color(1, 1, 1, 1));
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(2);
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), "Score:", getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*15);
        showPlayerScores();
        FHeroes.getObjectManager().getSpriteBatch().end();
    }

    public void addPlayerToScoreBoard(Player player){
        players.add(player);
    }

    public void sortPlayersByScore(){

    }

    public void showPlayerScores(){
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            FHeroes.getObjectManager().getBitmapFont().getData().setScale(1.4f);
            FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), player.getName(), getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*(15 - 3*(i+1)));
            FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), String.format("%-10d", player.getScore()), getAbsoluteX() + getWidth()/16*8, getAbsoluteY() + getHeight()/16*(15 - 3*(i+1)));
            FHeroes.getObjectManager().getBitmapFont().getData().setScale(2);
        }
    }
    
}
