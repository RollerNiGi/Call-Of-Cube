package com.rollernigi.game.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioMangager {
    public static final AudioMangager instance = new AudioMangager();

    private Music playingMusic;

    private AudioMangager(){

    }

    public void play(Sound sound){
        play(sound,1);
    }

    public void play(Sound sound,float volume){
        play(sound,volume,1);
    }
    public void play(Sound sound,float volume,float pitch){
        play(sound,volume,pitch,0);
    }
    public void play(Sound sound,float volume,float pitch,float pan){
        if(!GamePerferences.instance.sound) return;
        sound.play(30*volume,pitch,pan);
    }

    public void play(Music music){
        stopMusic();
        playingMusic=music;
        if(GamePerferences.instance.music){
            music.setLooping(true);
            music.setVolume(20);
            music.play();
        }
    }

    private void stopMusic() {
        if(playingMusic!=null)playingMusic.stop();
    }

    public Music getPlayingMusic(){
        return playingMusic;
    }

    public void onSettingUpdate(){
        if(playingMusic==null)return;
        playingMusic.setVolume(20);
        if(GamePerferences.instance.music){
            if(!playingMusic.isPlaying())playingMusic.play();
        }else {
            playingMusic.pause();
        }
    }
}
