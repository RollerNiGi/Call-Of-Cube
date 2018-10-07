package com.rollernigi.game.screens;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rollernigi.game.BasicClass.Assets;
import com.rollernigi.game.BasicClass.Level;
import com.rollernigi.game.BasicClass.WorldController;
import com.rollernigi.game.BasicClass.WorldRenderer;
import com.rollernigi.game.screens.transitions.DirectedGame;

public class GameScreen extends AbstractGameScreen {
    private static final String TAG =GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;

    public GameScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        //如果暂停，则不更新游戏
        if(!paused){
            worldController.update(deltaTime);
        }
        Gdx.gl.glClearColor(0.9f,0.9f,0.9f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width,height);
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer((worldController));
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume(){
        super.resume();
        paused=false;
    }
    @Override
    public InputProcessor getInputProcessor(){
        return worldController;
    }
}
