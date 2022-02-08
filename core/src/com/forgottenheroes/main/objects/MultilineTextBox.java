package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.forgottenheroes.main.FHeroes;

public class MultilineTextBox extends TextBox{
    private ArrayList<Text> textList;

    public MultilineTextBox(){
        super();
        textList = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        FHeroes.getObjectManager().getShapeRenderer().begin();

        if(isBgEnabled()){
            FHeroes.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
            FHeroes.getObjectManager().getShapeRenderer().setColor(DEFAULT_BG_COLOR);
            if(isFlexWidth() == true){
                int maxWidth = getMaxTextWidth();
                if(maxWidth == 0){
                    setWidth(WORLDWIDTH/32*1);
                }
                else{
                    setWidth(maxWidth + LINESPACING);
                }
                setRelativeX((WORLDWIDTH - getWidth()) / 2);
            }
            FHeroes.getObjectManager().getShapeRenderer().rect(getRelativeX(), getRelativeY(), getWidth(), getHeight());
        }
        
        FHeroes.getObjectManager().getShapeRenderer().end();
        FHeroes.getObjectManager().getSpriteBatch().begin();
        for(int i = 0; i < textList.size(); i++){
            Text line = textList.get(i);
            FHeroes.getObjectManager().getBitmapFont().setColor(line.getColor());
            FHeroes.getObjectManager().getBitmapFont().getData().setScale(line.getFontSize());
            FHeroes.getObjectManager().getBitmapFont().draw(FHeroes.getObjectManager().getSpriteBatch(), 
                line.getText(), getRelativeX() + (getWidth() - line.getWidth()) / 2, line.getRelativeY());
        }
        FHeroes.getObjectManager().getSpriteBatch().end();
    }

    @Override
    public void addTextLine(String text, float fontSize, Color color){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(fontSize);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        setHeight(getHeight() + fontHeight + LINESPACING);
        Text line = new Text(text, fontSize, color);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setRelativeY(getRelativeY() + fontHeight + LINESPACING);
        for(int i = 0; i < textList.size(); i++){
            Text currentLine = textList.get(i);
            currentLine.setRelativeY(currentLine.getRelativeY() + fontHeight + LINESPACING);
        }
        textList.add(line);
    }

    @Override
    public void addTextLine(String text, float fontSize){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(fontSize);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        setHeight(getHeight() + fontHeight + LINESPACING);
        Text line = new Text(text, fontSize, DEFAULT_TEXT_COLOR);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setRelativeY(getRelativeY() + fontHeight + LINESPACING);
        for(int i = 0; i < textList.size(); i++){
            Text currentLine = textList.get(i);
            currentLine.setRelativeY(currentLine.getRelativeY() + fontHeight + LINESPACING);
        }
        textList.add(line);
    }

    @Override
    public void addTextLine(String text){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        font.getData().setScale(DEFAULT_FONT_SIZE);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        setHeight(getHeight() + fontHeight + LINESPACING);
        Text line = new Text(text, DEFAULT_FONT_SIZE, Color.WHITE);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setRelativeY(getRelativeY() + fontHeight + LINESPACING);
        for(int i = 0; i < textList.size(); i++){
            Text currentLine = textList.get(i);
            currentLine.setRelativeY(currentLine.getRelativeY() + fontHeight + LINESPACING);
        }
        textList.add(line);
    }

    public void updateText(String text, int lineNumber){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        Text line = textList.get(lineNumber);
        line.setText(text);
        font.getData().setScale(line.getFontSize());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), line.getText());
        line.setWidth((int)layout.width);
        textList.set(lineNumber, line);
    }

    public void updateText(String text, Color color, int lineNumber){
        BitmapFont font = FHeroes.getObjectManager().getBitmapFont();
        Text line = textList.get(lineNumber);
        line.setText(text);
        line.setColor(color);
        font.getData().setScale(line.getFontSize());
        GlyphLayout layout = new GlyphLayout(FHeroes.getObjectManager().getBitmapFont(), line.getText());
        line.setWidth((int)layout.width);
        textList.set(lineNumber, line);
    }

    public int getMaxTextWidth(){
        int maxWidth = 0;
        for(int i = 0; i < textList.size(); i++){
            Text line = textList.get(i);
            if(line.getWidth() > maxWidth){
                maxWidth = line.getWidth();
            }
        }
        return maxWidth;
    }
}
