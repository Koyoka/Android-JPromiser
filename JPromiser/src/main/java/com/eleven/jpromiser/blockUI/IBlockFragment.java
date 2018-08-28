package com.eleven.jpromiser.blockUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 正 on 2016/9/5.
 */
public abstract class IBlockFragment extends DialogFragment {
    private String message = "loading...!";

    public final static String INTENT_KEY_MESSAGE = "IBlockFragment.message";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null && getArguments().getString(INTENT_KEY_MESSAGE) != null){
            message = getArguments().getString(INTENT_KEY_MESSAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =
         createBlockView(inflater,container,savedInstanceState);
        if(v != null){
            return v;
        }
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    public abstract View createBlockView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected String getLoadingMessage(){
        return message;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try{
            super.show(manager, tag);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try{
            super.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
