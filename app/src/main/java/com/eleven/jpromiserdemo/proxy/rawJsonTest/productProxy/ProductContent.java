package com.eleven.jpromiserdemo.proxy.rawJsonTest.productProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;

public class ProductContent extends BaseModelDto{

    private double total;
    private ArrayList<ProductItem> item;

    public void setTotal(double total){
        this.total = total;
    }
    public double getTotal(){
        return this.total;
    }

    public void setItem(ArrayList<ProductItem> item){
        this.item = item;
    }
    public ArrayList<ProductItem> getItem(){
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