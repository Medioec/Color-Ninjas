package com.forgottenheroes.main.objects;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class Popup extends GameObject{

    private String title;
    private String text;
    private String smallText;
    private float titleWidth;
    private float textWidth;
    private float smallTextWidth;
    private float titleScale;
    private float textScale;
    private float smallTextScale;
    private int textYOffset;

    public Popup(String title, String text, String smallText){
        setTitle(title);
        setText(text);
        setSmallText(smallText);
        setWidth(FHeroes.INIT_WIDTH / 2);
        setHeight(FHeroes.INIT_HEIGHT / 5);
        setX((FHeroes.INIT_WIDTH - getWidth()) / 2);
        setY((FHeroes.INIT_HEIGHT - getHeight()) / 2);
        setTitleScale(3);
        setTextScale(2);
        setSmallTextScale(1.5f);
        setTextYOffset(0);
        FHeroes.getObjectManager().getGame().getBitmapFont().getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), title);
        setTitleWidth(layout.width);
        FHeroes.getObjectManager().getGame().getBitmapFont().getData().setScale(getTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), text);
        setTextWidth(layout.width);
        FHeroes.getObjectManager().getGame().getBitmapFont().getData().setScale(getSmallTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), smallText);
        setSmallTextWidth(layout.width);
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(FHeroes game) {
        game.getShapeRenderer().begin();
        updateTextWidth();
        game.getShapeRenderer().set(ShapeType.Filled);
        game.getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 1f));
        game.getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        game.getShapeRenderer().end();
        game.getSpriteBatch().begin();
        game.getBitmapFont().setColor(new Color(1,1,1,1));
        game.getBitmapFont().getData().setScale(getTitleScale());
        game.getBitmapFont().draw(game.getSpriteBatch(), getTitle(), getX() + (getWidth() - getTitleWidth()) / 2, getY() + getHeight()/16*12);
        game.getBitmapFont().getData().setScale(getTextScale());
        game.getBitmapFont().draw(game.getSpriteBatch(), getText(), getX() + (getWidth() - getTextWidth()) / 2, getY() + getTextYOffset() + getHeight()/16*6);
        game.getBitmapFont().getData().setScale(getSmallTextScale());
        game.getBitmapFont().draw(game.getSpriteBatch(), getSmallText(), getX() + (getWidth() - getSmallTextWidth()) / 2, getY() + getHeight()/16*3);
        game.getSpriteBatch().end();
    }

    public void setText(String title, String text, String smallText){
        this.title = title;
        this.text = text;
        this.smallText = smallText;
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

    public String getSmallText() {
        return smallText;
    }

    public void setSmallText(String smallText) {
        this.smallText = smallText;
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

    public float getSmallTextWidth() {
        return smallTextWidth;
    }

    public void setSmallTextWidth(float smallTextWidth) {
        this.smallTextWidth = smallTextWidth;
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

    public float getSmallTextScale() {
        return smallTextScale;
    }

    public void setSmallTextScale(float smallTextScale) {
        this.smallTextScale = smallTextScale;
    }

    public int getTextYOffset() {
        return textYOffset;
    }

    public void setTextYOffset(int textYOffset) {
        this.textYOffset = textYOffset;
    }

    public void updateTextWidth(){
        BitmapFont font = FHeroes.getObjectManager().getGame().getBitmapFont();
        font.getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), title);
        setTitleWidth(layout.width);
        font.getData().setScale(getTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), text);
        setTextWidth(layout.width);
        font.getData().setScale(getSmallTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getGame().getBitmapFont(), smallText);
        setSmallTextWidth(layout.width);
    }
}
