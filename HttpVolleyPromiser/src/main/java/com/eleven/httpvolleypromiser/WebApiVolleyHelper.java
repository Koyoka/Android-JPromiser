package com.eleven.httpvolleypromiser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eleven.httpvolleypromiser.proxy.checker.HttpResultCheckFactory;
import com.eleven.httpvolleypromiser.proxy.checker.IHttpResultCheck;
import com.eleven.httpvolleypromiser.proxy.checker.IOnHttpCustCheckMessage;
import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.httpvolleypromiser.proxy.base.BaseHttpGetModelDto;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;
import com.eleven.httpvolleypromiser.proxy.base.ProxyStatic;
import com.eleven.httpvolleypromiser.proxy.ProviderHelper;
import com.eleven.httpvolleypromiser.utils.GsonHelper;
import com.eleven.httpvolleypromiser.utils.ProxyLog;
import com.eleven.httpvolleypromiser.proxy.event.IHttpHelperListener;
import com.eleven.jpromiser.core.JPromiser;
import com.eleven.jpromiser.core.Promiser;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 正 on 2016/12/1.
 */
public class WebApiVolleyHelper implements IProxyDataProvider {

    private final String TAG = WebApiVolleyHelper.class.getSimpleName();
    private final int DEFAULT_TIME_OUT = 20 * 1000;
    private final String METHOD_POST = "POST";
    private final String METHOD_GET = "GET";
    private int connTimeOut = DEFAULT_TIME_OUT;

    /**
     * promiser标识:Volley 服务调用出错
     * */
    public static final String VOLLEY_ERROR = "WebApiVolleyHelper.Error";
    /**
     * promiser标识:是否刷新token
     * */
    public static final String NOTIFY_ASTOKEN_EXPIRED = "WebApiVolleyHelper.AccessToken.Expired";
    /**
     * promiser标识:未登录
     * */
    public static final String NOTIFY_ASTOKEN_UNLOGIN = "WebApiVolleyHelper.AccessToken.UnLogin";
    /**开始记录日志*/
    public static final String NOTIFY_LOG_START = "WebApiVolleyHelper.log.start";
    /**成功-日志结束*/
    public static final String NOTIFY_LOG_END_SUCCESS = "WebApiVolleyHelper.log.end.success";
    /**错误-日志结束*/
    public static final String NOTIFY_LOG_END_ERROR = "WebApiVolleyHelper.log.end.error";
    /**
     * promiser标识: 不用刷新token key & value
     * */
    public static final String PROMISS_FRESHTOKEN_STATUS = "WebApiVolleyHelper.freshToken";
    public static final String PROMISS_FRESHTOKEN_STATUS_NON = "WebApiVolleyHelper.freshToken.non";

    public static final String RUNTIME_INJECT_KEY_GET_INPUT = "WebApiVolleyHelper.runtime.getInput";
    public static final String RUNTIME_INJECT_KEY_POST_INPUT = "WebApiVolleyHelper.runtime.postInput";

    private static WebApiVolleyHelper mVolleyHelp;
    private RequestQueue mRequestQueue = null;
    private String mTag;
    public WebApiVolleyHelper(Context context, String tags){
        mRequestQueue = Volley.newRequestQueue(context);
        mTag = tags;
    }

    public void setTimeOut(int second){
        connTimeOut = second * 1000;
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static void initRequestQueue(Context context, String tag){
        if(mVolleyHelp == null){
            mVolleyHelp = new WebApiVolleyHelper(context, tag);
        }
    }

    public static WebApiVolleyHelper sharedRequestQueue() throws NullPointerException{
        if(mVolleyHelp == null || mVolleyHelp.getRequestQueue() == null) {
            throw new NullPointerException("Please init RequestQueue object first!");
        }
        return mVolleyHelp;
    }

    @Override
    public Promiser post(ProviderContext pContext, BaseHttpGetModelDto gInput, BaseModelDto pInput, final IHttpHelperListener l) {
        return buildVolleyPromiser(pContext,
                METHOD_POST,gInput,pInput,
                l);
    }

    @Override
    public Promiser get(ProviderContext pContext, BaseHttpGetModelDto gInput, BaseModelDto pInput, IHttpHelperListener l) {
        return buildVolleyPromiser(pContext,
                METHOD_GET,gInput,pInput,
                l);
    }

    /**
     * 处理错误结果
     */
    private void reject(JPromiser.Deffered<String, String> d, IHttpHelperListener l, int stateCode, String errMessage){
        if(l.onError(stateCode, errMessage)){
            d.resolve();
        }else{
            d.reject(errMessage);

        }
    }

    public void makeRequest(@NonNull Request<?> request){
        mRequestQueue.add(request);
    }

    protected String buildContext(ProviderContext pContext, BaseHttpGetModelDto gInput, String method){
        String defineHost = ProviderHelper.formatHostUrl(pContext,gInput);
        pContext.setMethod(method);
        pContext.setRequestUrl(defineHost);
        return defineHost;
    }

    /**
     * core: 构建Volley调用队列
     * */
    protected Promiser buildVolleyPromiser(final ProviderContext pContext,
                                           final String method,
                                           final BaseHttpGetModelDto gInput,
                                           final BaseModelDto pInput,
                                           final IHttpHelperListener l){

        Promiser $q = new Promiser() {
            @Override
            protected void aync(final Deffered<String, String> d) {

                d.notify(ProxyStatic.PROVIDER_Q_STATE_START,0);
                d.notify(NOTIFY_LOG_START,pContext);

                //region #init build context & pInput

                //region #Operation GetInput
                OnRuntimeInjectListens gInputInject =
                    d.getPromiser().getRuntimeListens(RUNTIME_INJECT_KEY_GET_INPUT);
                if(gInputInject != null
                        && gInputInject.getInjectObject() instanceof BaseHttpGetModelDto){
                    BaseHttpGetModelDto runtimeInjectGInput = (BaseHttpGetModelDto) gInputInject.getInjectObject();
                    buildContext(pContext,runtimeInjectGInput,method);
                }else{
                    buildContext(pContext,gInput,method);
                }
                //endregion

                //region #Operation PostInput
                JSONObject tReqBody = null;
                if(method.equals(METHOD_POST)){

                    OnRuntimeInjectListens pInputInject =
                            d.getPromiser().getRuntimeListens(RUNTIME_INJECT_KEY_POST_INPUT);

                    if(pInputInject != null
                            && pInputInject.getInjectObject() instanceof BaseModelDto){
                        BaseModelDto runtimeInjectPInput = (BaseModelDto) pInputInject.getInjectObject();
                        try {
                            tReqBody = getPostJsonObject(pContext,runtimeInjectPInput);
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            l.onError(-1, e.getMessage());
//                            d.reject(e.getMessage());
                            reject(d, l, -1, e.getMessage());
                            return;
                        }
                    }else{
                        try {
                            tReqBody = getPostJsonObject(pContext,pInput);
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            l.onError(-1, e.getMessage());
//                            d.reject(e.getMessage());
                            reject(d, l, -1, e.getMessage());
                            return;
                        }
                    }
                }

                //endregion

                //endregion

                //region #print debug
                debugPrintRequest(
                        pContext.getRequestUrl(),
                        pContext.getMethod(),
                        "Request",
                        ".................................say something",
                        tReqBody == null? "t is null" : tReqBody.toString(),
                        pContext.getHeaderList());
                //endregion print debug

                //region #create error response listener
                Response.ErrorListener responseE =
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                buildVolleyPromiser_onErrorListener(pContext,l,d,error);
                            }
                        };
                //endregion

                //region #create request by method param
                Request<?> request;
                final Promiser me = this;

                //region #build request
                if(pContext.getMethod().equals(METHOD_POST)){
                    //region #post request
                    // this is json post request
                    final JSONObject jsonObject = tReqBody;
                    assert jsonObject != null;

                    Response.Listener<JSONObject> responseL = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String result = response == null ? "" : response.toString();
                            buildVolleyPromiser_onResponseListener(pContext,l,me,d,result);
                        }
                    };

                    request = new JsonObjectRequest(pContext.getRequestUrl(), jsonObject,responseL,responseE) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> defineHeader = pContext.getHeaderList();
                                if(defineHeader != null){
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.putAll(super.getHeaders());
                                    headers.putAll(defineHeader);
                                    return headers;
                                }
                                return super.getHeaders();
                            }

                            @Override
                            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                                try {
                                    String jsonString = new String(response.data,
                                            HttpHeaderParser.parseCharset(
                                                    response.headers,
                                                    TextUtils.isEmpty(pContext.getCharset())
                                                            ? PROTOCOL_CHARSET
                                                            : pContext.getCharset()
                                            )
                                    );
                                    if(TextUtils.isEmpty(jsonString)){
//                                        BaseProxy.ResBody emptyBody = new BaseProxy.ResBody();
//                                        emptyBody.State = 0;
//                                        emptyBody.Message = "";
                                        jsonString = "{}";//.toJson(emptyBody);
                                    }
                                    return Response.success(new JSONObject(jsonString),
                                            HttpHeaderParser.parseCacheHeaders(response));
                                } catch (UnsupportedEncodingException e) {
                                    return Response.error(new ParseError(e));
                                } catch (JSONException je) {
                                    return Response.error(new ParseError(je));
                                }
                            }
                        };

                    //endregion
                }else{
                    //region #get request
                    // this is string get request
                    Response.Listener<String> responseL = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response;
                            buildVolleyPromiser_onResponseListener(pContext,l,me,d,result);
                        }
                    };
                    request = new StringRequest(Request.Method.GET, pContext.getRequestUrl(),responseL,responseE) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> defineHeader = pContext.getHeaderList();
                            if(defineHeader != null){
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.putAll(super.getHeaders());
                                headers.putAll(defineHeader);
                                return headers;
                            }
                            return super.getHeaders();
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            if(!TextUtils.isEmpty(pContext.getCharset())){
                                //region new charset
                                String parsed;
                                try {
                                    parsed = new String(response.data, pContext.getCharset());
                                } catch (UnsupportedEncodingException e) {
                                    parsed = new String(response.data);
                                }
                                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                                //endregion
                            }else{
                                return super.parseNetworkResponse(response);
                            }

                        }
                    };
                    //endregion
                }
                //endregion

                //endregion

                //region #request setting
                request.setRetryPolicy(new DefaultRetryPolicy(
                        pContext.getTimeOutSecond() == -1 ? connTimeOut : pContext.getTimeOutSecond(),
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //endregion

                request.setShouldCache(pContext.getShouldCache());
                mRequestQueue.add(request);
            }
        };
        promiseManage(pContext,$q);
        return $q;
    }

    /**
     * 构建提交任务的promiser结果响应（外部上下文植入）
     */
    private void promiseManage(ProviderContext pContext, JPromiser<String,String> $q){
        if(pContext != null){
            $q
                    .success(pContext.getOnPSuccess())
                    .error(pContext.getOnPError())
                    .then(pContext.getPNotify());
        }

    }

    /**
     * core: 处理Response success回调
     * */
    protected void buildVolleyPromiser_onResponseListener(ProviderContext pContext,
                                                          final IHttpHelperListener l,
                                                          Promiser me,
                                                          final Promiser.Deffered<String,String> d,
                                                          String result){

        try{

            if(!PROMISS_FRESHTOKEN_STATUS_NON.equals( pContext.getObj(PROMISS_FRESHTOKEN_STATUS))){
                //需要重复刷新token,才需要记录日志
                d.notify(NOTIFY_LOG_END_SUCCESS,pContext.logSuccess(result));
            }

            //region #print debug
            debugPrintRequest(
                    pContext.getRequestUrl(),
                    pContext.getMethod(),
                    "Response",
                    ".................................say something",
                    result,
                    null);
            //endregion print debug

            //region #自定义response通用结果值判断

            IHttpResultCheck custChecker = null;
            if(pContext.getIHttpStateCheck() != null){
                custChecker = pContext.getIHttpStateCheck();
            }else if(HttpResultCheckFactory.getChecker() != null){
                custChecker = HttpResultCheckFactory.getChecker();
            }

            if(custChecker != null){
                boolean custCheckResult =
                        custChecker.httpResultCheck(
                            result,
                            me,
                            pContext,
                            new IOnHttpCustCheckMessage() {
                                @Override
                                public void onCustCheckMessage(int state, String errorMessage) {
//                                    l.onError(state,errorMessage);
//                                    d.reject(errorMessage);
                                    reject(d, l, state, errorMessage);
                                }
                            }
                        );

                if(!custCheckResult){
                    return;
                }
            }
            //endregion

            //region success
            if(l.onSuccess(result)){
                d.resolve(result);
            }else{
                d.reject(result);
            }
//            if(l instanceof IHttpHelperListener_v3){
//                boolean reBoolean =
//                    ((IHttpHelperListener)l).onSuccess(result);
//                if(reBoolean){
//                    d.resolve(result);
//                }else{
//                    d.reject(result);
//                }
//            }else{
//                l.onSuccess(result);
//                d.resolve(result);
//            }
            //endregion

        }catch (JsonParseException ex) {
            ex.printStackTrace();
            ProxyLog.$e(TAG,"buildVolleyPromiser_onResponseListener ERROR [非法的Json格式]!");
            ProxyLog.$e(TAG,"host:"+ pContext.getRequestUrl());
            ProxyLog.$e(TAG, result);
            ProxyLog.$e(TAG,ex.getMessage(),new Throwable(ex.getMessage()));
//            l.onError(-1, ex.getMessage()+" 非法的Json格式:" + result);
//            d.reject(ex.getMessage()+" 非法的Json格式:" + result);
            reject(d, l, -1, ex.getMessage()+" 非法的Json格式:" + result);
        }catch (Exception ex){
            ex.printStackTrace();
            ProxyLog.$e(TAG,"buildVolleyPromiser_onResponseListener ERROR !");
            ProxyLog.$e(TAG,"host:"+ pContext.getRequestUrl());
            ProxyLog.$e(TAG, result);
            ProxyLog.$e(TAG,ex.getMessage(),new Throwable(ex.getMessage()));
//            l.onError(-1, ex.getMessage());
//            d.reject(ex.getMessage());
            reject(d, l, -1, ex.getMessage());
        }
        finally {
            ProxyLog.$e(TAG,"#############################response finally");
            d.notify(ProxyStatic.PROVIDER_Q_STATE_DONE,100);
        }

    }

    /**
     * core: 处理Response error回调
     * */
    protected void buildVolleyPromiser_onErrorListener(ProviderContext pContext,IHttpHelperListener l,Promiser.Deffered<String,String> d,VolleyError error){

        try{
            String errMsg = ErrorHelper.getMessage(error);
            int stateCode =
                    ((error != null) && (error.networkResponse != null))
                            ? error.networkResponse.statusCode
                            : -1;
            d.notify(NOTIFY_LOG_END_ERROR,pContext.logError(stateCode,errMsg));

            //region #print debug
            debugPrintRequest(
                    pContext.getRequestUrl(),
                    pContext.getMethod(),
                    "onErrorResponse",
                    "error stateCode:" + stateCode,
                    errMsg,
                    null);
            //endregion print debug

            d.notify(VOLLEY_ERROR,l);
//            l.onError(stateCode, errMsg);
//            d.reject(errMsg);
            reject(d, l, stateCode, errMsg);
        }catch (Exception ex){
            ex.printStackTrace();
            ProxyLog.$e(TAG,ex.getMessage(),new Throwable(ex.getMessage()));
//            l.onError(-1, ex.getMessage());
//            d.reject(ex.getMessage());
            reject(d, l, -1, ex.getMessage());
        }
        finally {
            d.notify(ProxyStatic.PROVIDER_Q_STATE_DONE,100);
        }

    }

    protected JSONObject getPostJsonObject(ProviderContext pContext,BaseModelDto pInput) throws JSONException {

        String input = "";
        if(pInput != null) {
            input = GsonHelper.createGson(pContext.getDateTimeFormat()).toJson(pInput);
        }

        JSONObject jsonObject;
        if(input.length() != 0){
            jsonObject = new JSONObject(input);
        }else{
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

//    /**
//     * http 服务业务状态码判断
//     * @param result
//     * @param me
//     * @param pContext
//     * @return
//     */
//    protected boolean httpTokenCheck(String result, final Promiser.Deffered<String,String> d,
//                                     JPromiser me, ProviderContext pContext){
//        IOnHttpCustCheckMessage onCustCheckMessage =
//            new IOnHttpCustCheckMessage() {
//                @Override
//                public void onCustCheckMessage(String errorMessage) {
//                    d.reject(errorMessage);
//                }
//            };
//        if(pContext.getIHttpStateCheck() != null){
//            return pContext.getIHttpStateCheck().httpTokenCheck(
//                    result,
//                    me,
//                    pContext,
//                    onCustCheckMessage
//            );
//        }
//        return HttpStateCheckFactory.getChecker().httpTokenCheck(
//                result,
//                me,
//                pContext
//                onCustCheckMessage
//        );
//    }

    protected void debugPrintRequest(String host,String method,String action,String desc,String printData,Map<String, String> header){
        if(!ProxyLog.getBuildConfigDebug()){
            return;
        }

        ArrayList<String> logStr = new ArrayList<>();
        logStr.add("┌─ " + action + " ─────────────────────────────────┐");
        logStr.add("│ " + desc);
        logStr.add("│ method:" + method);
        logStr.add("│ host  :" + host);
        if(header != null && header.size() != 0){
            for (Map.Entry<String,String> entry : header.entrySet()) {
                String value = entry.getValue();
                String key = entry.getKey();
                logStr.add("│ header_key:" + key + " header_value:" + value);
            }
//            for (String key : header.keySet()) {
//                String value = header.get(key);
//                logStr.add("│ header_key:" + key + " header_value:" + value);
//            }
        }else{
            logStr.add("│ header: null");
        }
        logStr.add("│ " + printData);
        logStr.add("└────────────────────────────────────────┘");

        ProxyLog.e(TAG, "<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        for (String s :
                logStr) {
            ProxyLog.e(TAG, s);
        }

    }

    public static class ErrorHelper{

        public static final String NO_INTERNET = "无网络连接";
        public static final String GENERIC_SERVER_DOWN = "连接服务器失败";
        public static final String GENERIC_ERROR = "网络异常,请稍后再试";

        ErrorHelper(){}

        public static String getMessage(Object error) {
            if(error == null) {
                return GENERIC_SERVER_DOWN;
            }

            if (error instanceof TimeoutError) {
                return "TimeoutError: "+ GENERIC_SERVER_DOWN;
            } else if (isServerProblem(error)) {
                String errMsg = handleServerError(error);
                return errMsg!=null?errMsg: GENERIC_SERVER_DOWN;
            } else if (isNetworkProblem(error)) {
                String msg = "";
                if(error instanceof NetworkError){
                    msg = "NetworkError: "+((NetworkError)error).getMessage();
                }

                if(error instanceof NoConnectionError){
                    msg = "NoConnectionError: "+((NoConnectionError)error).getMessage();
                }
                return NO_INTERNET + ":"+msg;
            }
            return GENERIC_ERROR;
        }

        private static boolean isNetworkProblem(Object error) {
            return (error instanceof NetworkError)
                    || (error instanceof NoConnectionError);
        }

        private static boolean isServerProblem(Object error) {
            return (error instanceof ServerError)
                    || (error instanceof AuthFailureError);
        }

        private static String handleServerError(Object err) {
            VolleyError error = (VolleyError) err;

            NetworkResponse response = error.networkResponse;

            if(response == null){
                return GENERIC_ERROR;
            }
            switch (response.statusCode) {
                case 404:
                case 422:
                case 401:
                    try {
                        // server might return error like this { "error":
                        // "Some error occured" }
                        // Use "Gson" to parse the result
                        HashMap<String, String> result = new Gson().fromJson(
                                new String(response.data,"utf-8"),
                                new TypeToken<Map<String, String>>() {
                                }.getType());

                        if (result != null && result.containsKey("error")) {
                            return result.get("error");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return error.getMessage();

                default:
                    return "code:" + response.statusCode + " " + GENERIC_SERVER_DOWN;
            }

        }
    }

}
