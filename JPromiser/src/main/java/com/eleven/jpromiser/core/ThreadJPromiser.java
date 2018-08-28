package com.eleven.jpromiser.core;

import android.os.Handler;
import android.os.Message;

public abstract class ThreadJPromiser<T, U> extends Handler {

    private JPromiser.Deffered<T, U> rootD;
    private T mT;
    private U mU;
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        if(msg.what == 1){

            if(mT != null){
                rootD.resolve(mT);
            }else{
                rootD.resolve();
            }
        }else if(msg.what == 2){
            if(mU != null){
                rootD.reject(mU);
            }else{
                rootD.reject();
            }
        }
    }

    public JPromiser<T, U> promiser(){
        return new JPromiser<T, U>() {
            @Override
            protected void aync(final Deffered<T, U> d) {
                rootD = d;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        threadAync(ThreadJPromiser.this);
                    }
                };
                thread.run();
            }
        };
    }

    protected abstract void threadAync(ThreadJPromiser<T, U> d);

    public void resolve(T t){
        Message msg = new Message();
        mT = t;
        msg.obj = t;
        msg.what = 1;
        this.sendMessage(msg);
    }
    public void resolve(){
        Message msg = new Message();
        msg.what = 1;
        this.sendMessage(msg);
    }
    public void reject(U u){
        Message msg = new Message();
        mU = u;
        msg.obj = u;
        msg.what = 2;
        this.sendMessage(msg);
    }
    public void reject(){
        Message msg = new Message();
        msg.what = 2;
        this.sendMessage(msg);
    }
}
