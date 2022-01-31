package com.forgottenheroes.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class DisplayScreen implements Screen{
    final FHeroes game;
    private Viewport viewport;
    private OrthographicCamera camera;

    public DisplayScreen(FHeroes game, Viewport viewport, OrthographicCamera camera){
        this.game = game;
        this.viewport = viewport;
        this.camera = camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport){
        this.viewport = viewport;
    }

    public OrthographicCamera getOrthographicCamera(){
        return camera;
    }

    public void setOrthographicCamera(OrthographicCamera camera){
        this.camera = camera;
    }
}
