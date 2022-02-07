package com.forgottenheroes.main.objects;


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
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), title);
        setTitleWidth(layout.width);
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(getTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), text);
        setTextWidth(layout.width);
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(getSmallTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), smallText);
        setSmallTextWidth(layout.width);
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();
        updateTextWidth();
        FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
        FHeroes.getObjectManager().getShapeRenderer().setColor(new Color(.2f, .2f, .4f, 1f));
        FHeroes.getObjectManager().getShapeRenderer().rect(getX(), getY(), getWidth(), getHeight());
        FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getSpriteBatch().begin();
        FHeroes.getObjectManager().getBitmapFont().setColor(new Color(1,1,1,1));
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(getTitleScale());
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), getTitle(), getX() + (getWidth() - getTitleWidth()) / 2, getY() + getHeight()/16*12);
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(getTextScale());
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), getText(), getX() + (getWidth() - getTextWidth()) / 2, getY() + getTextYOffset() + getHeight()/16*6);
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(getSmallTextScale());
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), getSmallText(), getX() + (getWidth() - getSmallTextWidth()) / 2, getY() + getHeight()/16*3);
        FHeroes.getObjectManager().getSpriteBatch().end();
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
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(getTitleScale());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), title);
        setTitleWidth(layout.width);
        font.getData().setScale(getTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), text);
        setTextWidth(layout.width);
        font.getData().setScale(getSmallTextScale());
        layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), smallText);
        setSmallTextWidth(layout.width);
    }
}
