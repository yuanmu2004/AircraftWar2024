package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private int gameType=0;
    public static int screenWidth,screenHeight;

    public static Handler mHandler;
//    private int score;

    public static boolean soundOn;

    boolean backPressedOnce = false;

    private ActivityManager activityManager;

    //public MyMediaPlayer myMediaPlayer = new MyMediaPlayer(GameActivity.this,true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(GameActivity.this);

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

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


                if (msg.what == 1){
                    int score = baseGameView.getScore();
                    Intent intent = new Intent(GameActivity.this, RankListActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("gameType", gameType);
                    intent.putExtra("score", score);
//                    setContentView(R.layout.activity_record);
                    startActivity(intent);
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
        activityManager.screenWidth = screenWidth;
        //窗口高度
        screenHeight = dm.heightPixels;
        activityManager.screenHeight = screenHeight;

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
        if (backPressedOnce) {
            activityManager.exitApp(GameActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(GameActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }

}