package com.eleven.httpvolleypromiser.proxy.checker;

/**
 * Created by 正 on 2017/2/16.
 * state checker的构造工厂
 */

public class HttpResultCheckFactory {
    static IHttpResultCheck mChecker = null;
    public static IHttpResultCheck getChecker(){
        return mChecker;
    }
    public static void setCheck(IHttpResultCheck checker){
        mChecker = checker;
    }
}
