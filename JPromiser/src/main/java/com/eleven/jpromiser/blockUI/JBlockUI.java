package com.eleven.jpromiser.blockUI;

import com.eleven.jpromiser.blockUI.base.IBlockFragment;
import com.eleven.jpromiser.blockUI.base.IBlockUI;

/**
 * Created by 正 on 2016/9/5.
 *
 */
public class JBlockUI extends IBlockUI {
    IBlockFragment mIBlockFragment;
    public JBlockUI(String loadingMessage,IBlockFragment f) {
        super(loadingMessage);
        mIBlockFragment = f;
    }
    public static JBlockUI newInstance(IBlockFragment f){
        return new JBlockUI("loading...", f);
    }
    public static JBlockUI newInstance(String loadingMessage, IBlockFragment f){
        return new JBlockUI(loadingMessage, f);
    }
    public static JBlockUI newInstance(String loadingMessage){
        return new JBlockUI(loadingMessage, new JBlockFragment());
    }
    public static JBlockUI newInstance(){
        return new JBlockUI("loading", new JBlockFragment());
    }
    public JBlockUI setMessage(String loadingMessage){
        this.message = loadingMessage;
        return this;
    }

    @Override
    public IBlockFragment getFragment() {
        return mIBlockFragment;
    }

}
