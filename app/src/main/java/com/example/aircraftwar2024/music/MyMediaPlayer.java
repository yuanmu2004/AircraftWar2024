package com.example.aircraftwar2024.music;


import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;

//import com.example.aircraftwar2024.R;

public class MyMediaPlayer {
    private MediaPlayer bgMP = null;
    private MediaPlayer bossBgMP = null;
    private boolean soundOn;

    public MyMediaPlayer(Context context, boolean soundOn) {
        this.soundOn = soundOn;
        if (soundOn) {
            this.bgMP = MediaPlayer.create(context, R.raw.bgm);
            this.bossBgMP = MediaPlayer.create(context, R.raw.bgm);
        }

    }
    public void play_bgm(){
        if (soundOn) {
            bgMP.start();
            bgMP.setLooping(true);
        }

    }
    public void stop_bgm(){
        if (soundOn) {
            bgMP.stop();
        }
    }
    public void play_boss_bgm(){
        if (soundOn) {
            bossBgMP.start();
            bossBgMP.setLooping(true);
        }

    }
    public void stop_boss_bgm(){
        if (soundOn) {
            bossBgMP.stop();
        }
    }

    public void stop_bgm_and_play_bossBgm(){
        this.stop_bgm();
        this.play_boss_bgm();
    }

    public void stop_bossBgm_and_play_bgm(){
        this.stop_boss_bgm();
        this.play_bgm();
    }

    public void shutup() {
        if (soundOn) {
            bgMP.stop();
            bgMP.release();
            bgMP = null;

            bossBgMP.stop();
            bossBgMP.release();
            bossBgMP = null;
        }

    }



}
