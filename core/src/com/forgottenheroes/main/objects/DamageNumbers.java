package com.forgottenheroes.main.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.CNinjas;

public class DamageNumbers extends GameEntity{

    private static final int FADEMS = 200;
    private int damage;
    private long spawnTime;

    public DamageNumbers(int x, int y, int velX, int velY, int damage){
        super(x, y, velX, velY);
        this.damage = damage;
        this.spawnTime = TimeUtils.millis();
        CNinjas.getObjectManager().addToEntityList(this);
    }

    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getSpriteBatch().begin();

        CNinjas.getObjectManager().getBitmapFont().getData().setScale(2);
        CNinjas.getObjectManager().getBitmapFont().setColor(new Color(0f, 0f, 0f, 1f));
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), "-" + String.valueOf(damage), getAbsoluteX(), getAbsoluteY());
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(2);
        this.updateYPos();
        CNinjas.getObjectManager().getSpriteBatch().end();
        if(TimeUtils.millis() - spawnTime > FADEMS){
            CNinjas.getObjectManager().queueForRemoval(this);
        }
    }

    public void updateVelY(){
        
    }

    @Override
    public void updatePos() {
        // TODO Auto-generated method stub
        
    }
    
}
