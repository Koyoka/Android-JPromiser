package com.eleven.jpromiserdemo.proxy.user.sysLoginProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class SysLoginPInput extends BaseModelDto{

    private SysLoginObj Obj;
    private String name;
    private int age;

    public void setObj(SysLoginObj obj){
        this.Obj = obj;
    }
    public SysLoginObj getObj(){
        return this.Obj;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("Obj", safeGetHttpData(this.Obj));
        map.put("name", safeGetHttpData(this.name));
        map.put("age", safeGetHttpData(this.age));
        return map;
    }

}