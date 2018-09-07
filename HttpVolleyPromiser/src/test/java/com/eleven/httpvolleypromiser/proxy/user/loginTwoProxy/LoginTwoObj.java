package com.eleven.httpvolleypromiser.proxy.user.loginTwoProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class LoginTwoObj extends BaseModelDto{

    private String filed;
    private int Id;

    public void setFiled(String filed){
        this.filed = filed;
    }
    public String getFiled(){
        return this.filed;
    }

    public void setId(int id){
        this.Id = id;
    }
    public int getId(){
        return this.Id;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("filed", safeGetHttpData(this.filed));
        map.put("Id", safeGetHttpData(this.Id));
        return map;
    }

}