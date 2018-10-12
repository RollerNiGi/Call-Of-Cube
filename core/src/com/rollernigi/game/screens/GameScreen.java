package com.rollernigi.game.screens;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rollernigi.game.BasicClass.Assets;
import com.rollernigi.game.BasicClass.Level;
import com.rollernigi.game.BasicClass.WorldController;
import com.rollernigi.game.BasicClass.WorldRenderer;
import com.rollernigi.game.screens.transitions.DirectedGame;
import com.rollernigi.game.screens.transitions.ScreenTransition;
import com.rollernigi.game.screens.transitions.ScreenTransitionFade;
import com.rollernigi.game.util.Constants;
import com.rollernigi.game.util.GamePerferences;

public class GameScreen extends AbstractGameScreen {
    private static final String TAG =GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;

    private Stage stage;
    private Skin skinCOC;

    private Button stopButton;

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
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
        worldRenderer.resize(width,height);
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        GamePerferences prefs = GamePerferences.instance;
        prefs.load();
        worldController.changeLevel(prefs.currentLevel);
        worldRenderer = new WorldRenderer((worldController));
        Gdx.input.setCatchBackKey(true);
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT));
        skinCOC=new Skin(Gdx.files.internal(Constants.SKIN_COC_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        stopButton = new Button(skinCOC,"StopButton");
        stopButton.setWidth(127);
        stopButton.setHeight(38);
        stopButton.setPosition(643,380);
        stopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoSelectStageScreen();
            }
        });
        stage.addActor(stopButton);
    }

    @Override
    public void hide() {
        worldController.dispose();
        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
        stage.dispose();
        skinCOC.dispose();
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
        return stage;
    }


    private void gotoSelectStageScreen(){
        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        game.setScreen(new SelectStageScreen(game),transition);
    }





}
