package com.example.student.mycountdowntimer;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CountDownTimerFragment extends Fragment {

    MyCountDownTimer mTimer;
    FloatingActionButton mFab;
    FloatingActionButton pauseFab;
    FloatingActionButton resetFab;
    boolean stopChecked = false;
    boolean isRunning = false;
    private static long initCountMillis = 1 * 10 * 1000;

    public CountDownTimerFragment() {
        // Required empty public constructor
    }

    @SuppressLint("DefaultLocale")
    private void initSetting(long millis, View view) {
        long minute = millis / 1000 / 60;
        long second = millis / 1000 % 60;
        mTimer = new MyCountDownTimer(millis, 100);
        mTimer.mTimerText = (TextView) view.findViewById(R.id.text_timer);
        mTimer.mTimerText.setText(String.format("%1d:%2$02d", minute, second)); //指定された書式文字列で文字列を整形

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_down_timer, container, false);

        initSetting(initCountMillis, view);

        //Startボタン押す
        mFab = (FloatingActionButton) view.findViewById(R.id.start_play);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {//押したとき
                    isRunning = true;//動作中判定
                    mTimer.start();


//                    if (stopChecked) {//途中からだったら
//                        mTimer = new MyCountDownTimer(mTimer.countMills, 100);
//                        mTimer.start();
//                    }
                }
            }
        });

        //一時停止
        pauseFab = (FloatingActionButton) view.findViewById(R.id.pause);
        pauseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    mTimer.cancel();//タイマーをストップ
                }
            }
        });

//        resetFab = (FloatingActionButton) view.findViewById(R.id.reset);
//        resetFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isRunning = false;
//            }
//        });
        return view;

    }

    /**
     * Activityが前面になる時に呼び出し
     */
    @Override
    public void onResume() {
        super.onResume();
        //アラームの種類の設定
        mTimer.mSoundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        mTimer.mSoundResId = mTimer.mSoundPool.load(getContext(), R.raw.alert, 1);

    }

    /**
     * ActivityがBackgroundに移動するときに呼び出し
     */
    @Override
    public void onPause() {
        super.onPause();
        mTimer.mSoundPool.release();
    }
}