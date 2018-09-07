package com.eleven.httpvolleypromiser.proxy.base;


import com.eleven.httpvolleypromiser.utils.GsonHelper;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Eleven on 2016/3/30.
 */
public abstract class BaseModelDto implements Serializable {
    protected String safeGetHttpData(String data){
        if(data==null)
            return "";
        return "\""+data + "\"";
    }
    protected String safeGetHttpData(int data){
        return data+"";
    }
    protected String safeGetHttpData(boolean data){
        return data?"true":"false";
    }
    protected String safeGetHttpData(Object data){
        if(data == null)
            return "";
        return data+"";
    }

    public abstract Map<String, String> getFieldMap();
    protected String safeGetHttpData(BaseModelDto data){
        return GsonHelper.createGson().toJson(data);
    }


}
