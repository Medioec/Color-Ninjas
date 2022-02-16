package com.colorninjas.main.interfaces;

public interface ScreenInterface {
    abstract void preRenderPrep();

    abstract void changeViewportWorldSize(float width, float height);
    
    abstract void setCameraPosition(float width, float height);
}
