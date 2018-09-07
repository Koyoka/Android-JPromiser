package com.eleven.httpvolleypromiser.proxy.user.sysLoginProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseHttpGetModelDto;

public class SysLoginGInput extends BaseHttpGetModelDto{

    private String sUserID;
    private String sPassword;

    public void setSUserID(String sUserID){
        this.sUserID = sUserID;
    }
    public String getSUserID(){
        return this.sUserID;
    }

    public void setSPassword(String sPassword){
        this.sPassword = sPassword;
    }
    public String getSPassword(){
        return this.sPassword;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("sUserID", safeGetHttpData(this.sUserID));
        map.put("sPassword", safeGetHttpData(this.sPassword));
        return map;
    }

    @Override
    public String getQueryStr(String url){
        String qStr = url
            .replaceFirst("sUserID", this.sUserID == null ? "" : this.sUserID)
            .replaceFirst("sPassword", this.sPassword == null ? "" : this.sPassword)
        ;
        return qStr;
    }

}