package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity {
    boolean soundSwitch;

    class GameModeListener implements View.OnClickListener {

        int gameType;
        public GameModeListener(int gameMode) {
            this.gameType = gameMode;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("gameType", gameType);
            intent.putExtra("soundSwitch", soundSwitch);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        setContentView(R.layout.activity_offline);
        Button easyModeButton = (Button) findViewById(R.id.easyModeButton);
        Button normalModeButton = (Button) findViewById(R.id.normalModeButton);
        Button hardModeButton = (Button) findViewById(R.id.hardModeButton);
        this.soundSwitch = getIntent().getBooleanExtra("soundSwitch", false);
        easyModeButton.setOnClickListener(new GameModeListener(0));
        normalModeButton.setOnClickListener(new GameModeListener(1));
        hardModeButton.setOnClickListener(new GameModeListener(2));
    }


}