package com.eleven.httpvolleypromiser.proxy.user.loginOneProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class LoginOneOutput extends BaseModelDto{

    private String Message;
    private LoginOneContent Content;
    private int State;
    private int StatusCode;

    public void setMessage(String message){
        this.Message = message;
    }
    public String getMessage(){
        return this.Message;
    }

    public void setContent(LoginOneContent content){
        this.Content = content;
    }
    public LoginOneContent getContent(){
        return this.Content;
    }

    public void setState(int state){
        this.State = state;
    }
    public int getState(){
        return this.State;
    }

    public void setStatusCode(int statusCode){
        this.StatusCode = statusCode;
    }
    public int getStatusCode(){
        return this.StatusCode;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("Message", safeGetHttpData(this.Message));
        map.put("Content", safeGetHttpData(this.Content));
        map.put("State", safeGetHttpData(this.State));
        map.put("StatusCode", safeGetHttpData(this.StatusCode));
        return map;
    }

}