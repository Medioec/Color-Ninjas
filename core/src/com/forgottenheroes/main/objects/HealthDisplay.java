package com.forgottenheroes.main.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class HealthDisplay extends GameObject{

    private Player player;

    public HealthDisplay(Player player){
        this.player = player;
        setWidth(300);
        setHeight(100);
        switch(player.getPlayerNumber()){
            case PLAYER1:
                setX(0);
                setY(0);
                break;
            case PLAYER2:
                setX(FHeroes.INIT_WIDTH - getWidth());
                setY(FHeroes.INIT_HEIGHT - getHeight());
                break;
            default:
                break;

        }
    }

    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().begin();
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().setColor(new Color(1f, 1f, 1f, 1f));
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().setColor(new Color(0f, 0f, 0f, 1f));
        game.getShapeRenderer().rect(getX() + 20, getY() + 10, getWidth() - 40, getHeight()/32 * 14);
        game.getShapeRenderer().setColor(new Color(1f, 0f, 0f, 1f));
        game.getShapeRenderer().rect(getX() + 30, getY() + 16, getWidth() - 60, getHeight()/32 * 10);
        game.getShapeRenderer().setColor(new Color(0f, 1f, 0f, 1f));
        game.getShapeRenderer().rect(getX() + 30, getY() + 16, (int) ((double) player.getHp() / (double) player.getMaxHP() * (getWidth() - 60)), getHeight()/32 * 10);
        game.getShapeRenderer().end();
        game.getSpriteBatch().begin();
        game.getBitmapFont().getData().setScale(2);
        game.getBitmapFont().setColor(player.getColor());
        game.getBitmapFont().draw(game.getSpriteBatch(), player.getName(), getX() + 30, getY() + 90);
        game.getBitmapFont().setColor(new Color(0f, 0f, 0f, 1f));
        game.getBitmapFont().draw(game.getSpriteBatch(), String.valueOf(player.getHp()), getX() + 125, getY() + 42);

        game.getSpriteBatch().end();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
