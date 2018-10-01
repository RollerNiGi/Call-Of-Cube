package com.rollernigi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.rollernigi.game.BasicClass.Assets;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.rollernigi.game.screens.MenuScreen;

public class RollerNiGiGame extends Game{



    @Override
    public void create() {

        //将日志级别设置为调试模式
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //加载资源
        Assets.instance.init(new AssetManager());
        //启动游戏菜单屏幕
        setScreen(new MenuScreen(this));
    }

}
