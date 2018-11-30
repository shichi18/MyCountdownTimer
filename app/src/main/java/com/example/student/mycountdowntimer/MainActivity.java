package com.example.student.mycountdowntimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * カウントダウンタイマー
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Create CountDownTimerFragment
        CountDownTimerFragment countDownTimerFragment = new CountDownTimerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.change_layout, countDownTimerFragment).commit();
    }
}
