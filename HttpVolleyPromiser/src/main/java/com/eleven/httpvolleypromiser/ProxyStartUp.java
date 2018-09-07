package com.eleven.httpvolleypromiser;


import android.app.Application;

import com.eleven.httpvolleypromiser.proxy.checker.DefaultHttpResultChecker;
import com.eleven.httpvolleypromiser.proxy.checker.HttpResultCheckFactory;
import com.eleven.httpvolleypromiser.proxy.checker.IHttpResultCheck;
import com.eleven.httpvolleypromiser.utils.ProxyLog;

/**
 * Created by 正 on 2017/3/3.
 */

public class ProxyStartUp {
    public final static int PROXY_V1 = 1;
    public final static int PROXY_V2 = 2;

    public static final String DATETIME_FORMAT_HTTP_YMD_T_HMS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATETIME_FORMAT_HTTP_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_FORMAT_YYYYMMDD = "yyyy-MM-dd";

//    public static String ProxyDateTimeFormat = DATETIME_FORMAT_HTTP_YMD_HMS;

    public static void init(Application app, IHttpResultCheck httpResultChecker, boolean debug){
        WebApiVolleyHelper.initRequestQueue(app,"defaultProxy");
        ProxyLog.buildDebug = debug;
        HttpResultCheckFactory.setCheck(httpResultChecker);
    }
    public static void init(Application app, boolean debug){
        WebApiVolleyHelper.initRequestQueue(app,"defaultProxy");
        ProxyLog.buildDebug = debug;
        HttpResultCheckFactory.setCheck(new DefaultHttpResultChecker());
    }

}
