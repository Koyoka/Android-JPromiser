package com.eleven.httpvolleypromiser.proxy;


import com.eleven.httpvolleypromiser.ProxyStartUp;
import com.eleven.httpvolleypromiser.proxy.checker.IHttpResultCheck;
import com.eleven.jpromiser.core.JPromiser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eleven on 2016/4/1.
 */
public class ProviderContext {

    private Map<String,Object> objs;
    private String host;
    private String requestUrl;
    private String method;
    private Map<String,String> headerList;
    private JPromiser.OnPromiseResult<String> onPSuccess;
    private JPromiser.OnPromiseResult<String> onPError;
    private ArrayList<JPromiser.OnPromiseThen> onPNotifys;
    private int timeOutSecond = -1;
    private String charset = null;
    private String dateTimeFormat = ProxyStartUp.DATETIME_FORMAT_HTTP_YMD_HMS;
    public String getCharset(){
        return this.charset;
    }
    public void setCharset(String c){
        this.charset = c;
    }


    //region customer IHttpStateCheck

    private IHttpResultCheck mIHttpStateCheck;

    public IHttpResultCheck getIHttpStateCheck() {
        return mIHttpStateCheck;
    }

    public void setIHttpStateCheck(IHttpResultCheck mIHttpStateCheck) {
        this.mIHttpStateCheck = mIHttpStateCheck;
    }
    //endregion

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    private boolean shouldCache = true;

    public boolean getShouldCache() {
        return shouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
    }

    public void setTimeOutSecond(int timeOutSecond){
        this.timeOutSecond = timeOutSecond * 1000;
    }

    public int getTimeOutSecond(){
        return this.timeOutSecond;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Map<String, String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(Map<String, String> headerList) {
        this.headerList = headerList;
    }

    public JPromiser.OnPromiseResult<String> getOnPSuccess() {
        return onPSuccess;
    }

    public void setOnPSuccess(JPromiser.OnPromiseResult<String> onPSuccess) {
        this.onPSuccess = onPSuccess;
    }

    public JPromiser.OnPromiseResult<String> getOnPError() {
        return onPError;
    }

    public void setOnPError(JPromiser.OnPromiseResult<String> onPError) {
        this.onPError = onPError;
    }

    public void addPNotify(JPromiser.OnPromiseThen onPNotify){

        if(onPNotifys == null){
            onPNotifys = new ArrayList<>();
        }
        onPNotifys.add(onPNotify);
    }
    public JPromiser.OnPromiseThen[] getPNotify(){
        if(onPNotifys == null)
            return null;

        return onPNotifys.toArray(new JPromiser.OnPromiseThen[onPNotifys.size()]);
    }

    public void setObj(String key,Object obj){
        if(objs == null)
            objs = new HashMap<>();
        objs.put(key,obj);
    }

    public Object getObj(String key){
        if(objs == null)
            return null;
        return objs.get(key);

    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    //================log
    public ProviderContext logSuccess(String result){

        if(result!=null){
            try {
                int len = result.getBytes("utf-8").length;
                getLog().setSize(len);
                getLog().logMessage("S",200,"result size:"+len);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        return this;
    }
    public ProviderContext logError(int code,String errMsg){
        getLog().logMessage("E",code,errMsg);
        return this;
    }
    private ProviderLog mProviderLog;
    public ProviderLog getLog(){
        if(mProviderLog == null){
            mProviderLog = new ProviderLog(requestUrl,method);
        }
        return mProviderLog;
    }

    public static class ProviderLog{
        private long startTime;
        private long endTime;
        private long totalTime;
        private ArrayList<ProviderLogModel> logList;
        private String requestUrl;
        private String method;
        private String user;
        private int size;
        private String state;

        public String getState() {
            return state;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public ArrayList<ProviderLogModel> getLogList() {
            return logList;
        }

        public String getRequestUrl() {
            return requestUrl;
        }

        public String getMethod() {
            return method;
        }

        public String getUser() {
            return user;
        }

        public ProviderLog(String requestUrl, String method){
            logList = new ArrayList<>();
            this.requestUrl = requestUrl;
            this.method = method;
        }
        public void start(String user){
            this.user = user;
            startTime = System.currentTimeMillis();
        }
        public void logMessage(String state,int code,String msg){

            ProviderLogModel m = new ProviderLogModel();
            m.setState(state);
            m.setCode(code);
            m.setMessage(msg);
            logList.add(m);
        }
        public ProviderLog success(){
            state = "S";
            end();
            return this;
        }
        public ProviderLog error(){
            state = "E";
            end();
            return this;
        }
        private void end(){
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
        }

    }
    public static  class ProviderLogModel{
        private String state;
        private int code;
        private String message;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    //region query string
    public Map<String,String> queryString;

    /**
     * 设置queryString键值对
     * @param key
     * @param value
     */
    public ProviderContext putQueryString(String key,String value){
        if(queryString == null){
            queryString = new HashMap<>();
        }
        queryString.put(key,value);
        return this;
    }

    /**
     * 清除queryString的键值对
     */
    public ProviderContext clearQueryString(){
        queryString.clear();
        queryString = null;
        return this;
    }
    //endregion
}
