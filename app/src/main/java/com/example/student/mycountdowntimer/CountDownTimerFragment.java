package com.example.student.mycountdowntimer;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CountDownTimerFragment extends Fragment implements View.OnClickListener {

    private MyCountDownTimer mTimer;
    private boolean stopChecked = false;
    private boolean isRunning = false;
    private long time = 0L;
    private View view;

    private SoundPool soundPool = null;
    private int soundResId = 0;

    public CountDownTimerFragment() {
        // Required empty public constructor
    }

    public void initSet(long update_time, View view) {
        mTimer = null;
        mTimer = new MyCountDownTimer(update_time, 100, getContext());
        mTimer.mTimerText = view.findViewById(R.id.text_timer);
        mTimer.updateTimer(update_time);
        setInfo();
    }

    private long timeSet() {
        time = 10 * 1000;
        return time;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_count_down_timer, container, false);

        //タイマーセット
        long initCountMillis = timeSet();
        initSet(initCountMillis, view);

        //Idセット
        FloatingActionButton mFab = view.findViewById(R.id.start_play);
        FloatingActionButton pauseFab = view.findViewById(R.id.pause);
        FloatingActionButton resetFab = view.findViewById(R.id.reset);

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
                    initSet(time, view);//いる！
                    mTimer.start();
                } else {
                    isRunning = true;
                    mTimer.start();
                }
            }
        } else if (v.getId() == R.id.pause) {
            if (isRunning) {
                isRunning = false;
                stopChecked = true;
                getInfo();
                mTimer.cancel();//タイマーをストップ
            }
        } else if (v.getId() == R.id.reset) {
            isRunning = false;
        }
    }

    public void getInfo() {
        time = mTimer.getMillis();
        soundPool = mTimer.getmSoundPool();
        soundResId = mTimer.getmSoundResId();
    }

    public void setInfo() {
        mTimer.setmSoundPool(soundPool);
        mTimer.setmSoundResId(soundResId);
    }

    /**
     * Activityが前面になる時に呼び出し
     */
    @Override
    public void onResume() {
        super.onResume();
        mTimer.musicSet();
    }

    /**
     * FragmentがBackgroundに移動するときに呼び出し
     */
    @Override
    public void onPause() {
        super.onPause();
        mTimer.musicRelease();
    }
}