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
import com.rollernigi.game.BasicClass.WorldController;
import com.rollernigi.game.BasicClass.WorldRenderer;
import com.rollernigi.game.screens.transitions.DirectedGame;
import com.rollernigi.game.screens.transitions.ScreenTransitionSlide;
import com.rollernigi.game.util.Constants;
import com.rollernigi.game.screens.transitions.ScreenTransition;
import com.rollernigi.game.screens.transitions.ScreenTransitionFade;
import com.rollernigi.game.util.GamePerferences;

public class SelectStageScreen extends AbstractGameScreen{
    private static final String TAG = MenuScreen.class.getName();

    private Stage stage;
    private Skin skinCOC;


    public String selectedLevel="00";

    //菜单
    private Image imgBackground;
    private Image imgJumper;
    private Button btnMenuPlay;
    private Button btnMenuPlay2;
    private Button btnMenuPlay3;
    private Button btnMenuPlay4;
    private Button btnMenuPlay5;
    private Button btnMenuPlay6;
    private Button btnMenuPlay7;
    private Button btnMenuPlay8;
    private Button btnMenuPlay9;
    private Button btnMenuPlay10;
    private Button btnMenuPlay11;
    private Button btnMenuPlay12;
    private Button btnMenuPlay13;
    private Button btnMenuPlay14;
    private Button btnMenuPlay15;
    private Button btnMenuPlay16;
    private Button btnMenuOptions;

    //TODO:Add more levels
    public SelectStageScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f,0.9f,0.9f,0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        stage.act(delta);
        stage.draw();

    }

    private void rebuildStage(){
        skinCOC=new Skin(Gdx.files.internal(Constants.SKIN_COC_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

        Table layerBackground = buildBackgourndLayer();
        Table layerLogo = buildLogoLayer();
        Table layerControls1 = buildControlLayer1();
        Table layerControls2 = buildControlLayer2();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerBackground);
        stack.add(layerLogo);
        stack.add(layerControls1);
        stack.add(layerControls2);
    }

    private Table buildControlLayer1() {
        Table layer = new Table();

        //开始按钮
        btnMenuPlay = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("00");
            }
        });

        //开始按钮
        btnMenuPlay2 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay2);
        btnMenuPlay2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("00");
            }
        });

        //开始按钮
        btnMenuPlay3 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay3);
        btnMenuPlay3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("00");
            }
        });

        //开始按钮
        btnMenuPlay4 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay4);
        btnMenuPlay4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("00");
            }
        });
        btnMenuPlay5 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay5);
        btnMenuPlay5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay6 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay6);
        btnMenuPlay6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay7 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay7);
        btnMenuPlay7.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay8 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay8);
        btnMenuPlay8.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        layer.row();
        //开始按钮
        btnMenuPlay9 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay9);
        btnMenuPlay9.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });

        //开始按钮
        btnMenuPlay10 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay10);
        btnMenuPlay10.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });

        //开始按钮
        btnMenuPlay11 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay11);
        btnMenuPlay11.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });

        //开始按钮
        btnMenuPlay12 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay12);
        btnMenuPlay12.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay13 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay13);
        btnMenuPlay13.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay14 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay14);
        btnMenuPlay14.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay15 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay15);
        btnMenuPlay15.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });
        btnMenuPlay16 = new Button(skinCOC,"BlockButton");
        layer.add(btnMenuPlay16);
        btnMenuPlay16.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked("03");
            }
        });



        return  layer;
    }

    private Table buildControlLayer2() {
        Table layer = new Table();
        layer.left().top();

        //设置按钮
        btnMenuOptions = new Button(skinCOC,"BackButton");
        layer.add(btnMenuOptions);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });

        return  layer;
    }

    private void  onPlayClicked(String level){
        GamePerferences prefs = GamePerferences.instance;
        prefs.currentLevel=level;
        prefs.save();
        prefs.load();
        Gdx.app.debug(TAG,"#CurrentLevel"+prefs.currentLevel);
        gotoGameScreen();
    }

    private void onOptionsClicked(){
        backToMenuScreen();
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
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT));
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
        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        game.setScreen(new  GameScreen(game),transition);
    }

    private void backToMenuScreen(){
        ScreenTransition transition = ScreenTransitionSlide.init(0.75f,ScreenTransitionSlide.RIGHT,false,Interpolation.bounceOut);
        game.setScreen(new MenuScreen(game),transition);
    }

}
