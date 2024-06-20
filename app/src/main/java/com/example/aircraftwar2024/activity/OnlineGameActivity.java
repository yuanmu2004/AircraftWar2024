package com.example.aircraftwar2024.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.game.OnlineGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class OnlineGameActivity extends AppCompatActivity {
    private static final String TAG = "OnlineGameActivity";

//    private int gameType=0;
    public static int screenWidth,screenHeight;

    public static Handler mHandler;
//    private int score;

    public static boolean soundOn;

    boolean backPressedOnce = false;

    private ActivityManager activityManager;
    public OnlineGame onlineGameView;
    private AlertDialog connectingMsgDialog;
    private AlertDialog matchingMsgDialog;

    //public MyMediaPlayer myMediaPlayer = new MyMediaPlayer(GameActivity.this,true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(OnlineGameActivity.this);

        getScreenHW();

        if(getIntent() != null){
//            gameType = getIntent().getIntExtra("gameType",1);
            soundOn = getIntent().getBooleanExtra("soundOn", false);
            Log.v("GAMEAC", String.valueOf(soundOn));
        }
        connectingMsgDialog = new AlertDialog.Builder(OnlineGameActivity.this).setMessage("Connecting...Please wait").create();
        matchingMsgDialog = new AlertDialog.Builder(OnlineGameActivity.this).setMessage("Finding rival...Please wait").create();
        Log.v("GAME","LOADING GAME");

//        BaseGame baseGameView = getGameByModeID(gameType);
        //baseGameView.setSoundOn(soundOn);
//        OnlineGame onlineGameView = new OnlineGame(OnlineGameActivity.this);
//        Log.v("GAME","HAVE LOADED GAME");
//        setContentView(baseGameView);
//
//        mHandler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//
//                if (msg.what == 1){
//                    int score = baseGameView.getScore();
//                    Intent intent = new Intent(GameActivity.this, RankListActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("gameType", gameType);
//                    intent.putExtra("score", score);
////                    setContentView(R.layout.activity_record);
//                    startActivity(intent);
//                }
//
//            }
//        };
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0: connectingMsgDialog.show();     break;
                    case 1: matchingMsgDialog.show();       break;
                    case 2: connectingMsgDialog.dismiss();  break;
                    case 3: matchingMsgDialog.dismiss();    break;
                    case 4: displayGame();                  break;
                }
            }
        };
        new ClientThread(mHandler).start();
    }

    private void displayGame() {
        onlineGameView = new OnlineGame(OnlineGameActivity.this);
        Log.v("INFO", "Game is created");
        setContentView(onlineGameView);
    }
    private void gameOver(int score, int rivalScore) {
        Intent intent = new Intent(OnlineGameActivity.this, OnlineResultActivity.class);
        intent.putExtra("1", score);
        intent.putExtra("2", rivalScore);
        startActivity(intent);
        onlineGameView.interrupt();
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

//    public BaseGame getGameByModeID(int gameType) {
//        switch (gameType) {
//            case 0: return new EasyGame(GameActivity.this);
//            case 1: return new MediumGame(GameActivity.this);
//            case 2: return new HardGame(GameActivity.this);
//            default: return new MediumGame(GameActivity.this);
//        }
//    }

    public class ClientThread extends Thread {
        private BufferedReader br;
        private Handler toClientHandler;

        private PrintWriter writer;


        public ClientThread(Handler toClientHandler) {
            this.toClientHandler = toClientHandler;
        }
//        public ClientThread() {}


        @Override
        public void run() {
            try {
                Socket socket = new Socket();

//                connectingMsgDialog.show();
                toClientHandler.sendEmptyMessage(0);
                Log.v("INFO", "Connecting to server");
                socket.connect(new InetSocketAddress("10.0.2.2", 9999), 5000);
//                connectingMsgDialog.dismiss();
                toClientHandler.sendEmptyMessage(2);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")), true);
//                matchingMsgDialog.show();
                toClientHandler.sendEmptyMessage(1);
                Thread handleServerMsg = new Thread(() -> {
                    String fromServer;
                    try {
                        while ((fromServer = br.readLine()) != null) {
//                            Log.v("msg", fromServer);
                            if (fromServer.equals("game start")) {
//                                matchingMsgDialog.dismiss();
                                toClientHandler.sendEmptyMessage(3);
//                                Log.v("info", "asasasa");
                                toClientHandler.sendEmptyMessage(4);
                            } else if (fromServer.equals("game over")) {
                                int score = onlineGameView.getScore();
                                writer.println(score);
                                int rivalScore = Integer.parseInt(br.readLine());
//                                Thread.sleep(1000);
//                                socket.close();
                                gameOver(score, rivalScore);
                                return;
                            } else if (fromServer.equals("score request")) {
                                int score = onlineGameView.getScore();
                                writer.println(score);
                            } else if (fromServer.equals("state request")) {
                                if (onlineGameView.isGameOver()) {
                                    writer.println("game over");
                                }
                                else {
                                    writer.println("game running");
                                }
                            }
                            else {
//                                Log.v("rivalScore", fromServer);
                                try {
                                    int rivalScore = Integer.parseInt(fromServer);
                                    onlineGameView.setRivalScore(rivalScore);
//                                    Log.v("INFO", String.valueOf(onlineGameView.getScore()));
                                } catch (Exception e) {
//                                    Log.v("INFO", "problem");
                                }
                            }
                        }
//                        Log.v("out", "out");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                handleServerMsg.start();


            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }



    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            activityManager.exitApp(OnlineGameActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(OnlineGameActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }


}
