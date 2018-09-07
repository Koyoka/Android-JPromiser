package com.eleven.httpvolleypromiser.utils;

import android.text.TextUtils;
import android.util.Log;


public class ProxyLog {

    public static boolean buildDebug = true;
    public static boolean getBuildConfigDebug(){
        return buildDebug;
    }
    public static int $e(String tag, String msg){
        if(!getBuildConfigDebug()){
            return 0;
        }
        return Log.e(tag, msg);
    }

    public static int $e(String tag, String msg, Throwable tr){
        if(!getBuildConfigDebug()){
            return 0;
        }
        return Log.e(tag, msg, tr);
    }

    public static int e(String tag, String msg){
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        if(getBuildConfigDebug()){
            return Log.e(tag, msg);
        }else{
            return 0;
        }
    }

    public static int e(String tag, String msg, Throwable tr){
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        if(getBuildConfigDebug()){
            return Log.e(tag, msg, tr);
        }else{
            return 0;
        }
    }
}
