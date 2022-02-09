package com.forgottenheroes.main.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.CNinjas;

public class AttackSwing extends GameObject{

    private long spawnTime;
    private int originX;
    private int originY;
    private int scaleX;
    private int scaleY;
    private int rotation;
    private int srcX;
    private int srcY;
    private int srcWidth;
    private int srcHeight;
    private boolean flipX;
    private boolean flipY;
    private final int FADEMS = 100;

    public AttackSwing(Player player){
        setTexture(new Texture(Gdx.files.internal("attacksprite.png")));
        setWidth(100);
        setHeight(50);
        setAbsoluteX(player.getAbsoluteX());
        setAbsoluteY(player.getAbsoluteY() - getHeight() / 2);
        originX = 0;
        originY = getTexture().getHeight() / 2;
        scaleX = 1;
        scaleY = 2;
        rotation = rotateToPlayerDirection(player);
        srcX = 0;
        srcY = 0;
        srcWidth = getWidth();
        srcHeight = getHeight();
        flipX = false;
        flipY = false;
        
        spawnTime = TimeUtils.millis();
        CNinjas.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getSpriteBatch().begin();
        CNinjas.getObjectManager().getSpriteBatch().draw(getTexture(), getAbsoluteX(), getAbsoluteY(), originX, originY, getWidth(), getHeight(), scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        CNinjas.getObjectManager().getSpriteBatch().end();
        if(TimeUtils.millis() - spawnTime > FADEMS){
            CNinjas.getObjectManager().queueForRemoval(this);
        }
    }

    public int rotateToPlayerDirection(Player player){
        switch(player.getCurrentDirection()){
            case E:
            return 0;
            case N:
            return 90;
            case NE:
            return 45;
            case NW:
            return 135;
            case S:
            return 270;
            case SE:
            return 315;
            case SW:
            return 225;
            case W:
            return 180;
            default:
            return 0;
        }
    }    
}
