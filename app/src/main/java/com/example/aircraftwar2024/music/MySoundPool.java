package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.aircraftwar2024.R;

import java.util.HashMap;
import java.util.Map;

public class MySoundPool {
    private SoundPool mysp = null;

    private Boolean soundOn;
    Map<Integer, Integer> soundPollMap;
    public MySoundPool(Context context, boolean soundOn) {
        this.soundOn = soundOn;
        if (this.soundOn) {
            AudioAttributes audioAttributes = null;
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mysp = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
            soundPollMap = new HashMap<>();
            soundPollMap.put(1, mysp.load(context, R.raw.bomb_explosion, 1));
            soundPollMap.put(2, mysp.load(context, R.raw.bullet_hit, 1));
            soundPollMap.put(3, mysp.load(context, R.raw.game_over, 1));
            soundPollMap.put(4, mysp.load(context, R.raw.get_supply, 1));
        }

    }
    public void play_bomb_explosion() {
        if (soundOn)
            mysp.play((soundPollMap.get(1)), 1, 1,0,0, 1.2f);
    }
    public void play_bullet_hit() {
        if (soundOn)
            mysp.play((soundPollMap.get(2)), 1, 1,0,0, 1.2f);
    }
    public void play_game_over() {
        if (soundOn)
            mysp.play((soundPollMap.get(3)), 1, 1,0,0, 1.2f);
    }
    public void play_get_supply() {
        if (soundOn)
            mysp.play((soundPollMap.get(4)), 1, 1,0,0, 1.2f);
    }

}

