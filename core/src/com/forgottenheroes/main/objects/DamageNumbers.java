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
    public void render(float delta) {
        FHeroes.getObjectManager().getSpriteBatch().begin();

        FHeroes.getObjectManager().getBitmapFont().getData().setScale(1);
        FHeroes.getObjectManager().getBitmapFont().setColor(new Color(1f, 0f, 0f, 1f));
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), "-" + String.valueOf(damage), getX(), getY());
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(2);
        this.updateYPos();
        FHeroes.getObjectManager().getSpriteBatch().end();
        if(TimeUtils.millis() - spawnTime > FADEMS){
            FHeroes.getObjectManager().queueForRemoval(this);
        }
    }

    public void updateVelY(){
        
    }

    @Override
    public void updatePos() {
        // TODO Auto-generated method stub
        
    }
    
}
