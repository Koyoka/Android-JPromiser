package com.eleven.jpromiserdemo.proxy.rawJsonTest.productErrorProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class ProductErrorContent extends BaseModelDto{

    private double total;
    private ArrayList<ProductErrorItem> item;

    public void setTotal(double total){
        this.total = total;
    }
    public double getTotal(){
        return this.total;
    }

    public void setItem(ArrayList<ProductErrorItem> item){
        this.item = item;
    }
    public ArrayList<ProductErrorItem> getItem(){
        return this.item;
    }


    @Override
    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("total", safeGetHttpData(this.total));
        map.put("item", safeGetHttpData(this.item));
        return map;
    }

}