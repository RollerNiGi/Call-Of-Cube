package com.rollernigi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rollernigi.game.BasicClass.Assets;
import com.rollernigi.game.screens.transitions.DirectedGame;
import com.rollernigi.game.screens.transitions.ScreenTransitionSlide;
import com.rollernigi.game.util.Constants;
import com.rollernigi.game.screens.transitions.ScreenTransition;
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


    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f,0.5f,1.0f,0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        stage.act(delta);
        stage.draw();

    }

    private void rebuildStage(){
        skinCOC=new Skin(Gdx.files.internal(Constants.SKIN_COC_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

        Table layerBackground = buildBackgourndLayer();
        Table layerLogo = buildLogoLayer();
        Table layerControls = buildControlLayer();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HTIGHT);
        stack.add(layerBackground);
        stack.add(layerLogo);
        stack.add(layerControls);
    }

    private Table buildControlLayer() {
        Table layer = new Table();
        layer.right().bottom();

        //开始按钮
        btnMenuPlay = new Button(skinCOC,"play");
        layer.add(btnMenuPlay);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });

        layer.row();

        //设置按钮
        btnMenuOptions = new Button(skinCOC,"settings");
        layer.add(btnMenuOptions);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });

        return  layer;
    }

    private void  onPlayClicked(){
        gotoGameScreen();
    }

    private void onOptionsClicked(){

    }

    private Table buildBackgourndLayer() {
        Table layer = new Table();

        return layer;
    }

    private Table buildLogoLayer() {
        Table layer = new Table();
        return  layer;
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HTIGHT));
        rebuildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
        skinCOC.dispose();
    }

    @Override
    public void pause() {

    }
    @Override
    public InputProcessor getInputProcessor(){
        return stage;
    }

    private void gotoGameScreen(){
        ScreenTransition transition = ScreenTransitionSlide.init(0.75f,ScreenTransitionSlide.DOWN,false,Interpolation.bounceOut);
        game.setScreen(new GameScreen(game),transition);
    }
}
