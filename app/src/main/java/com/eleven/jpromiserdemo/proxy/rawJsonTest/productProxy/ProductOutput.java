package com.eleven.jpromiserdemo.proxy.rawJsonTest.productProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class ProductOutput extends BaseModelDto{

    private String Message;
    private int State;
    private ProductContent Content;

    public void setMessage(String message){
        this.Message = message;
    }
    public String getMessage(){
        return this.Message;
    }

    public void setState(int state){
        this.State = state;
    }
    public int getState(){
        return this.State;
    }

    public void setContent(ProductContent content){
        this.Content = content;
    }
    public ProductContent getContent(){
        return this.Content;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("Message", safeGetHttpData(this.Message));
        map.put("State", safeGetHttpData(this.State));
        map.put("Content", safeGetHttpData(this.Content));
        return map;
    }

}