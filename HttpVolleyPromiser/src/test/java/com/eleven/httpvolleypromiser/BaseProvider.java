//package com.eleven.httpvolleypromiser;
//
//import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;
//
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * Created by 正 on 2016/9/5.
// */
//@Deprecated
//public abstract class BaseProvider {
//    protected String convertDto2PostParamStr(BaseModelDto dto){
//        if(dto == null)
//            return "";
//
//        Map<String, String> map = dto.getFieldMap();
//        StringBuilder paramStr = new StringBuilder();
//        for (String key : map.keySet()) {
//            String value = map.get(key);
//            paramStr.append("&").append(key).append("=").append(value);
//        }
//        return paramStr.toString();
//    }
//}
