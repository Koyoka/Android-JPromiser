package com.eleven.httpvolleypromiser.proxy;

import android.text.TextUtils;


import com.eleven.httpvolleypromiser.proxy.base.BaseHttpGetModelDto;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Created by 正 on 2016/12/1.
 */

public class ProviderHelper {

    /**
     * 格式化创建HttpHostURL
     * 1.provider 上下文 -> host
     * 2.getInputModel   -> url
     * */
    public static String formatHostUrl(ProviderContext pContext, BaseHttpGetModelDto gInput){
        String defineHost;
        if(gInput != null){
            defineHost = gInput.getQueryStr(pContext.getHost());
        }else{
            defineHost = pContext.getHost();
        }

        try {
            URL url = new URL(defineHost);
            URI uri1 = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri1.toURL();
            defineHost = url.toString();

            //region add query string for URL
            if(pContext.queryString != null){
                StringBuilder sb =
                        getQueryString(pContext.queryString);
                if(!TextUtils.isEmpty(sb)){
                    defineHost += "?"+sb.toString();
                }
            }
            //endregion
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return defineHost;
    }

    private static StringBuilder getQueryString(Map<String,String> urlQuerys){
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String,String> entry : urlQuerys.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();
            if(!TextUtils.isEmpty(val)){
                sb.append(key).append("=").append(val).append("&");
            }

        }
//        for (String key : urlQuerys.keySet()){
//            String val = urlQuerys.get(key);
//            if(!TextUtils.isEmpty(val)){
//                sb.append(key).append("=").append(val).append("&");
//            }
//        }
        return sb;
    }

}
