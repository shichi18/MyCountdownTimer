package com.example.student.mycountdowntimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 　カウントダウン処理のクラス
 */

public class BaseCountDownTimer extends CountDownTimer {

    private TextView timerText;
    private SoundPool soundPool;
    private int soundResId;
    private long millis = 0L;

    private Context context;

    /**
     * @param millisInFuture
     * @param countDownInterval
     * @param timerText
     * @param context
     */
    BaseCountDownTimer(long millisInFuture, long countDownInterval, TextView timerText, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.timerText = timerText;
    }

    /**
     * コンストラクタで指定した間隔で呼び出し
     * カウントダウン処理
     *
     * @param millisUntilFinished タイマーのミリ秒が渡される(残り時間)
     */

    @Override
    public void onTick(long millisUntilFinished) {
        updateTimer(millisUntilFinished);
        millis = millisUntilFinished;//残り時間を代入
    }

    public long getMillis() {
        return millis;
    }

    @SuppressLint("DefaultLocale")
    public void updateTimer(long mill) {
        long minute = mill / 1000 / 60;
        long second = mill / 1000 % 60;
        timerText.setText(String.format("%1d:%2$02d", minute, second)); //指定された書式文字列で文字列を整形
    }


    public void musicSet() {
        //アラームの種類の設定
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        } else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
            soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build();
        }
        soundResId = soundPool.load(context, R.raw.alert, 1);
    }


    public void musicRelease() {
        soundPool.release();
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }

    public int getSoundResId() {
        return soundResId;
    }

    public void setSoundResId(int soundResId) {
        this.soundResId = soundResId;
    }

    /**
     * タイマー終了時に呼び出し
     */
    @Override
    public void onFinish() {
        timerText.setText("0:00");
        //音を鳴らす
        soundPool.play(soundResId, 1.0f, 1.0f, 0, 0, 1.0f);
    }
}