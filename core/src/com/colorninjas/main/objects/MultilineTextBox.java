package com.colorninjas.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.colorninjas.main.CNinjas;

public class MultilineTextBox extends TextBox{
    private ArrayList<Text> textList;

    public MultilineTextBox(){
        super();
        textList = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        CNinjas.getObjectManager().getShapeRenderer().begin();
        Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        if(isBgEnabled()){
            CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
            CNinjas.getObjectManager().getShapeRenderer().setColor(DEFAULT_BG_COLOR);
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
            CNinjas.getObjectManager().getShapeRenderer().rect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        }
        
        CNinjas.getObjectManager().getShapeRenderer().end();
        CNinjas.getObjectManager().getSpriteBatch().begin();
        for(int i = 0; i < textList.size(); i++){
            Text line = textList.get(i);
            CNinjas.getObjectManager().getBitmapFont().setColor(line.getColor());
            CNinjas.getObjectManager().getBitmapFont().getData().setScale(line.getFontSize());
            CNinjas.getObjectManager().getBitmapFont().draw(CNinjas.getObjectManager().getSpriteBatch(), 
                line.getText(), getAbsoluteX() + (getWidth() - line.getWidth()) / 2, line.getAbsoluteY());
        }
        CNinjas.getObjectManager().getSpriteBatch().end();
    }

    @Override
    public void addTextLine(String text, float fontSize, Color color){
        BitmapFont font = CNinjas.getObjectManager().getBitmapFont();
        font.getData().setScale(fontSize);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        setHeight(getHeight() + fontHeight + LINESPACING);
        Text line = new Text(text, fontSize, color);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setAbsoluteY(getAbsoluteY() + fontHeight + LINESPACING);
        for(int i = 0; i < textList.size(); i++){
            Text currentLine = textList.get(i);
            currentLine.setAbsoluteY(currentLine.getAbsoluteY() + fontHeight + LINESPACING);
        }
        textList.add(line);
    }

    @Override
    public void addTextLine(String text, float fontSize){
        BitmapFont font = CNinjas.getObjectManager().getBitmapFont();
        font.getData().setScale(fontSize);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        setHeight(getHeight() + fontHeight + LINESPACING);
        Text line = new Text(text, fontSize, DEFAULT_TEXT_COLOR);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setAbsoluteY(getAbsoluteY() + fontHeight + LINESPACING);
        for(int i = 0; i < textList.size(); i++){
            Text currentLine = textList.get(i);
            currentLine.setAbsoluteY(currentLine.getAbsoluteY() + fontHeight + LINESPACING);
        }
        textList.add(line);
    }

    @Override
    public void addTextLine(String text){
        BitmapFont font = CNinjas.getObjectManager().getBitmapFont();
        font.getData().setScale(DEFAULT_FONT_SIZE);
        GlyphLayout layout = new GlyphLayout(font, text);
        int fontHeight = (int)layout.height;
        int fontWidth = (int)layout.width;
        setHeight(getHeight() + fontHeight + LINESPACING);
        Text line = new Text(text, DEFAULT_FONT_SIZE, Color.WHITE);
        line.setWidth(fontWidth);
        line.setHeight(fontHeight);
        line.setAbsoluteY(getAbsoluteY() + fontHeight + LINESPACING);
        for(int i = 0; i < textList.size(); i++){
            Text currentLine = textList.get(i);
            currentLine.setAbsoluteY(currentLine.getAbsoluteY() + fontHeight + LINESPACING);
        }
        textList.add(line);
    }

    public void updateText(String text, int lineNumber){
        BitmapFont font = CNinjas.getObjectManager().getBitmapFont();
        Text line = textList.get(lineNumber);
        line.setText(text);
        font.getData().setScale(line.getFontSize());
        GlyphLayout layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), line.getText());
        line.setWidth((int)layout.width);
        textList.set(lineNumber, line);
    }

    public void updateText(String text, Color color, int lineNumber){
        BitmapFont font = CNinjas.getObjectManager().getBitmapFont();
        Text line = textList.get(lineNumber);
        line.setText(text);
        line.setColor(color);
        font.getData().setScale(line.getFontSize());
        GlyphLayout layout = new GlyphLayout(CNinjas.getObjectManager().getBitmapFont(), line.getText());
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
