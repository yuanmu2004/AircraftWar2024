package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.aircraftwar2024.R;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private SoundPool soundPool = null;

    private Boolean soundOn;
    Map<Integer, Integer> soundPollMap;
    public SoundPlayer(Context context, boolean soundOn) {
        this.soundOn = soundOn;
        if (this.soundOn) {
            AudioAttributes audioAttributes = null;
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
            soundPollMap = new HashMap<>();
            soundPollMap.put(1, soundPool.load(context, R.raw.bomb_explosion, 1));
            soundPollMap.put(2, soundPool.load(context, R.raw.bullet_hit, 1));
            soundPollMap.put(3, soundPool.load(context, R.raw.game_over, 1));
            soundPollMap.put(4, soundPool.load(context, R.raw.get_supply, 1));
        }

    }
    public void playBombExplosion() {
        if (soundOn)
            soundPool.play((soundPollMap.get(1)), 0.7f, 0.7f,0,0, 1.2f);
    }
    public void playBulletHit() {
        if (soundOn)
            soundPool.play((soundPollMap.get(2)), 1, 1,0,0, 1.2f);
    }
    public void playGameOver() {
        if (soundOn)
            soundPool.play((soundPollMap.get(3)), 1, 1,0,0, 1.2f);
    }
    public void playGetSupply() {
        if (soundOn)
            soundPool.play((soundPollMap.get(4)), 1, 1,0,0, 1.2f);
    }

    public void shutUp() {
        soundPool.release();
    }


}

