package com.example.aircraftwar2024.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.OnlineGameActivity;
import com.example.aircraftwar2024.music.BgmPlayer;
import com.example.aircraftwar2024.music.SoundPlayer;

public class OnlineGame extends BaseGame {

    public void setRivalScore(int rivalScore) {
        this.rivalScore = rivalScore;
    }

    private int rivalScore;

    private final Paint mTextPaint;

    public OnlineGame (Context context) {
        super(context);
        this.backGround = ImageManager.BACKGROUND2_IMAGE;
        this.enemyMaxNumber = 3;
        this.heroShootCycle = 9;
        this.enemyShootCycle = 19;
        this.eliteProb = 0.15;
        this.bossScoreThreshold = 300;
        this.tickCycle = 300;
        mTextPaint = new Paint();
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);

    }

    /**
     * 普通模式随着时间增加而提高难度
     */
    @Override
    protected void tick() {
        this.tickCounter++;
        if (this.tickCounter >= this.tickCycle) {
            this.tickCounter = 0;
            // 提高敌机产生频率（减小产生周期）
            this.enemyCycle *= 0.99;
            // 提高敌机血量
            gameLevel *= 1.01;
            System.out.format(" 提高难度！精英机概率:%.2f,敌机周期:%.2f, 敌机属性提升倍率:%.2f。\n",
                    eliteProb, enemyCycle, gameLevel);

        }
    }

    @Override
    protected void paintScoreAndLife() {
        super.paintScoreAndLife();
        try {
            canvas.drawText("Rival Score:" + rivalScore, 300, 60, mTextPaint);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void gameOverAction() {

    }

    @Override
    protected void audioInit(Context context) {
        bgmPlayer = new BgmPlayer(context, OnlineGameActivity.soundOn);
        soundPlayer = new SoundPlayer(context, OnlineGameActivity.soundOn);
    }
}
