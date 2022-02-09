package com.forgottenheroes.main.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.CNinjas;

public class HealthDisplay extends GameObject{

    private Player player;

    public HealthDisplay(Player player){
        this.player = player;
        setWidth(300);
        setHeight(100);
        switch(player.getPlayerNumber()){
            case 1:
                setRelativeX(0);
                setRelativeY(0);
                break;
            case 2:
                setRelativeX(CNinjas.INIT_WIDTH - getWidth());
                setRelativeY(CNinjas.INIT_HEIGHT - getHeight());
                break;
            default:
                break;

        }
    }

    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getShapeRenderer().begin();
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        CNinjas.getObjectManager().getShapeRenderer().setColor(new Color(1f, 1f, 1f, 1f));
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().setColor(new Color(0f, 0f, 0f, 1f));
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX() + 20, getAbsoluteY() + 10, getWidth() - 40, getHeight()/32 * 14);
        CNinjas.getObjectManager().getShapeRenderer().setColor(new Color(1f, 0f, 0f, 1f));
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX() + 30, getAbsoluteY() + 16, getWidth() - 60, getHeight()/32 * 10);
        CNinjas.getObjectManager().getShapeRenderer().setColor(new Color(0f, 1f, 0f, 1f));
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX() + 30, getAbsoluteY() + 16, (int) ((double) player.getHp() / (double) player.getMaxHP() * (getWidth() - 60)), getHeight()/32 * 10);
        CNinjas.getObjectManager().getShapeRenderer().end();
        CNinjas.getObjectManager().getSpriteBatch().begin();
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(2);
        CNinjas.getObjectManager().getBitmapFont().setColor(player.getColor());
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), player.getName(), getAbsoluteX() + 30, getAbsoluteY() + 90);
        CNinjas.getObjectManager().getBitmapFont().setColor(new Color(0f, 0f, 0f, 1f));
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), String.valueOf(player.getHp()) + "/" + String.valueOf(player.getMaxHP()), getAbsoluteX() + getWidth()/16*6, getAbsoluteY() + 42);

        CNinjas.getObjectManager().getSpriteBatch().end();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
