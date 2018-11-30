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


public class CountDownTimerFragment extends Fragment {

    MyCountDownTimer mTimer;
    FloatingActionButton mFab;

    public CountDownTimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_down_timer, container, false);


        mTimer = new MyCountDownTimer(1 * 10 * 1000, 100);
        mTimer.mTimerText = (TextView) view.findViewById(R.id.text_timer);
        mTimer.mTimerText.setText("0:10");

        //Stopボタン押す
        mFab = (FloatingActionButton) view.findViewById(R.id.stop_play);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer.isRunning) {
                    mTimer.isRunning = false;
                    mTimer.cancel();
                    mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp);//再生アイコン
                } else {
                    mTimer.isRunning = true;
                    mTimer.start();
                    mFab.setImageResource(R.drawable.ic_stop_black_24dp);//停止アイコン
                }
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
     * ActivityがBackgroundに移動するときに呼び出し
     */
    @Override
    public void onPause() {
        super.onPause();
        mTimer.mSoundPool.release();
    }
}