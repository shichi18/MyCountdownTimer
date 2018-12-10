package com.example.student.mycountdowntimer;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CountDownTimerFragment extends Fragment implements View.OnClickListener {

    private BaseCountDownTimer baseCountDownTimer;
    private boolean pauseChecked = false;
    private boolean isRunning = false;

    private long initTime = 0L;
    private long remainingTime = 0L;

    private View view;

    private SoundPool soundPool = null;
    private int soundResId = 0;

    public CountDownTimerFragment() {
        // Required empty public constructor
    }

    public void initSet(long update_time, View view) {
        TextView timerText = view.findViewById(R.id.text_timer);
        baseCountDownTimer = new BaseCountDownTimer(update_time, 100, timerText, getContext());
        baseCountDownTimer.updateTimer(update_time);
        setInfo();
    }

    private void timeSet() {
        initTime = 1 * 5 * 1000;//ミリ秒
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_count_down_timer, container, false);

        //タイマーセット
        timeSet();
        initSet(initTime, view);
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
        if (v.getId() == R.id.start_play) {//スタートボタン
            if (!isRunning) {//停止状態のとき
                if (pauseChecked) {
                    pauseChecked = false;
                    isRunning = true;
                    initSet(remainingTime, view);//いる！
                    baseCountDownTimer.start();
                } else {
                    isRunning = true;
                    baseCountDownTimer.start();
                }
            }
        } else if (v.getId() == R.id.pause) {//一時停止ボタン
            if (isRunning) {
                isRunning = false;
                pauseChecked = true;
                getInfo();
                baseCountDownTimer.cancel();//タイマーをストップ
            }
        } else if (v.getId() == R.id.reset) {//リセットボタン
            isRunning = false;
            getInfo();
            baseCountDownTimer.cancel();
            initSet(initTime, view);
        }
    }

    public void getInfo() {
        remainingTime = baseCountDownTimer.getMillis();
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