package com.forgottenheroes.main.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.FHeroes;

public class AttackSwing extends GameObject{

    private long spawnTime;
    private final int FADEMS = 100;

    public AttackSwing(Player player){
        setSprite(new Sprite(new Texture(Gdx.files.internal("attacksprite.png"))));
        getSprite().setOrigin(0, getSprite().getHeight() / 2);
        rotateToPlayerDirection(player);
        getSprite().setX(player.getX());
        getSprite().setY(player.getY() - player.getAtkWidth() / 2);
        spawnTime = TimeUtils.millis();
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(FHeroes game) {
        game.getSpriteBatch().begin();
        getSprite().draw(game.getSpriteBatch());
        game.getSpriteBatch().end();
        if(TimeUtils.millis() - spawnTime > FADEMS){
            FHeroes.getObjectManager().queueForRemoval(this);
        }
    }

    public void rotateToPlayerDirection(Player player){
        switch(player.getCurrentDirection()){
            case E:
                break;
            case N:
            getSprite().setRotation(90);
                break;
            case NE:
            getSprite().setRotation(45);
                break;
            case NW:
            getSprite().setRotation(135);
                break;
            case S:
            getSprite().setRotation(270);
                break;
            case SE:
            getSprite().setRotation(315);
                break;
            case SW:
            getSprite().setRotation(225);
                break;
            case W:
            getSprite().setRotation(180);
                break;
            default:
                break;
        }
    }    
}
