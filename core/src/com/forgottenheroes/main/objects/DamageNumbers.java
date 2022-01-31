package com.forgottenheroes.main.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.FHeroes;

public class DamageNumbers extends GameEntity{

    private static final int FADEMS = 200;
    private int damage;
    private long spawnTime;

    public DamageNumbers(int x, int y, int velX, int velY, int damage){
        super(x, y, velX, velY);
        this.damage = damage;
        this.spawnTime = TimeUtils.millis();
        FHeroes.getObjectManager().addToEntityList(this);
    }

    @Override
    public void render(FHeroes game) {
        game.getSpriteBatch().begin();

        game.getBitmapFont().getData().setScale(1);
        game.getBitmapFont().setColor(new Color(1f, 0f, 0f, 1f));
        game.getBitmapFont().draw(game.getSpriteBatch(), "-" + String.valueOf(damage), getX(), getY());
        game.getBitmapFont().getData().setScale(2);
        this.updateYPos();
        game.getSpriteBatch().end();
        if(TimeUtils.millis() - spawnTime > FADEMS){
            FHeroes.getObjectManager().queueForRemoval(this);
        }
    }

    public void updateVelY(){
        
    }
    
}
