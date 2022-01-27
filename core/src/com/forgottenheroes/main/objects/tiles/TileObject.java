package com.forgottenheroes.main.objects.tiles;

import com.forgottenheroes.main.FHeroes;

public abstract class TileObject extends GridObject{
    public TileObject(Tile tile){
        super(tile);
    }

    public abstract void render(FHeroes game);
}
