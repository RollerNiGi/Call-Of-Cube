package com.rollernigi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.rollernigi.game.BasicClass.Assets;
import com.rollernigi.game.objects.AbstractGameObject;
import com.rollernigi.game.screens.transitions.DirectedGame;

public abstract class AbstractGameScreen implements Screen {
        protected DirectedGame game;

        public AbstractGameScreen(DirectedGame game){
                this.game=game;
        }

        public abstract void render(float delta);
        public abstract void resize(int width,int height);
        public abstract void show();
        public abstract void hide();
        public abstract void pause();
        public abstract InputProcessor getInputProcessor();

        public void resume(){
                Assets.instance.init(new AssetManager());
        }

        public void dispose(){
                Assets.instance.dispose();
        }
}
