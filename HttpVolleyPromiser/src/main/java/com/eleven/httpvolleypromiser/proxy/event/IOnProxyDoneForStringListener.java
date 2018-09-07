package com.eleven.httpvolleypromiser.proxy.event;


import com.eleven.jpromiser.core.JPromiser;

public abstract class IOnProxyDoneForStringListener {
    public abstract void done();


    JPromiser mPromiser;
    public void setPromiser(JPromiser $q){
        mPromiser = $q;
    }
    public JPromiser getPromiser(){
        return mPromiser;
    }
    public JPromiser.JWallet getWallet(){
        if(mPromiser != null){
            return mPromiser.getWallet();
        }
        return null;
    }

    /**
     *
     * @param code 网络编码
     * @param message 错误信息
     */
    public abstract boolean error(int code, String message);
    public abstract void success(String result);
}
