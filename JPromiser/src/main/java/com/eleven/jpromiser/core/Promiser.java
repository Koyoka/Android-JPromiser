package com.eleven.jpromiser.core;

/**
 * Created by 正 on 2016/5/13.
 */
public abstract class Promiser extends JPromiser<String,String> {

    protected void qStart(){
        mDeffered.notify(JPromiserQueue.JPROMISE_QUEUE_START);
    }
    protected void qEnd(){
        mDeffered.notify(JPromiserQueue.JPROMISE_QUEUE_DONE);
    }

}
