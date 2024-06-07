package com.example.aircraftwar2024.music;


import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;

//import com.example.aircraftwar2024.R;

public class BgmPlayer {
    private MediaPlayer bgmPlayer = null;
    private MediaPlayer bgmBossPlayer = null;
    private boolean soundOn;
    private boolean bgmActive = false;
    private boolean bgmBossActive = false;

    public BgmPlayer(Context context, boolean soundOn) {
        this.soundOn = soundOn;
        if (soundOn) {
            this.bgmPlayer = MediaPlayer.create(context, R.raw.bgm);
            bgmPlayer.setLooping(true);
//            this.bgMP.setVolume(1, 1);
            this.bgmBossPlayer = MediaPlayer.create(context, R.raw.bgm_boss);
            bgmBossPlayer.setLooping(true);
//            this.bossBgMP.setVolume(1, 1);
        }

    }
    public void bgmStart(){
        if (soundOn) {
            if (bgmActive) {
                bgmPlayer.seekTo(0);
                bgmPlayer.start();
            }
            else {
                bgmPlayer.start();
                bgmActive = true;
            }
//            bgMP.setLooping(true);
        }

    }
    public void bgmEnd(){
        if (soundOn) {
            bgmPlayer.pause();
        }
    }
    public void bgmBossStart(){
        if (soundOn) {
//            bossBgMP.seekTo(0);
            if (bgmBossActive) {
                bgmBossPlayer.seekTo(0);
                bgmBossPlayer.start();
            }
            else {
                bgmBossPlayer.start();
                bgmBossActive = true;
            }
//            bossBgMP.setLooping(true);
        }

    }
    public void bgmBossEnd(){
        if (soundOn) {
            bgmBossPlayer.pause();
        }
    }

    public void toBossBgm(){
        this.bgmEnd();
        this.bgmBossStart();
    }

    public void toNormalBgm(){
        this.bgmBossEnd();
        this.bgmStart();
    }

    public void shutUp() {
        if (soundOn) {
            bgmPlayer.stop();
            bgmPlayer.release();
            bgmPlayer = null;

            bgmBossPlayer.stop();
            bgmBossPlayer.release();
            bgmBossPlayer = null;
        }

    }



}
