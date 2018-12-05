package com.example.student.mycountdowntimer;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CountDownTimerFragment extends Fragment implements View.OnClickListener {

    MyCountDownTimer mTimer;
    boolean stopChecked = false;
    boolean isRunning = false;
    View view = null;
    private long time = 0L;

    FloatingActionButton mFab;
    FloatingActionButton pauseFab;
    FloatingActionButton resetFab;

    private SoundPool mSoundPool = null;
    private int mSoundResId = 0;

    public CountDownTimerFragment() {
        // Required empty public constructor
    }

    public void initSet(long update_time, View view) {
        mTimer = null;
        mTimer = new MyCountDownTimer(update_time, 100, mSoundPool, mSoundResId);
        mTimer.mTimerText = (TextView) view.findViewById(R.id.text_timer);
        mTimer.updateTimer(update_time);
    }

    private long timeSet() {
        time = 1 * 10 * 1000;
        return time;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_count_down_timer, container, false);
        long initCountMillis = timeSet();
        //タイマーセット
        initSet(initCountMillis, view);
        //Idセット
        mFab = view.findViewById(R.id.start_play);
        pauseFab = view.findViewById(R.id.pause);
        resetFab = view.findViewById(R.id.reset);

        //リスナーセット
        mFab.setOnClickListener(this);
        pauseFab.setOnClickListener(this);
        resetFab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_play) {
            if (!isRunning) {//停止状態のとき
                if (stopChecked) {
                    stopChecked = false;
                    isRunning = true;
                    initSet(time, view);
                    mTimer.start();
                } else {
                    //一回目だけ
                    isRunning = true;
                    mTimer.start();
                }
            }
        }

        if (v.getId() == R.id.pause) {
            if (isRunning) {
                isRunning = false;
                stopChecked = true;
                time = mTimer.getMillis();
                mTimer.cancel();//タイマーをストップ

            }

        }

        if (v.getId() == R.id.reset) {
            isRunning = false;
        }
    }

    public void musicSet() {
        mSoundPool = mTimer.mSoundPool;
        mSoundResId = mTimer.mSoundResId;
        //アラームの種類の設定
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        } else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
            mSoundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build();
        }
        mSoundResId = mSoundPool.load(getContext(), R.raw.alert, 1);

    }

    /**
     * Activityが前面になる時に呼び出し
     */
    @Override
    public void onResume() {
        super.onResume();
        musicSet();
    }


    /**
     * FragmentがBackgroundに移動するときに呼び出し
     */
    @Override
    public void onPause() {
        super.onPause();
        mTimer.mSoundPool.release();
    }
}