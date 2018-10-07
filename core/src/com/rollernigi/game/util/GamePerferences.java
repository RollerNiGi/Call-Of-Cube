package com.rollernigi.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.rollernigi.game.util.Constants;

import java.security.Permission;

public class GamePerferences
{
    public static final GamePerferences instance = new GamePerferences();

    public boolean sound;
    public boolean music;
    public String currentLevel;

    private Preferences prers;

    private GamePerferences(){
        prers = Gdx.app.getPreferences(Constants.PREFERENCES);
    }
    public void load(){
        sound = prers.getBoolean("sound",true);
        music = prers.getBoolean("music",true);
        currentLevel = prers.getString("currentLevel","999");
    }

    public void save(){
        prers.putBoolean("sound",sound);
        prers.putBoolean("music",music);
        prers.putString("currentLevel",currentLevel);
        prers.flush();
    }
}
