package com.eleven.jpromiser.core;

import android.util.Log;


import com.eleven.jpromiser.DebugWatcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Eleven on 2016/4/1.
 */
public abstract class JPromiser<T,U> {
    public static boolean DEBUG_SHOW_STACK = false;

    private int runTimes = 0;
    public int getRunTimes(){
        return runTimes;
    }
    private String debugStr;
    public void setDebugStr(String s){
        debugStr = s;
    }
    public String getDebugStr(){
        return debugStr;
    }

    public interface OnPromiseResult<U1>{
        void onResult(U1 result);
    }
    public interface OnPromiseThen{
        void onResult(String key, Object obj);
    }

    //region promiser 钱包观察者
    private static abstract class OnWallet{
        JPromiser $q;
        public void setPromiser(JPromiser q){
            $q = q;
        }
        public JPromiser getPromiser(){
            return $q;
        }
    }
    public static abstract class OnWalletResult<U1> extends OnWallet implements OnPromiseResult<U1> {

    }
    public static abstract class OnWalletThen extends OnWallet implements OnPromiseThen{

    }

    private void checkWalletWatcher(OnPromiseResult onPResult){
        if(onPResult instanceof OnWallet){
            ((OnWallet)onPResult).setPromiser(this);
        }
    }
    private void checkWalletWatcher(OnPromiseThen onPThen){
        if(onPThen instanceof OnWallet){
            ((OnWallet)onPThen).setPromiser(this);
        }
    }
    //endregion

    //sync queue
    private ArrayList<OnPromiseResult<T>> mResolveQueue;
    private ArrayList<OnPromiseResult<U>> mRejectQueue;
    private ArrayList<OnPromiseThen> mNotifyQueue;

    private OnPromiseResult<T> mResolve;
    private OnPromiseResult<U> mReject;
    private OnPromiseThen mNotify;

    private OnPromiseResult<T> mAfterResolve;
    private OnPromiseResult<U> mAfterReject;
    private OnPromiseThen mAfterNotify;
    protected Deffered<T,U> mDeffered;


    String trackDebug = "";
    public JPromiser(){
        if(DEBUG_SHOW_STACK){
            trackDebug = DebugWatcher.fullStack("Promiser-Track","new & run JPromiser Stack!!!");
        }

        mDeffered = new Deffered<T,U>(this);
    }

    public JPromiser<T,U> run(OnPromiseThen then,OnPromiseResult<T> success,OnPromiseResult<U> error){
        if(DEBUG_SHOW_STACK){
            try{
                Log.i("Promiser-Track", trackDebug);
            }catch (Exception ex){
                System.out.print(trackDebug);
            }
        }
        mNotify = then;
        mResolve = success;
        mReject = error;
        runTimes++;
        aync(mDeffered);
        return this;
    }
    public JPromiser<T,U> runAfter(OnPromiseThen then,OnPromiseResult<T> success,OnPromiseResult<U> error){
        if(DEBUG_SHOW_STACK){
            try{
                Log.i("Promiser-Track", trackDebug);
            }catch (Exception ex){
                System.out.print(trackDebug);
            }
        }
        mAfterNotify = then;
        mAfterResolve = success;
        mAfterReject = error;
        runTimes++;
        aync(mDeffered);
        return this;
    }
    public void run(){
        if(DEBUG_SHOW_STACK){
            try{
                Log.i("Promiser-Track", trackDebug);
            }catch (Exception ex){
                System.out.print(trackDebug);
            }
        }
        runTimes++;
        aync(mDeffered);
    }

    private void callThen(String s,Object obj){
        if(mNotify != null) {
            checkWalletWatcher(mNotify);
            mNotify.onResult(s, obj);
        }

        //call sync queue
        if(mNotifyQueue != null){
            for (OnPromiseThen item:
                    mNotifyQueue) {
                checkWalletWatcher(item);
                item.onResult(s,obj);
            }
        }

        if(mAfterNotify != null) {
            checkWalletWatcher(mAfterNotify);
            mAfterNotify.onResult(s, obj);
        }
    }
    private void callResolve(T s){
        if(mResolve != null){
            checkWalletWatcher(mResolve);
            mResolve.onResult(s);
        }

        //call sync queue
        if(mResolveQueue != null){
            for (OnPromiseResult<T> item :
                    mResolveQueue) {
                checkWalletWatcher(item);
                item.onResult(s);
            }
        }
        if(mAfterResolve != null){
            checkWalletWatcher(mAfterResolve);
            mAfterResolve.onResult(s);
        }


    }
    private void callReject(U s){
        if(mReject != null){
            checkWalletWatcher(mReject);
            mReject.onResult(s);
        }
        //call sync queue
        if(mRejectQueue != null){
            for (OnPromiseResult<U> item :
                    mRejectQueue) {
                checkWalletWatcher(item);
                item.onResult(s);
            }
        }

        if(mAfterReject != null){
            checkWalletWatcher(mAfterReject);
            mAfterReject.onResult(s);
        }
    }

    public JPromiser<T,U> success(OnPromiseResult<T> s){
        if(s != null){
            if(mResolveQueue == null){
                mResolveQueue = new ArrayList<>();
            }
            mResolveQueue.add(s);
        }
        return this;
    }
    public JPromiser<T,U> error(OnPromiseResult<U> s){
        if(s != null){
            if(mRejectQueue == null){
                mRejectQueue = new ArrayList<>();
            }
            mRejectQueue.add(s);
        }
        return this;
    }
    public JPromiser<T,U> then(OnPromiseThen... ss){
        if(ss != null){

            for (OnPromiseThen s : ss) {
                if(s != null){

                    if(mNotifyQueue == null){
                        mNotifyQueue = new ArrayList<>();
                    }

                    mNotifyQueue.add(s);
                }
            }
        }

        return this;
    }

    protected abstract void aync(Deffered<T,U> d);

    public static class Deffered<T,U>{
        JPromiser<T,U> mJPromiser;
        public Deffered(JPromiser<T,U> p){
            mJPromiser = p;
        }

        public void resolve(T s){
            mJPromiser.callResolve(s);
        }
        public void resolve(){
            mJPromiser.callResolve(null);
        }

        public void reject(U s){
            mJPromiser.callReject(s);
        }
        public void reject(){
            mJPromiser.callReject(null);
        }

        public void notify(String s,Object obj){
            mJPromiser.callThen(s,obj);
        }

        public void notify(String s){
            mJPromiser.callThen(s,null);
        }

        public JPromiser getPromiser(){
            return mJPromiser;
        }

        public JWallet getWallet(){
            if(mJPromiser==null) return null;
            return mJPromiser.getWallet();
        }
    }

    //region #runtime
    Map<String,OnRuntimeInjectListens> mRuntimeInjecter;
    public JPromiser<T, U> setRunTimeListens(String key,OnRuntimeInjectListens l){
        if(mRuntimeInjecter == null){
            mRuntimeInjecter = new HashMap<>();
        }
        mRuntimeInjecter.put(key,l);
        return this;
    }

    public OnRuntimeInjectListens getRuntimeListens(String key){
        if(mRuntimeInjecter == null)
            return null;
        return mRuntimeInjecter.get(key);
    }
    public interface OnRuntimeInjectListens<T>{
        T getInjectObject();
    }
    //endregion

    //region #wallet

    protected JWallet mJWallet;
    public static class JWallet{
        private String walletId;
        public String getId(){
            return walletId==null?"":walletId;
        }
        //region debug
        public ArrayList<String> debugInfo;
        private void putDebugInfo(String message){
            if(debugInfo == null){
                debugInfo = new ArrayList<>();
            }
            debugInfo.add(message);
        }
        private void putDebugInfoBefore(ArrayList<String> info){
            if(debugInfo == null){
                debugInfo = new ArrayList<>();
            }
            debugInfo.addAll(0,info);
        }
        public void debugPrint(){

            if(debugInfo != null){
                for (String msg :
                        debugInfo) {
                    try{
                        Log.i("Promiser-WalletData", msg);
                    }catch (Exception ex){
                        System.out.print(msg);
                    }

                }
            }
        }
        //endregion
        public JWallet(){

            walletId = UUID.randomUUID().toString();
            mEveryBodyBag = new HashMap<String,Object>(){
                @Override
                public Object put(String key, Object value) {

                    if(DEBUG_SHOW_STACK){
                        //region 代码定位
                        String message = "KEY:["+key+"]   TYPE:[" +
                                (value==null
                                        ? "null"
                                        : value.getClass()
                                )  + "]";
                        String callMethod = "com.eleven.jpromiser.core.JPromiser$JWallet.putExtra";
                        String transInfo =
                                DebugWatcher.codeTrack("Promiser-WalletData", message, callMethod, 1,true);
                        try{
                            Log.i("Promiser-WalletData", transInfo);
                        }catch (Exception ex){
                            System.out.print(transInfo);
                        }

                        putDebugInfo(transInfo);
                        //endregion
                    }

                    return super.put(key, value);
                }
            };
        }
        public Map<String,Object> mEveryBodyBag;
        //region put&get
        public JWallet putExtra(String key,int value){
            mEveryBodyBag.put(key, Integer.valueOf(value));
            return this;
        }
        public JWallet putExtra(String key,double value){
            mEveryBodyBag.put(key, Double.valueOf(value));
            return this;
        }
        public JWallet putExtra(String key,float value){
            mEveryBodyBag.put(key, Float.valueOf(value));
            return this;
        }
        public JWallet putExtra(String key,boolean value){
            mEveryBodyBag.put(key, Boolean.valueOf(value));
            return this;
        }
        public JWallet putExtra(String key,String value){
            mEveryBodyBag.put(key,value);
            return this;
        }
        public JWallet putExtra(String key, Serializable serializable){
            mEveryBodyBag.put(key,serializable);
            return this;
        }
        public JWallet putExtra(String key,Object obj){
            mEveryBodyBag.put(key,obj);
            return this;
        }

        //region safe get
        public <T extends Object> T getSerializable(String key,Class<T> clz) {
            if(!mEveryBodyBag.containsKey(key))
                return null;
            else {
                Object obj = mEveryBodyBag.get(key);
                if(obj == null){
                    return null;
                }
                if(clz.isInstance(obj)){
                    return (T) obj;
                }else{
                    return null;
                }
            }

        }
        public <T> ArrayList<T> getSerializableArray(String key,Class<T> clz){
            if(!mEveryBodyBag.containsKey(key))
                return new ArrayList<>();

            //region find
            Object obj = mEveryBodyBag.get(key);
            if(obj == null){
                return new ArrayList<>();
            }

            if(obj instanceof ArrayList<?>){
                ArrayList<T> list = new ArrayList<>();
                for(Object item : (ArrayList<?>)obj){
                    if (item != null && item.getClass() == clz){
                        list.add(clz.cast(item));
                    }
                }
                return list;
            }
            //endregion
            return new ArrayList<>();
        }

        public Integer getSafeInt(String key){
            try {
                return getInt(key);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public Double getSafeDouble(String key){
            try {
                return getDouble(key);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public Float getSafeFloat(String key){
            try {
                return getFloat(key);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public Boolean getSafeBoolean(String key){
            try {
                return getBoolean(key);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public String getSafeString(String key){
            try {
                return getString(key);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //endregion

        public Integer getInt(String key) throws Exception {
            return get(key,Integer.class);
        }
        public Double getDouble(String key) throws Exception {
            return get(key,Double.class);
        }
        public Float getFloat(String key) throws Exception {
            return get(key,Float.class);
        }
        public Boolean getBoolean(String key) throws Exception {
            return get(key,Boolean.class);
        }
        public String getString(String key) throws Exception {
            return get(key,String.class);
        }
        public Serializable getSerializable(String key) throws Exception {
            return get(key,Serializable.class);
        }

        public Object getObject(String key){
            return mEveryBodyBag.get(key);
        }

        private <T extends Object> T get(String key,Class<T> clz) throws Exception {
            if(!mEveryBodyBag.containsKey(key))
                return null;
            else {
                Object obj = mEveryBodyBag.get(key);
                if(obj == null){
                    return null;
                }
                if(clz.isInstance(obj)){
                    return (T) obj;
                }else{
                    throw new Exception("key:["+key+"] is not " + clz.getName() + "!!! " + obj);
                }
            }
        }
        //endregion

        public JWallet merge(JWallet wallet){
            if(wallet != null && wallet.mEveryBodyBag != null) {
                mEveryBodyBag.putAll(wallet.mEveryBodyBag);
            }
            if(wallet != null && wallet.debugInfo != null){
                this.putDebugInfoBefore(wallet.debugInfo);
            }
            return this;
        }
    }
    private void initWallet(){
        if(mJWallet == null){
            mJWallet = new JWallet();
        }
    }
    public JWallet getWallet(){
        initWallet();
        return mJWallet;
    }
    public JPromiser<T, U> giveWallet(JWallet otherWallet){
        if(mJWallet != null){
            mJWallet = otherWallet.merge(mJWallet);
        }else{
            mJWallet = otherWallet;
        }
        return this;
    }

    protected String myNiceName;
    public void setName(String promiserName){
        myNiceName = promiserName;
    }
    //endregion

}
