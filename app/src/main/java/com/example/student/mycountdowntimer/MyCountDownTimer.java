package com.example.student.mycountdowntimer;

import android.annotation.SuppressLint;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 　カウントダウン処理のクラス
 */

public class MyCountDownTimer extends CountDownTimer {

    TextView mTimerText;
    SoundPool mSoundPool = null;
    int mSoundResId = 0;
    private long millis = 0L;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * コンストラクタで指定した間隔で呼び出し
     * カウントダウン処理
     *
     * @param millisUntilFinished タイマーのミリ秒が渡される(残り時間)
     */

    @Override
    public void onTick(long millisUntilFinished) {
        timerSet(millisUntilFinished);
        millis = millisUntilFinished;//残り時間を代入
    }

    @SuppressLint("DefaultLocale")
    public void timerSet(long mill) {
        long minute = mill / 1000 / 60;
        long second = mill / 1000 % 60;
        mTimerText.setText(String.format("%1d:%2$02d", minute, second)); //指定された書式文字列で文字列を整形
    }

    /**
     * タイマー終了時に呼び出し
     */
    @Override
    public void onFinish() {
        mTimerText.setText("0:00");
        //音を鳴らす
        mSoundPool.play(mSoundResId, 1.0f, 1.0f, 0, 0, 1.0f);
    }
}