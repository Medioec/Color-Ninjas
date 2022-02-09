package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.CNinjas;

public class Scoreboard extends GameObject{

    private ArrayList<Player> players;

    public Scoreboard(){
        setWidth(270);
        setHeight(200);
        setRelativeX(CNinjas.INIT_WIDTH - getWidth() - 20);
        setRelativeY(CNinjas.INIT_HEIGHT / 32 * 12);
        players = new ArrayList<Player>();
        CNinjas.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getShapeRenderer().begin();
        Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        CNinjas.getObjectManager().getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 0.4f));
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().end();
        CNinjas.getObjectManager().getSpriteBatch().begin();
        CNinjas.getObjectManager().getBitmapFont().setColor(new Color(1, 1, 1, 1));
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(2);
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), "Score:", getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*15);
        showPlayerScores();
        CNinjas.getObjectManager().getSpriteBatch().end();
    }

    public void addPlayerToScoreBoard(Player player){
        players.add(player);
    }

    public void sortPlayersByScore(){

    }

    public void showPlayerScores(){
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            CNinjas.getObjectManager().getBitmapFont().getData().setScale(1.4f);
            CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), player.getName(), getAbsoluteX() + getWidth()/16*2, getAbsoluteY() + getHeight()/16*(15 - 3*(i+1)));
            CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), String.format("%-10d", player.getScore()), getAbsoluteX() + getWidth()/16*8, getAbsoluteY() + getHeight()/16*(15 - 3*(i+1)));
            CNinjas.getObjectManager().getBitmapFont().getData().setScale(2);
        }
    }
    
}
