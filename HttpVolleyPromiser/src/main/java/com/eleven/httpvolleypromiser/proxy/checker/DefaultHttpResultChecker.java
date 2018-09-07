package com.eleven.httpvolleypromiser.proxy.checker;

import android.support.annotation.NonNull;

import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.jpromiser.core.JPromiser;

import org.json.JSONException;
import org.json.JSONObject;


public class DefaultHttpResultChecker implements IHttpResultCheck {
    @Override
    public boolean httpResultCheck(String result, JPromiser me, ProviderContext pContext,@NonNull IOnHttpCustCheckMessage onHttpCustCheckMessage) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int state = 0;
            if(jsonObject.has("State")){
                state = jsonObject.getInt("State");
            }
            String errMsg = "";
            if(jsonObject.has("Message")){
                errMsg = jsonObject.getString("Message");
            }

            //获取access_token时Secret错误，或者access_token无效 User Not Login	用户未登录
            //需要重新登录
//            if(state == 40001){
//                onHttpCustCheckMessage.onCustCheckMessage(state, errMsg);
//                return false ;
//            }

            if(state == 0){
                return true;
            }else{
                onHttpCustCheckMessage.onCustCheckMessage(state, errMsg);
                return false ;
            }
        } catch (JSONException e) {
            onHttpCustCheckMessage.onCustCheckMessage(-1, e.getMessage());
            return false ;
        }
    }

}
