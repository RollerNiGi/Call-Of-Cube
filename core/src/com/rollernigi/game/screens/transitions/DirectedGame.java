package com.rollernigi.game.screens.transitions;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.rollernigi.game.screens.AbstractGameScreen;

public abstract class DirectedGame implements ApplicationListener {
    private boolean init;
    private AbstractGameScreen currentScreen;
    private AbstractGameScreen nextScreen;
    private FrameBuffer currFbo;
    private FrameBuffer nextFbo;
    private SpriteBatch batch;
    private float t;
    private ScreenTransition screenTransition;

    public void setScreen(AbstractGameScreen screen){
        setScreen(screen, null);
    }

    public void setScreen(AbstractGameScreen screen,ScreenTransition screenTransition){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        if(!init){
            currFbo = new FrameBuffer(Pixmap.Format.RGB888,w,h,false);
            nextFbo = new FrameBuffer(Pixmap.Format.RGB888,w,h,false);
            batch = new SpriteBatch();
            init = true;
        }
        //启动一个新的切换
        nextScreen = screen;
        nextScreen.show();
        nextScreen.resize(w,h);
        nextScreen.render(0);
        if(currentScreen!=null)currentScreen.pause();
        nextScreen.pause();
        Gdx.input.setInputProcessor(null);
        this.screenTransition = screenTransition;
        t=0;
    }
    @Override
    public void render(){
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(),1.0f/60.0f);
        if(nextScreen==null){
            if(currentScreen!=null){
                currentScreen.render(deltaTime);
            }
        }else {
            float duration=0;
            if(screenTransition!=null){
                duration=screenTransition.getDuration();
            }
            t=Math.min(t+deltaTime,duration);
            if(screenTransition==null||t>=duration){
                if(currentScreen!=null)currentScreen.hide();
                nextScreen.resume();
                Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
                currentScreen=nextScreen;
                nextScreen=null;
                screenTransition=null;
            }else {
                currFbo.begin();
                if(currentScreen!=null)currentScreen.render(deltaTime);
                currFbo.end();
                nextFbo.begin();
                nextScreen.render(deltaTime);
                nextFbo.end();
                float alpha = t/duration;
                screenTransition.render(batch,currFbo.getColorBufferTexture(),nextFbo.getColorBufferTexture(),alpha);
            }
        }
    }
    @Override
    public void resize(int width,int height){
        if(currentScreen!=null)currentScreen.resize(width,height);
        if(nextScreen != null)nextScreen.resize(width,height);
    }

    @Override
    public void pause(){
        if(currentScreen!=null)currentScreen.pause();
    }

    @Override
    public void resume(){
        if(currentScreen!=null)currentScreen.resume();
    }

    @Override
    public void dispose(){
        if(currentScreen!=null)currentScreen.hide();
        if(nextScreen!=null)nextScreen.hide();
        if(init){
            currFbo.dispose();
            currentScreen = null;
            nextFbo.dispose();
            nextScreen = null;
            batch.dispose();
            init=false;
        }
    }
}
