package com.eleven.httpvolleypromiser.proxy.base;

import com.eleven.httpvolleypromiser.proxy.checker.HttpResultCheckFactory;
import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.httpvolleypromiser.utils.GsonHelper;
import com.eleven.jpromiser.core.JPromiser;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eleven on 2016/3/30.
 */
public abstract class BaseProxy {

//    protected final String SERVICE_ERROR_DATA = "service error";
//    public String globalDefaultHost = "";
    protected ProviderContext mPContext;
    //========== setting provider context =====================================
    protected String mCurHost;
//    private String mToken;
    private String mAuthToken;
//    private String mUser;
    private JPromiser.OnPromiseResult<String> mOnSuccessListens;
    private JPromiser.OnPromiseResult<String> mOnErrorListens;
    private JPromiser.OnPromiseThen mOnNotifyListens;


    //========== base common utils ==========

    public void SetProviderContext(ProviderContext providerContext) {
        mPContext = providerContext;
    }

    protected String getHost() {
        if (mCurHost != null)
            return mCurHost;

        if (mPContext != null && mPContext.getHost() != null) {
            return mPContext.getHost();
        }

        return "";
//        return globalDefaultHost;
    }

    protected void initProxy() {
        initProxy(new ProviderContext());
    }

    protected void initProxy(ProviderContext pContext) {
        mPContext = pContext;
    }

//    @Deprecated
//    public boolean checkServiceState(int state) {
//        if(mPContext != null && mPContext.getIHttpStateCheck() != null){
//            return mPContext.getIHttpStateCheck().proxyServiceStateCheck(state);
//        }
//
//        if(HttpResultCheckFactory.getChecker() != null){
//            return HttpResultCheckFactory.getChecker().proxyServiceStateCheck(state);
//        }
//
//        return true;
//    }

    public <T> T resovleJson(String jsonStr, Class<T> t) {
        Gson g = GsonHelper.createGson(getPContext().getDateTimeFormat());
        return g.fromJson(jsonStr, t);
    }

    public void setCurrentHost(String curHost){
        mCurHost = curHost;
    }
//    public void setHeaderTokenAndUser(String token,String user){
//        mToken = token;
//        mUser = user;
//    }
    public void setAuthToken(String authToken){
        mAuthToken = authToken;
    }
    public void setOnPromiseSuccessListens(JPromiser.OnPromiseResult<String> l){
        mOnSuccessListens = l;
    }
    public void setOnPromiseErrorListens(JPromiser.OnPromiseResult<String> l){
        mOnErrorListens = l;
    }
    public void setOnPromiseNotifyListens(JPromiser.OnPromiseThen l){
        mOnNotifyListens = l;
    }

    public ProviderContext getPContext(){
        ProviderContext c;

        if(mPContext != null){
            c = mPContext;
        }else{
            c = new ProviderContext();
        }

//        if(mToken != null && mUser != null){
//            Map<String,String> header = new HashMap<String,String>();
//            header.put("INS-TOKEN",mToken);
//            header.put("INS-USER",mUser);
//            c.setHeaderList(header);
//        }

        if(mAuthToken != null){
            Map<String,String> header ;
            if(c.getHeaderList() == null){
                header = new HashMap<>();
                c.setHeaderList(header);
            }else{
                header = c.getHeaderList();
            }
            header.put("Authorization","Bearer " + mAuthToken);
        }

        if(mOnSuccessListens != null){
            c.setOnPSuccess(mOnSuccessListens);
        }
        if(mOnErrorListens != null){
            c.setOnPError(mOnErrorListens);
        }

        if(mOnNotifyListens != null){
            c.addPNotify(mOnNotifyListens);
        }

        return c;
    }

//    public static class ResBody{
//        public int State;
//        public String Message;
//    }

}
