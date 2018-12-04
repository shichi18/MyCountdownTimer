package com.example.student.mycountdowntimer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.student.mycountdowntimer.MyCountDownTimer.millis;


public class CountDownTimerFragment extends Fragment {

    MyCountDownTimer mTimer = null;
    FloatingActionButton mFab;
    FloatingActionButton pauseFab;
    FloatingActionButton resetFab;
    boolean stopChecked = false;
    boolean isRunning = false;
    public long initCountMillis = 1 * 10 * 1000;

    public CountDownTimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_count_down_timer, container, false);

        mTimer = new MyCountDownTimer(initCountMillis, 100);
        mTimer.mTimerText = (TextView) view.findViewById(R.id.text_timer);
        mTimer.timerSet(initCountMillis);

        // TODO: 2018/12/02 再生する実装
        //Startボタン押す
        mFab = (FloatingActionButton) view.findViewById(R.id.start_play);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {//停止状態のとき
                    if (stopChecked) {
                        stopChecked = false;
                        isRunning = true;
                        mTimer = null;
                        System.out.println("---------------------------------------" + millis / 1000 % 60);
                        mTimer = new MyCountDownTimer(millis, 100);
                        mTimer.mTimerText = (TextView) view.findViewById(R.id.text_timer);
                        mTimer.timerSet(millis);
                        mTimer.start();
                    } else {
                        //一回目だけ
                        isRunning = true;
                        mTimer.start();
                    }
                }
            }
        });


        // TODO: 2018/12/02 一時停止の実装
        //一時停止
        pauseFab = (FloatingActionButton) view.findViewById(R.id.pause);
        pauseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    stopChecked = true;
                    mTimer.cancel();//タイマーをストップ
                }
            }
        });

        // TODO: 2018/12/02 リセットの実装（初期値に戻す）
        resetFab = (FloatingActionButton) view.findViewById(R.id.reset);
        resetFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
            }
        });
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
     * FragmentがBackgroundに移動するときに呼び出し
     */
    @Override
    public void onPause() {
        super.onPause();
        mTimer.mSoundPool.release();
    }
}