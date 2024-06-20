package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.aircraftwar2024.R;

public class MainActivity extends AppCompatActivity {

    private ActivityManager activityManager;
    boolean backPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(MainActivity.this);
        setContentView(R.layout.activity_main);
        RadioButton soundOn = (RadioButton) findViewById(R.id.sound_on_radio_button);
        Button offlineButton = (Button) findViewById(R.id.offline_button);
        Button onlineButton = (Button) findViewById(R.id.online_button);
        offlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                intent.putExtra("soundSwitch", soundOn.isChecked());
                startActivity(intent);
            }
        });

        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OnlineGameActivity.class);
                intent.putExtra("soundSwitch", soundOn.isChecked());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            activityManager.exitApp(MainActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(MainActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }
}