package com.eleven.httpvolleypromiser.proxy.base;

/**
 * Created by Eleven on 2016/4/13.
 */
public abstract class BaseHttpGetModelDto extends BaseModelDto {
    public abstract String getQueryStr(String url);
}
