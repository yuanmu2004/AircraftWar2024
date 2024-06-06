package com.example.aircraftwar2024.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.music.MyMediaPlayer;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private int gameType=0;
    public static int screenWidth,screenHeight;

    public static Handler handler;

    public static boolean soundOn;

    //public MyMediaPlayer myMediaPlayer = new MyMediaPlayer(GameActivity.this,true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getScreenHW();

        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            soundOn = getIntent().getBooleanExtra("soundOn", false);
            Log.v("GAMEAC", String.valueOf(soundOn));
        }


        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        Log.v("GAME","LOADING GAME");
        BaseGame baseGameView = getGameByModeID(gameType);
        //baseGameView.setSoundOn(soundOn);
        Log.v("GAME","HAVE LOADED GAME");
        setContentView(baseGameView);

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = null;
                if (msg.what == 1) {
                    intent = new Intent(GameActivity.this, RankListActivity.class);
                    //should be: intent.putExtra(...) to transmit information
                    intent.putExtra("score", baseGameView.getScore());
                    //in RankListActivity use: getIntent().getIntExtra("score", 0) to get score
                    startActivity(intent);
                    //setContentView(R.layout.activity_rank_list);
                }
            }
        };
    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    public BaseGame getGameByModeID(int gameType) {
        switch (gameType) {
            case 0: return new EasyGame(GameActivity.this);
            case 1: return new MediumGame(GameActivity.this);
            case 2: return new HardGame(GameActivity.this);
            default: return new MediumGame(GameActivity.this);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}