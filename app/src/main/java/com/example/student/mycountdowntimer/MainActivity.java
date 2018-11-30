package com.example.student.mycountdowntimer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * カウントダウンタイマー
 */

public class MainActivity extends AppCompatActivity {

    MyCountDownTimer mTimer;
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = new MyCountDownTimer(1 * 10 * 1000, 100);

        mTimer.mTimerText = (TextView) findViewById(R.id.timer_text);
        mTimer.mTimerText.setText("0:10");
        mFab = (FloatingActionButton) findViewById(R.id.play_stop);

        //ボタンクリック
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer.isRunning) {
                    mTimer.isRunning = false;
                    mTimer.cancel();
                    mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                } else {
                    mTimer.isRunning = true;
                    mTimer.start();
                    mFab.setImageResource(R.drawable.ic_stop_black_24dp);
                }
            }
        });
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        mTimer.mSoundPool= new SoundPool(2, AudioManager.STREAM_ALARM,0);
        mTimer.mSoundResId = mTimer.mSoundPool.load(this,R.raw.alert,1);
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        mTimer.mSoundPool.release();
    }
}
