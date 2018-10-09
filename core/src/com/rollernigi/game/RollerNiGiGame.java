package com.rollernigi.game;


import com.badlogic.gdx.assets.AssetManager;
import com.rollernigi.game.BasicClass.Assets;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.rollernigi.game.screens.transitions.DirectedGame;
import com.rollernigi.game.screens.MenuScreen;
import com.rollernigi.game.util.AudioMangager;
import com.rollernigi.game.util.GamePerferences;

public class RollerNiGiGame extends DirectedGame{



    @Override
    public void create() {

        //将日志级别设置为调试模式
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //加载资源
        Assets.instance.init(new AssetManager());
        //加载音频并开始播放音乐
        GamePerferences.instance.load();
        AudioMangager.instance.play(Assets.instance.music.song01);
        //启动游戏菜单屏幕
        setScreen(new MenuScreen(this));
    }

}
