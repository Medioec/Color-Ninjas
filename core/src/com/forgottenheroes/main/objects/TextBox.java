package com.forgottenheroes.main.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class TextBox extends GameObject{

    private Text text;
    private boolean flexWidth;
    private boolean bgEnabled;

    public final int WORLDWIDTH = (int)FHeroes.getObjectManager().getMainMenuScreen().getMENUWIDTH();
    public final int WORLDHEIGHT = (int)FHeroes.getObjectManager().getMainMenuScreen().getMENUHEIGHT();
    public static final int LINESPACING = (int)FHeroes.getObjectManager().getMainMenuScreen().getMENUHEIGHT()/32*1;
    public static final int DEFAULT_FONT_SIZE = 1;
    public static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
    public static final Color DEFAULT_BG_COLOR = new Color(.2f, .2f, .4f, 1f);

    public TextBox(){
        super();
        flexWidth = false;
        bgEnabled = true;
        setWidth(WORLDWIDTH);
        setHeight(LINESPACING);
        setRelativeX((WORLDWIDTH - getWidth()) / 2);
        setRelativeY(WORLDHEIGHT - getHeight() / 2);
        FHeroes.getObjectManager().addToObjectList(this);
    }

    public TextBox(String string){
        super();
        flexWidth = false;
        bgEnabled = true;
        addTextLine(string);
        setWidth(WORLDWIDTH/16*13);
        setHeight(LINESPACING);
        setRelativeX((WORLDWIDTH - getWidth()) / 2);
        setRelativeY(WORLDHEIGHT - getHeight() / 2);
        FHeroes.getObjectManager().addToObjectList(this);
    }

    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();

        if(bgEnabled){
            FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
            FHeroes.getObjectManager().getShapeRenderer().setColor(DEFAULT_BG_COLOR);
            if(flexWidth == true){
                int width = text.getWidth();
                if(width == 0){
                    setWidth(WORLDWIDTH/32*1);
                }
                else{
                    setWidth(width + LINESPACING);
                }
                setRelativeX((WORLDWIDTH - getWidth()) / 2);
            }
            FHeroes.getObjectManager().getShapeRenderer().rect(getRelativeX(), getRelativeY(), getWidth(), getHeight());
        }
        
        FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getSpriteBatch().begin();
        FHeroes.getObjectManager().getBitmapFont().setColor(text.getColor());
        FHeroes.getObjectManager().getBitmapFont().getData().setScale(text.getFontSize());
        FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), 
            text.getText(), getRelativeX() + (getWidth() - text.getWidth()) / 2, text.getRelativeY());
        FHeroes.getObjectManager().getSpriteBatch().end();
    }

    public boolean isFlexWidth() {
        return flexWidth;
    }

    public void setFlexWidth(boolean flexWidth) {
        this.flexWidth = flexWidth;
    }

    public boolean isBgEnabled() {
        return bgEnabled;
    }

    public void setBgEnabled(boolean bgEnabled) {
        this.bgEnabled = bgEnabled;
    }

    public void addTextLine(String text, float fontSize, Color color){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(fontSize);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        if(this.text == null){
            setHeight(getHeight() + fontHeight + LINESPACING);
        }
        Text line = new Text(text, fontSize, color);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setRelativeY(getRelativeY() + fontHeight + LINESPACING);
        this.text = line;
    }

    public void addTextLine(String text, float fontSize){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(fontSize);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        if(this.text == null){
            setHeight(getHeight() + fontHeight + LINESPACING);
        }
        Text line = new Text(text, fontSize, DEFAULT_TEXT_COLOR);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setRelativeY(getRelativeY() + fontHeight + LINESPACING);
        this.text = line;
    }

    public void addTextLine(String text){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(DEFAULT_FONT_SIZE);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        if(this.text == null){
            setHeight(getHeight() + fontHeight + LINESPACING);
        }
        Text line = new Text(text, DEFAULT_FONT_SIZE, DEFAULT_TEXT_COLOR);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setRelativeY(getRelativeY() + fontHeight + LINESPACING);
        this.text = line;
    }

    public void updateText(String text){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        this.text.setText(text);
        font.getData().setScale(this.text.getFontSize());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), this.text.getText());
        this.text.setWidth((int)layout.width);
    }
    public void updateText(String text, Color color){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        this.text.setText(text);
        this.text.setColor(color);
        font.getData().setScale(this.text.getFontSize());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), this.text.getText());
        this.text.setWidth((int)layout.width);
    }
}
