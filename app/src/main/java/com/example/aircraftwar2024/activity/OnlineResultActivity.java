package com.example.aircraftwar2024.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;

public class OnlineResultActivity extends AppCompatActivity {

    private ActivityManager activityManager;
    private boolean backPressedOnce = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(OnlineResultActivity.this);
        setContentView(R.layout.activity_online_result);
        int score, rivalScore;
        if (getIntent() != null) {
            score = getIntent().getIntExtra("1", 0);
            rivalScore = getIntent().getIntExtra("2", 0);
            TextView yourScoreText = (TextView) findViewById(R.id.your_score_text);
            TextView rivalScoreText = (TextView) findViewById(R.id.rival_score_text);
            String yourScoreTextContent = "Your Score: " + score;
            String rivalScoreTextContent = "Rival Score: " + rivalScore;
            yourScoreText.setText(yourScoreTextContent);
            rivalScoreText.setText(rivalScoreTextContent);
        }
        Button mainMenuButton = (Button) findViewById(R.id.main_menu_button_online_result);
        Button exitButton = (Button) findViewById(R.id.exit_button_online_result);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OnlineResultActivity.this)
                        .setTitle("Back to Title")
                        .setMessage("Are you sure to BACK to title?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activityManager.back2Title();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OnlineResultActivity.this)
                        .setTitle("Exit")
                        .setMessage("Are you sure to EXIT?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activityManager.exitApp(OnlineResultActivity.this);
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            }
        });
    }





    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            activityManager.exitApp(OnlineResultActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(OnlineResultActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }
}
