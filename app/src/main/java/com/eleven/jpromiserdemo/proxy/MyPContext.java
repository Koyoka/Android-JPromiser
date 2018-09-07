package com.eleven.jpromiserdemo.proxy;

import com.eleven.httpvolleypromiser.proxy.ProviderContext;

public class MyPContext extends ProviderContext {

    public static MyPContext newInstance(){
        return new MyPContext();
    }

    public MyPContext(){
        this.setShouldCache(false);
        this.setCharset("UTF-8");
        this.setHost("https://raw.githubusercontent.com/Koyoka/");
    }

}
