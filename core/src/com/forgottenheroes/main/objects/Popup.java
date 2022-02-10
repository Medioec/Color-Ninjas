package com.forgottenheroes.main.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.CNinjas;

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
        setWidth(CNinjas.INIT_WIDTH / 2);
        setHeight(CNinjas.INIT_HEIGHT / 5);
        setRelativeX((CNinjas.INIT_WIDTH - getWidth()) / 2);
        setRelativeY((CNinjas.INIT_HEIGHT - getHeight()) / 2);
        setTitleScale(3);
        setTextScale(2);
        setSmallTextScale(1.5f);
        setTextYOffset(0);
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), title);
        setTitleWidth(layout.width);
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(getTextScale());
        layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), text);
        setTextWidth(layout.width);
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(getSmallTextScale());
        layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), smallText);
        setSmallTextWidth(layout.width);
        CNinjas.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getShapeRenderer().begin();
        updateTextWidth();
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        CNinjas.getObjectManager().getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 0.6f));
        CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        CNinjas.getObjectManager().getShapeRenderer().end();
        CNinjas.getObjectManager().getSpriteBatch().begin();
        CNinjas.getObjectManager().getBitmapFont().setColor(new Color(1,1,1,1));
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(getTitleScale());
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), getTitle(), getAbsoluteX() + (getWidth() - getTitleWidth()) / 2, getAbsoluteY() + getHeight()/16*12);
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(getTextScale());
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), getText(), getAbsoluteX() + (getWidth() - getTextWidth()) / 2, getAbsoluteY() + getTextYOffset() + getHeight()/16*6);
        CNinjas.getObjectManager().getBitmapFont().getData().setScale(getSmallTextScale());
        CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), getSmallText(), getAbsoluteX() + (getWidth() - getSmallTextWidth()) / 2, getAbsoluteY() + getHeight()/16*3);
        CNinjas.getObjectManager().getSpriteBatch().end();
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
        BitmapFont font = CNinjas.getObjectManager().getBitmapFont();
        font.getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), title);
        setTitleWidth(layout.width);
        font.getData().setScale(getTextScale());
        layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), text);
        setTextWidth(layout.width);
        font.getData().setScale(getSmallTextScale());
        layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), smallText);
        setSmallTextWidth(layout.width);
    }
}
