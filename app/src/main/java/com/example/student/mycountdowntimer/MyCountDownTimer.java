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

public class MyCountDownTimer extends CountDownTimer {

    TextView mTimerText;
    private SoundPool mSoundPool;
    private int mSoundResId;
    private long millis = 0L;

    private Context context = null;

    /**
     * @param millisInFuture
     * @param countDownInterval
     * @param context
     */
    MyCountDownTimer(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = context;
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
        mTimerText.setText(String.format("%1d:%2$02d", minute, second)); //指定された書式文字列で文字列を整形
    }


    public void musicSet() {
        //アラームの種類の設定
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        } else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
            mSoundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build();
        }
        mSoundResId = mSoundPool.load(context, R.raw.alert, 1);
    }


    public void musicRelease() {
        mSoundPool.release();
    }

    public SoundPool getmSoundPool() {
        return mSoundPool;
    }

    public void setmSoundPool(SoundPool mSoundPool) {
        this.mSoundPool = mSoundPool;
    }

    public int getmSoundResId() {
        return mSoundResId;
    }

    public void setmSoundResId(int mSoundResId) {
        this.mSoundResId = mSoundResId;
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