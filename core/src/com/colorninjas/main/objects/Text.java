package com.colorninjas.main.objects;

import com.badlogic.gdx.graphics.Color;

public class Text extends GameObject {

    private Color color;
    private float fontSize;
    private String text;

    public Text(String text, float fontSize, Color color){
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    @Override
    public void render(float delta) {
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
