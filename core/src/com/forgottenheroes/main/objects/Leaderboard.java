package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class Leaderboard extends GameObject{

    private ArrayList<Player> players;

    public Leaderboard(){
        setWidth(300);
        setHeight(200);
        setX(0);
        setY(FHeroes.INIT_HEIGHT / 32 * 12);
        players = new ArrayList<Player>();
    }

    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().begin();
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 1f));
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().end();
        game.getSpriteBatch().begin();
        game.getBitmapFont().setColor(new Color(1, 1, 1, 1));
        game.getBitmapFont().draw(game.getSpriteBatch(), "Rounds Won:", getX() + getWidth()/16*2, getY() + getHeight()/16*15);
        showPlayerWins(game);
        game.getSpriteBatch().end();
    }

    public void addPlayerToLeaderboard(Player player){
        players.add(player);
    }

    public void sortPlayersByWins(){
        
    }

    public void showPlayerWins(FHeroes game){
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            game.getBitmapFont().getData().setScale(1.4f);
            game.getBitmapFont().draw(game.getSpriteBatch(), player.getName(), getX() + getWidth()/16*2, getY() + getHeight()/16*(15 - 3*(i+1)));
            game.getBitmapFont().draw(game.getSpriteBatch(), String.format("%-10d", player.getWins()), getX() + getWidth()/16*8, getY() + getHeight()/16*(15 - 3*(i+1)));
            game.getBitmapFont().draw(game.getSpriteBatch(), "First to 3 wins", getX() + getWidth()/16*2, getY() + getHeight()/16*2);
            game.getBitmapFont().getData().setScale(2);
        }
    }
    
    public int[] getHighestWins(){
        int[] highest = {-1, -1};
        
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getWins() > highest[1]){
                highest = new int[]{player.getPlayerNumber(), player.getWins()};
            }
        }
        return highest;
    }
}
