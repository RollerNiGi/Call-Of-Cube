package com.rollernigi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rollernigi.game.BasicClass.Assets;
import com.rollernigi.game.BasicClass.WorldController;
import com.rollernigi.game.BasicClass.WorldRenderer;
import com.rollernigi.game.screens.transitions.DirectedGame;
import com.rollernigi.game.screens.transitions.ScreenTransitionSlide;
import com.rollernigi.game.util.AudioMangager;
import com.rollernigi.game.util.Constants;
import com.rollernigi.game.screens.transitions.ScreenTransition;
import com.rollernigi.game.util.GamePerferences;
import com.rollernigi.game.screens.transitions.ScreenTransitionFade;

public class MenuScreen extends AbstractGameScreen{
    private static final String TAG = MenuScreen.class.getName();

    private Stage stage;
    private Skin skinCOC;

    //菜单
    private Image imgBackground;
    private Image imgJumper;
    private Button btnMenuPlay;
    private Button btnMenuOptions;
    private Button btnMenuOptions2;
    private Button btnMenuOptions3;
    private Button btnMenuOptions4;

    private boolean ssound=true,smusic=true;

    private boolean paused;

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private void loadSettings(){
        GamePerferences prefs = GamePerferences.instance;
        prefs.load();
        if(prefs.music==true){
            smusic=true;
            btnMenuOptions4 = new Button(skinCOC,"MusicButton");
        }else if(prefs.music==false){
            smusic=false;
            btnMenuOptions4 = new Button(skinCOC,"MusicButton2");
        }
        if(prefs.sound==true){
            ssound=true;
            btnMenuOptions3 = new Button(skinCOC,"SoundButton");
        }else if(prefs.sound==false){
            ssound=false;
            btnMenuOptions3 = new Button(skinCOC,"SoundButton2");
        }
       refresh();
    }


    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.9f,0.9f,0.9f,0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!paused){
            worldController.update(deltaTime);
        }worldRenderer.render();


        stage.act(deltaTime);
        stage.draw();

    }

    private void rebuildStage(){
        worldController.setPlayLoseLiveSound(false);
        worldController.changeLevel("999");
        worldRenderer.setHideGUI();
        worldController.setInputAble();
        skinCOC=new Skin(Gdx.files.internal(Constants.SKIN_COC_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        loadSettings();

    }

    private Table buildBackgourndLayer() {
        Table layer = new Table();
        imgBackground=new Image(skinCOC,"background");
        layer.right();
        layer.add(imgBackground);
        return layer;
    }
    //TODO:Add text for buttons
    private Table buildControlLayer() {
        Table layer = new Table();
        layer.right();
        //开始按钮
        btnMenuPlay = new Button(skinCOC,"StartButton");
        layer.add(btnMenuPlay);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();
        //设置按钮
        btnMenuOptions = new Button(skinCOC,"defaultButton");
        layer.add(btnMenuOptions);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });
        layer.row();
        btnMenuOptions2 = new Button(skinCOC,"defaultButton");
        layer.add(btnMenuOptions2);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });
        layer.row();
        layer.add(btnMenuOptions3);
        btnMenuOptions3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ssound=!ssound;
                GamePerferences prers = GamePerferences.instance;
                prers.sound=ssound;
                prers.save();
                AudioMangager.instance.onSettingUpdate();
                loadSettings();
            }
        });
        layer.row();
        layer.add(btnMenuOptions4);
        btnMenuOptions4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                smusic=!smusic;
                GamePerferences prers = GamePerferences.instance;
                prers.music=smusic;
                prers.save();
                AudioMangager.instance.onSettingUpdate();
                loadSettings();
            }
        });

        return  layer;
    }

    private void  onPlayClicked(){
        gotoGameScreen();
    }

    private void onOptionsClicked(){

    }



    private Table buildLogoLayer() {
        Table layer = new Table();
        return  layer;
    }
    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width,height);
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void show() {

        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer((worldController));
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT));
        rebuildStage();
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        stage.dispose();
        skinCOC.dispose();
    }

    @Override
    public void pause() {
        super.resume();
        paused=false;
    }
    @Override
    public InputProcessor getInputProcessor(){
        return stage;
    }

    private void gotoGameScreen(){
        ScreenTransition transition = ScreenTransitionSlide.init(0.75f,ScreenTransitionSlide.LEFT,false,Interpolation.bounceOut);
        game.setScreen(new SelectStageScreen(game),transition);
    }

    private void refresh(){
        Table layerBackground = buildBackgourndLayer();
        Table layerLogo = buildLogoLayer();
        Table layerControls = buildControlLayer();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(800f,480f);
        stack.add(layerBackground);
        stack.add(layerLogo);
        stack.add(layerControls);
    }

}
