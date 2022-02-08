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
        setRelativeX(0);
        setRelativeY(FHeroes.INIT_HEIGHT / 32 * 12);
        players = new ArrayList<Player>();
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getSpriteBatch().begin();
        FHeroes.getObjectManager().getBitmapFont().setColor(new Color(1, 1, 1, 1));
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(2);
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), "Rounds Won:", getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*15);
        showPlayerWins();
        FHeroes.getObjectManager().getSpriteBatch().end();
    }

    public void addPlayerToLeaderboard(Player player){
        players.add(player);
    }

    public void sortPlayersByWins(){
        
    }

    public void showPlayerWins(){
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            FHeroes.getObjectManager().getBitmapFont().getData().setScale(1.4f);
            FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), player.getName(), getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*(15 - 3*(i+1)));
            FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), String.format("%-10d", player.getWins()), getAbsoluteX() + getWidth()/16*8, getAbsoluteY() + getHeight()/16*(15 - 3*(i+1)));
            FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), "First to 3 wins", getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*2);
            FHeroes.getObjectManager().getBitmapFont().getData().setScale(2);
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
