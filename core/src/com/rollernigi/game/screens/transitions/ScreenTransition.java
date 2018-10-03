package com.rollernigi.game.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenTransition {
    public float getDuration();
    public void render (SpriteBatch batch,Texture currScreen,Texture nextScreen,float alpha);
}
