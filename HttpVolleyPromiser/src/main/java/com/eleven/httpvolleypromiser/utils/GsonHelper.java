package com.eleven.httpvolleypromiser.utils;

import com.eleven.httpvolleypromiser.ProxyStartUp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by 正 on 2016/9/5.
 */
public class GsonHelper {
    public static String defaultDateTimeFormat = ProxyStartUp.DATETIME_FORMAT_HTTP_YMD_HMS;
    public static Gson createGson() {
        return new GsonBuilder().setDateFormat(defaultDateTimeFormat).create();
    }
    public static Gson createGson(String dateTimeFormat) {
        return new GsonBuilder().setDateFormat(dateTimeFormat).create();
    }

    private Gson mGson;
    private static GsonHelper mGsonHelper;
    public static GsonHelper getInstance(){
        if(mGsonHelper == null){
            mGsonHelper = new GsonHelper();
        }
        return mGsonHelper;
    }
    public GsonHelper(){
        mGson = createGson();
    }
    public <T> ArrayList<T> jsonToArray(String jsonStr, Class<T> clz){
        return  mGson.fromJson(jsonStr,new ListParameterizedType(clz));
    }
    public Gson getGson(){
        return mGson;
    }

}
