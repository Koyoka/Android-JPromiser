package com.eleven.jpromiser.blockUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.eleven.jpromiser.R;
import com.eleven.jpromiser.blockUI.base.IBlockFragment;


/**
 * Created by 正 on 2016/9/5.
 *
 */
public class JBlockFragment extends IBlockFragment {
    protected TextView messageTextView;

    @Override
    public View createBlockView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getDialog().getWindow() != null){
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        getDialog().setCanceledOnTouchOutside(false);
        View v = inflater.inflate(R.layout.jp_dialog_progress_fragment, null);
        messageTextView = (TextView) v.findViewById(R.id.txtMessageView);
        messageTextView.setText(getLoadingMessage());
        return v;
    }
    public TextView getMessageTextView(){
        return messageTextView;
    }
    public void setMessage(String message){
        if(getMessageTextView() != null){
            getMessageTextView().setText(message);
        }
    }
}