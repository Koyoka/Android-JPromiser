package com.eleven.jpromiserdemo.proxy.rawJsonTest.productProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class ProductItem extends BaseModelDto{

    private double cost;
    private int qty;
    private String desc;

    public void setCost(double cost){
        this.cost = cost;
    }
    public double getCost(){
        return this.cost;
    }

    public void setQty(int qty){
        this.qty = qty;
    }
    public int getQty(){
        return this.qty;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("cost", safeGetHttpData(this.cost));
        map.put("qty", safeGetHttpData(this.qty));
        map.put("desc", safeGetHttpData(this.desc));
        return map;
    }

}