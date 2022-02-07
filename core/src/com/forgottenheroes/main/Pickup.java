package com.forgottenheroes.main;

import com.forgottenheroes.main.objects.tiles.GridObject;
import com.forgottenheroes.main.objects.tiles.Tile;

public class Pickup extends GridObject{

    private Type type;

    public Pickup(Tile tile, Type type) {
        super(tile);
        this.type = type;
    }

    @Override
    public void render(float delta) {
        //TODO work on this
    }

    public static enum Type{
        WEAPON,
        SCORE,
        EQUIPMENT,
        HEAL
    }
    
    public void addPickupToPlayer(){
        switch(type){
            case WEAPON:
                break;
            case EQUIPMENT:
                break;
            case HEAL:
                break;
            case SCORE:
                break;
            default:
                break;
        }
    }

    public void removePickupFromMap(){
        
    }
}
