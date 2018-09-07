package com.eleven.jpromiserdemo;

import android.app.Application;

import com.eleven.httpvolleypromiser.ProxyStartUp;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProxyStartUp.init(this, true);
    }
}
