package com.eleven.jpromiserdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//test oom
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },1000000);
    }
}
