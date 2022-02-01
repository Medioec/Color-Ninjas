package com.forgottenheroes.main.objects;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class Popup extends GameObject{

    private String title;
    private String text;
    private float titleWidth;
    private float textWidth;
    private float titleScale;
    private float textScale;

    public Popup(){
        setWidth(FHeroes.INIT_WIDTH / 2);
        setHeight(FHeroes.INIT_HEIGHT / 5);
        setX((FHeroes.INIT_WIDTH - getWidth()) / 2);
        setY((FHeroes.INIT_HEIGHT - getHeight()) / 2);
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), "");
        FHeroes.getObjectManager().addToObjectList(this);
    }

    public Popup(String title, String text){
        setTitle(title);
        setText(text);
        setWidth(FHeroes.INIT_WIDTH / 2);
        setHeight(FHeroes.INIT_HEIGHT / 5);
        setX((FHeroes.INIT_WIDTH - getWidth()) / 2);
        setY((FHeroes.INIT_HEIGHT - getHeight()) / 2);
        setTitleScale(3);
        setTextScale(2);
        FHeroes.getObjectManager().getGame().getBitmapFont().getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), title);
        setTitleWidth(layout.width);
        FHeroes.getObjectManager().getGame().getBitmapFont().getData().setScale(getTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), text);
        setTextWidth(layout.width);
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().begin();
        game.getShapeRenderer().set(ShapeType.Filled);//
        game.getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 1f));
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().end();
        game.getSpriteBatch().begin();
        game.getBitmapFont().setColor(new Color(1,1,1,1));
        game.getBitmapFont().getData().setScale(3);
        game.getBitmapFont().draw(game.getSpriteBatch(), getTitle(), getX() + (getWidth() - getTitleWidth()) / 2, getY() + getHeight()/16*12);
        game.getBitmapFont().getData().setScale(2);
        game.getBitmapFont().draw(game.getSpriteBatch(), getText(), getX() + (getWidth() - getTextWidth()) / 2, getY() + getHeight()/16*6);
        game.getSpriteBatch().end();
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getTitleWidth() {
        return titleWidth;
    }

    public void setTitleWidth(float titleWidth) {
        this.titleWidth = titleWidth;
    }

    public float getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(float textWidth) {
        this.textWidth = textWidth;
    }

    public float getTitleScale() {
        return titleScale;
    }

    public void setTitleScale(float titleScale) {
        this.titleScale = titleScale;
    }

    public float getTextScale() {
        return textScale;
    }

    public void setTextScale(float textScale) {
        this.textScale = textScale;
    }
}
