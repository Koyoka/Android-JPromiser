package com.eleven.httpvolleypromiser.proxy.event;



/**
 * Created by Eleven on 2016/4/13.
 */
public interface IHttpHelperListener {
    boolean onSuccess(String result);
    boolean onError(int stateCode, String message);
}
