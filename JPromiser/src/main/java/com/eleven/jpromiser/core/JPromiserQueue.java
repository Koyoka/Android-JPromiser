package com.eleven.jpromiser.core;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by 正 on 2016/5/3.
 */
public class JPromiserQueue {
    public static String JPROMISE_QUEUE_DONE = "JPromiserQueue.done";
    public static String JPROMISE_QUEUE_START = "JPromiserQueue.start";

    public static JPromiserQueue newInstance(){
        return new JPromiserQueue();
    }
    public JPromiserQueue(){
        queueId = UUID.randomUUID().toString();
//        INSLog.d("JPromiserQueue","ID:"+queueId);
//        System.out.print("\nJPromiserQueue"+"ID:"+queueId+"\r\n");
    }

    private int mDoneCount = 0;

    private String queueId = "";
    public String getQueueId(){
        return queueId;
    }

    private Promiser curPromiser;
    private Promiser getCurPromiser(){
        return curPromiser;
    }

    /**
     * 异步队列执行
     * 如:A,B,C 三个任务同时执行
     * 只有当A,B,C都完成的时候,该队列才完成
     * */
    public Promiser async(final JPromiser... $cq){

        curPromiser = new Promiser() {
            @Override
            protected void aync(final Deffered<String,String> d) {
                final JPromiser.JWallet myWallet
                        = d.getPromiser().getWallet();

                d.notify(JPROMISE_QUEUE_START,queueId);
                OnPromiseResult promiseDone = new OnPromiseResult() {
                    @Override
                    public void onResult(Object result) {
                        mDoneCount++;
                        if(mDoneCount>= $cq.length){
                            d.resolve("");
                            d.notify(JPROMISE_QUEUE_DONE,queueId);
                        }
                    }
                };

                final OnPromiseThen promiseNotify = new OnPromiseThen() {
                    @Override
                    public void onResult(String key, Object obj) {
                        d.notify(key,obj);
                    }
                };

                for (JPromiser item :
                        $cq) {
                    item .giveWallet(myWallet).runAfter(promiseNotify, promiseDone,promiseDone);
                }

            }
        };
        JPromiser.JWallet myWallet = new JPromiser.JWallet();
        curPromiser.giveWallet(myWallet);
        return curPromiser;

    }

    /**
     * 同步队列执行
     * 如:A,B,C 三个任务按照顺序执行
     * A 完成-> B,B 完成-> C...
     * */
    public Promiser sync(final JPromiser... $cq){

        curPromiser = new Promiser() {
            @Override
            protected void aync(final Deffered<String,String> d) {
                final JPromiser.JWallet myWallet
                        = d.getPromiser().getWallet();

                d.notify(JPROMISE_QUEUE_START,queueId);

                final OnPromiseResult promiseErrorDone = new OnPromiseResult() {
                    @Override
                    public void onResult(Object result) {
                        d.reject("");
                        d.notify(JPROMISE_QUEUE_DONE,queueId);
                        return;
                    }
                };
                final OnPromiseThen promiseNotify = new OnPromiseThen() {
                    @Override
                    public void onResult(String key, Object obj) {
                        d.notify(key,obj);
                    }
                };

                OnPromiseResult promiseSuccessDone = new OnPromiseResult() {
                    @Override
                    public void onResult(Object result) {
                        mDoneCount++;
                        if(mDoneCount>= $cq.length){
                            d.resolve("");
                            d.notify(JPROMISE_QUEUE_DONE,queueId);
                            return;
                        }

                        $cq[mDoneCount]
                                .giveWallet(myWallet)
                                .runAfter(promiseNotify, this,promiseErrorDone);
                    }
                };

                $cq[mDoneCount]
                        .giveWallet(myWallet)
                        .runAfter(promiseNotify, promiseSuccessDone, promiseErrorDone);
            }
        };
        JPromiser.JWallet myWallet = new JPromiser.JWallet();
        curPromiser.giveWallet(myWallet);
        return curPromiser;
    }

    /**
     * 同步队列执行
     * 如:A,B,C 三个任务按照顺序执行
     * A 完成-> B,B 完成-> C...
     * */
    public Promiser sync(final ArrayList<JPromiser> $cq){

        curPromiser = new Promiser() {
            @Override
            protected void aync(final Deffered<String,String> d) {
                final JPromiser.JWallet myWallet
                        = d.getPromiser().getWallet();

                d.notify(JPROMISE_QUEUE_START, queueId);

                final OnPromiseResult promiseErrorDone = new OnPromiseResult() {
                    @Override
                    public void onResult(Object result) {
                        d.reject("");
                        d.notify(JPROMISE_QUEUE_DONE, queueId);
                        return;
                    }
                };
                final OnPromiseThen promiseNotify = new OnPromiseThen() {
                    @Override
                    public void onResult(String key, Object obj) {
                        d.notify(key,obj);
                    }
                };

                OnPromiseResult promiseSuccessDone = new OnPromiseResult() {
                    @Override
                    public void onResult(Object result) {
                        mDoneCount++;
                        if(mDoneCount>= $cq.size()){
                            d.resolve("");
                            d.notify(JPROMISE_QUEUE_DONE, queueId);
                            return;
                        }

//                        $cq[mDoneCount].runAfter(promiseNotify, this,promiseErrorDone);
                        $cq.get(mDoneCount)
                                .giveWallet(myWallet)
                                .runAfter(promiseNotify, this,promiseErrorDone);
                    }
                };

//                $cq[mDoneCount].runAfter(null, promiseSuccessDone,promiseErrorDone);
                $cq.get(mDoneCount)
                        .giveWallet(myWallet)
                        .runAfter(promiseNotify, promiseSuccessDone, promiseErrorDone);
//                $cq[mDoneCount].runAfter(promiseNotify, promiseSuccessDone, promiseErrorDone);
//                $cq[mDoneCount].run(null, promiseSuccessDone,promiseSuccessDone);
            }
        };
        JPromiser.JWallet myWallet = new JPromiser.JWallet();
        curPromiser.giveWallet(myWallet);
        return curPromiser;
    }

    //region unPublish
//    private Promiser syncWithWallet(final ArrayList<JPromiser> $cq){
//
//        curPromiser = new Promiser() {
//            @Override
//            protected void aync(final Deffered<String,String> d) {
//
//                final JPromiser.JWallet myWallet
//                        = d.getPromiser().getWallet();
//
//                d.notify(JPROMISE_QUEUE_START, queueId);
//
//                final OnPromiseResult promiseErrorDone = new OnPromiseResult() {
//                    @Override
//                    public void onResult(Object result) {
//                        d.reject("");
//                        d.notify(JPROMISE_QUEUE_DONE, queueId);
//                        return;
//                    }
//                };
//                final OnPromiseThen promiseNotify = new OnPromiseThen() {
//                    @Override
//                    public void onResult(String key, Object obj) {
//                        d.notify(key,obj);
//                    }
//                };
//
//                OnPromiseResult promiseSuccessDone = new OnPromiseResult() {
//                    @Override
//                    public void onResult(Object result) {
//                        mDoneCount++;
//                        if(mDoneCount>= $cq.size()){
//                            d.resolve("");
//                            d.notify(JPROMISE_QUEUE_DONE, queueId);
//                            return;
//                        }
//                        $cq.get(mDoneCount)
//                                .giveWallet(myWallet)
//                                .runAfter(promiseNotify, this,promiseErrorDone);
//                    }
//                };
//
//                $cq.get(mDoneCount)
//                        .giveWallet(myWallet)
//                        .runAfter(promiseNotify, promiseSuccessDone, promiseErrorDone);
//            }
//        };
//        JPromiser.JWallet myWallet = new JPromiser.JWallet();
//        curPromiser.giveWallet(myWallet);
//        return curPromiser;
//    }
    //endregion
}
