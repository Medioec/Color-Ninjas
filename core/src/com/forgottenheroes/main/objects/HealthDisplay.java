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
            case 1:
                setX(0);
                setY(0);
                break;
            case 2:
                setX(FHeroes.INIT_WIDTH - getWidth());
                setY(FHeroes.INIT_HEIGHT - getHeight());
                break;
            default:
                break;

        }
    }

    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(1f, 1f, 1f, 1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(0f, 0f, 0f, 1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getX() + 20, getY() + 10, getWidth() - 40, getHeight()/32 * 14);
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(1f, 0f, 0f, 1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getX() + 30, getY() + 16, getWidth() - 60, getHeight()/32 * 10);
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(0f, 1f, 0f, 1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getX() + 30, getY() + 16, (int) ((double) player.getHp() / (double) player.getMaxHP() * (getWidth() - 60)), getHeight()/32 * 10);
        FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getSpriteBatch().begin();
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(2);
        FHeroes.getObjectManager().getBitmapFont().setColor(player.getColor());
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), player.getName(), getX() + 30, getY() + 90);
        FHeroes.getObjectManager().getBitmapFont().setColor(new Color(0f, 0f, 0f, 1f));
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), String.valueOf(player.getHp()) + "/" + String.valueOf(player.getMaxHP()), getX() + getWidth()/16*6, getY() + 42);

        FHeroes.getObjectManager().getSpriteBatch().end();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
