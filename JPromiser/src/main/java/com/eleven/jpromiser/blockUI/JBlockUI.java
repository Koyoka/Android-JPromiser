package com.eleven.jpromiser.blockUI;

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
//    public JBlockUI setFragment(IBlockFragment f){
//        mIBlockFragment = f;
//        return this;
//    }
    public JBlockUI setMessage(String loadingMessage){
        this.message = loadingMessage;
        return this;
    }

    @Override
    public IBlockFragment getFragment() {
        return mIBlockFragment;
    }

}
