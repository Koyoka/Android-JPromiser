package com.eleven.jpromiser.blockUI.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


import com.eleven.jpromiser.core.JPromiser;
import com.eleven.jpromiser.core.JPromiserQueue;
import com.eleven.jpromiser.core.Promiser;

import java.util.ArrayList;

/**
 * Created by 正 on 2016/9/5.
 */
public abstract class IBlockUI {

    String TAG = IBlockUI.class.getSimpleName();

    protected String message;
    public IBlockUI(String loadingMessage){
        message = loadingMessage;
    }

    public abstract IBlockFragment getFragment();
    public JPromiser getBlockPromiser(){
        return null;
    }

    /**
     * 异步队列顺序执行
     * */
    public Promiser dialog(final AppCompatActivity acty, JPromiser... $qs) {
        return dialog(acty,null,$qs);
    }
    public Promiser dialog(final AppCompatActivity acty,JPromiser.OnPromiseResult onPromiseSuccess, JPromiser... $qs){
        setContext(acty);
        return _dialog(acty.getSupportFragmentManager(),
                false,acty.getClass().getSimpleName(),onPromiseSuccess,$qs);
    }
    public Promiser dialog(final Fragment fragment, JPromiser... $qs) {
        return dialog(fragment,null,$qs);
    }
    public Promiser dialog(final Fragment fragment, JPromiser.OnPromiseResult onPromiseSuccess, JPromiser... $qs){
        setContext(fragment);
        return _dialog(fragment.getChildFragmentManager(),
                false,fragment.getClass().getSimpleName(),onPromiseSuccess,$qs);
    }

    /**
     * 同步队列顺序执行
     * */
    public Promiser $dialog(final AppCompatActivity acty, JPromiser... $qs) {
        return $dialog(acty,null,$qs);
    }
    public Promiser $dialog(final AppCompatActivity acty,JPromiser.OnPromiseResult onPromiseSuccess, JPromiser... $qs){
        setContext(acty);
        return _dialog(acty.getSupportFragmentManager(),
                true,acty.getClass().getSimpleName(),onPromiseSuccess,$qs);
    }

    public Promiser $dialog(final AppCompatActivity acty, ArrayList<JPromiser> $qs) {
        return $dialog(acty,null,$qs);
    }
    public Promiser $dialog(final AppCompatActivity acty,JPromiser.OnPromiseResult onPromiseSuccess, ArrayList<JPromiser> $qs){
        setContext(acty);
        return _dialog(acty.getSupportFragmentManager(),
               acty.getClass().getSimpleName(),onPromiseSuccess,$qs);
    }

    public Promiser $dialog(final Fragment fragment, JPromiser... $qs){
        setContext(fragment);
        return _dialog(fragment.getChildFragmentManager(),
                true,fragment.getClass().getSimpleName(),null,$qs);
    }
    public Promiser $dialog(final Fragment fragment, ArrayList<JPromiser> $qs){
        setContext(fragment);
        return _dialog(fragment.getChildFragmentManager(),
                fragment.getClass().getSimpleName(),null,$qs);
    }

    //======================================================================================================
    private Promiser _dialog(final FragmentManager fragmentManager, boolean isSync, final String tag,
                             JPromiser.OnPromiseResult onPromiseSuccess,
                             JPromiser... $qs){
        if($qs == null)
            return null;
        Fragment f =
                fragmentManager.findFragmentByTag(tag);
        if(f != null){
            return null;
        }

        final IBlockFragment dialogFragment = getFragment();
        Bundle bundle = dialogFragment.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putString(IBlockFragment.INTENT_KEY_MESSAGE,message);
        dialogFragment.setArguments(bundle);

        Promiser $q = isSync
                ? makeSyncRunPromiser($qs)
                : makeAsynRunPromiser($qs);
//        Promiser $q = isSync
//                ? JPromiserQueue.newInstance().sync($qs)
//                : JPromiserQueue.newInstance().async($qs);

        $q.run(new Promiser.OnPromiseThen() {
            @Override
            public void onResult(String s, Object obj) {
                if (s.equals(JPromiserQueue.JPROMISE_QUEUE_DONE)) {

                    if(!TextUtils.isEmpty(mStartQueueId)
                            && obj instanceof String
                            && mStartQueueId.equals(obj)){
                        //INSLog.d("BlockUI","Done QID:"+mStartQueueId);
                        dialogFragment.dismiss();
                    } //else
                        //INSLog.e("BlockUI","Done 抛弃 QID:"+obj);

                } else if (s.equals(JPromiserQueue.JPROMISE_QUEUE_START)) {

                    if(!TextUtils.isEmpty(mStartQueueId)
                            && !mStartQueueId.equals(obj)){
                        //INSLog.e("BlockUI","Start 抛弃 QID:"+obj);
                        return;
                    }

                    if(TextUtils.isEmpty(mStartQueueId)
                            && obj instanceof String){
                        mStartQueueId = (String) obj;
                        //INSLog.e("BlockUI","QID 这里不能为空，需要提前初始化"+mStartQueueId);
                    }

                    Fragment f =
                            fragmentManager.findFragmentByTag(tag);
                    if (f != null) {
                        return;
                    }
                    //INSLog.d("BlockUI","Start QID:"+mStartQueueId);
                    dialogFragment.show(fragmentManager, tag);
                }
            }
        }, onPromiseSuccess, null);

        return $q;
    }
    private Promiser _dialog(final FragmentManager fragmentManager, final String tag,
                             JPromiser.OnPromiseResult onPromiseSuccess,
                             ArrayList<JPromiser> $qs){
        Fragment f =
                fragmentManager.findFragmentByTag(tag);
        if(f != null){
            return null;
        }

        final IBlockFragment dialogFragment = getFragment();
        Bundle bundle = dialogFragment.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putString(IBlockFragment.INTENT_KEY_MESSAGE,message);
        dialogFragment.setArguments(bundle);

        Promiser $q = makeSyncRunPromiser($qs);
//        Promiser $q = JPromiserQueue.newInstance().sync($qs);

        $q.run(new JPromiser.OnPromiseThen() {
            @Override
            public void onResult(String s, Object obj) {
                if (s.equals(JPromiserQueue.JPROMISE_QUEUE_DONE)) {

                    if(!TextUtils.isEmpty(mStartQueueId)
                            && obj instanceof String
                            && mStartQueueId.equals(obj)){
                        //INSLog.d("BlockUI","Done QID:"+mStartQueueId);
                        dialogFragment.dismiss();
                    } //else
                        //INSLog.e("BlockUI","Done 抛弃 QID:"+obj);

                } else if (s.equals(JPromiserQueue.JPROMISE_QUEUE_START)) {

                    if(!TextUtils.isEmpty(mStartQueueId)
                            && !mStartQueueId.equals(obj)){
                        //INSLog.e("BlockUI","Start 抛弃 QID:"+obj);
                        return;
                    }

                    if(TextUtils.isEmpty(mStartQueueId)
                            && obj instanceof String){
                        mStartQueueId = (String) obj;
                        //INSLog.e("BlockUI","QID 这里不能为空，需要提前初始化"+mStartQueueId);
                    }

                    Fragment f =
                            fragmentManager.findFragmentByTag(tag);
                    if (f != null) {
                        return;
                    }
                    //INSLog.d("BlockUI","Start QID:"+mStartQueueId);
                    dialogFragment.show(fragmentManager, tag);
                }
            }
        }, onPromiseSuccess, null);

        return $q;
    }

    private Promiser makeSyncRunPromiser(ArrayList<JPromiser> $qs){
        JPromiserQueue queue = JPromiserQueue.newInstance();
        mStartQueueId = queue.getQueueId();
        //INSLog.d("BlockUI","初始化 QID:"+mStartQueueId);
        if(getBlockPromiser() != null){
            $qs.add(0,getBlockPromiser());
        }
        return queue.sync($qs);
    }
    private Promiser makeSyncRunPromiser(JPromiser... $qs){
        JPromiserQueue queue = JPromiserQueue.newInstance();
        mStartQueueId = queue.getQueueId();
        //INSLog.d("BlockUI","初始化 QID:"+mStartQueueId);
        if(getBlockPromiser() != null){
            ArrayList<JPromiser> task = new ArrayList<>();
            task.add(getBlockPromiser());
            //region #..
            for (JPromiser q : $qs) {
                task.add(q);
            }
            //endregion
            return queue.sync(task);
        }else{
            return queue.sync($qs);
        }

    }
    private Promiser makeAsynRunPromiser(JPromiser... $qs){
        JPromiserQueue queue = JPromiserQueue.newInstance();
        mStartQueueId = queue.getQueueId();
        //INSLog.d("BlockUI","初始化 QID:"+mStartQueueId);
        if(getBlockPromiser() != null){
            return //JPromiserQueue.newInstance()
            queue.sync(
                    getBlockPromiser(),
                    JPromiserQueue.newInstance().async($qs)
            );
        }else{
            return queue.async($qs);
        }
    }

    private String mStartQueueId = null;

    public String getBlockQueueId(){
        return mStartQueueId;
    }
    private Context mContext;
    private void setContext(Activity acty){
        mContext = acty;
    }
    private void setContext(Fragment fragment){
        mContext = fragment.getContext();
    }
    protected Context getContext(){
        return mContext;
    }


}
