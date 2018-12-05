package com.example.student.mycountdowntimer;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CountDownTimerFragment extends Fragment implements View.OnClickListener {

    private BaseCountDownTimer baseCountDownTimer;
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
        int timerTextId = R.id.text_timer;
        baseCountDownTimer = new BaseCountDownTimer(update_time, 100, view, getContext(), timerTextId);
        baseCountDownTimer.updateTimer(update_time);
        setInfo();
    }

    private long timeSet() {
        time = 5 * 1000;
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
                    baseCountDownTimer.start();
                } else {
                    isRunning = true;
                    baseCountDownTimer.start();
                }
            }
        } else if (v.getId() == R.id.pause) {
            if (isRunning) {
                isRunning = false;
                stopChecked = true;
                getInfo();
                baseCountDownTimer.cancel();//タイマーをストップ
            }
        } else if (v.getId() == R.id.reset) {
            isRunning = false;
        }
    }

    public void getInfo() {
        time = baseCountDownTimer.getMillis();
        soundPool = baseCountDownTimer.getSoundPool();
        soundResId = baseCountDownTimer.getSoundResId();
    }

    public void setInfo() {
        baseCountDownTimer.setSoundPool(soundPool);
        baseCountDownTimer.setSoundResId(soundResId);
    }

    /**
     * Activityが前面になる時に呼び出し
     */
    @Override
    public void onResume() {
        super.onResume();
        baseCountDownTimer.musicSet();
    }

    /**
     * FragmentがBackgroundに移動するときに呼び出し
     */
    @Override
    public void onPause() {
        super.onPause();
        baseCountDownTimer.musicRelease();
    }
}