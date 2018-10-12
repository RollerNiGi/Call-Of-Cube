package com.rollernigi.game.util;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Constants {

    public static final float VIEWPORT_WIDTH = 5.0f;
    public static final float VIEWPORT_HEIGHT = 5.0f;

    public static final float VIEWPORT_GUI_WIDTH = 800f;
    public static final float VIEWPORT_GUI_HEIGHT= 480f;


    //纹理集描述文件路径
    public static final String TETURE_ATLAS_OBJECTS = "images/ver0.01.pack";

    public static final String Level_00 = "levels/level00.png";

    public static final int LIVES_START = 3;

    public static final float ITEM_JUMPBUFFER_POWERUP_DURATION = 6;
    public static final float TIME_DELAY_GAME_OVER = 3;

    public static final String TEXTURE_ATLAS_UI = "images/MenuScreen.pack";
    public static final String TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";
    public static final String SKIN_LIBGDX_UI = "images/uiskin.json";
    public static final String SKIN_COC_UI ="images/cocui.json";
    public static final String PREFERENCES = "coc.prefs";

    //FallBreak数量
    public static final int FALLBREAK_SPAWN_MAX = 100;
    //FallBreak的散布半径
    public static final float FALLBREAK_SPAWN_RADIUS = 3.5f;
    //游戏结束后的延时
    public static final float TIME_DELAY_GAME_FINISHED = 6;

}
