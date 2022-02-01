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
        players = new ArrayList<Player>();
    }

    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().begin();
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 0.1f));
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().end();
        game.getSpriteBatch().begin();
        game.getBitmapFont().setColor(new Color(1, 1, 1, 1));
        game.getBitmapFont().draw(game.getSpriteBatch(), "Score:", getX() + getWidth()/16*2, getY() + getHeight()/16*15);
        showPlayerScores(game);
        game.getSpriteBatch().end();
    }

    public void addPlayerToScoreBoard(Player player){
        players.add(player);
    }

    public void sortPlayersByScore(){

    }

    public void showPlayerScores(FHeroes game){
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            game.getBitmapFont().getData().setScale(1.4f);
            game.getBitmapFont().draw(game.getSpriteBatch(), player.getName(), getX() + getWidth()/16*2, getY() + getHeight()/16*(15 - 3*(i+1)));
            game.getBitmapFont().draw(game.getSpriteBatch(), String.format("%-10d", player.getScore()), getX() + getWidth()/16*8, getY() + getHeight()/16*(15 - 3*(i+1)));
            game.getBitmapFont().getData().setScale(2);
        }
    }
    
}
