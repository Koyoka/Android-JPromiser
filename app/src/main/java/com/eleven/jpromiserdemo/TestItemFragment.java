package com.eleven.jpromiserdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.eleven.httpvolleypromiser.WebApiVolleyHelper;
import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.httpvolleypromiser.proxy.checker.IHttpResultCheck;
import com.eleven.httpvolleypromiser.proxy.checker.IOnHttpCustCheckMessage;
import com.eleven.httpvolleypromiser.proxy.event.IOnProxyDoneListener;
import com.eleven.httpvolleypromiser.utils.GsonHelper;
import com.eleven.httpvolleypromiser.utils.ProxyLog;
import com.eleven.jpromiser.blockUI.JBlockUI;
import com.eleven.jpromiser.core.JPromiser;
import com.eleven.jpromiser.core.Promiser;
import com.eleven.jpromiserdemo.proxy.MyPContext;
import com.eleven.jpromiserdemo.proxy.rawJsonTest.productErrorProxy.ProductErrorOutput;
import com.eleven.jpromiserdemo.proxy.rawJsonTest.productErrorProxy.ProductErrorProxyApi;
import com.eleven.jpromiserdemo.proxy.rawJsonTest.productProxy.ProductOutput;
import com.eleven.jpromiserdemo.proxy.rawJsonTest.productProxy.ProductProxyApi;
import com.eleven.rvadapter.extend.AutoTestItemFragment;

import org.json.JSONException;
import org.json.JSONObject;


public class TestItemFragment extends AutoTestItemFragment {

    private static class MyHandle<T,U> extends Handler{
        JPromiser.Deffered<T, U> mD;
        MyHandle(JPromiser.Deffered<T, U> d){
            mD = d;
        }

        @Override
        public void handleMessage(Message msg) {
            mD.reject();
        }
    }

    private void testBlockUI(){

        ProductProxyApi proxy = new ProductProxyApi(MyPContext.newInstance());
        Promiser $q = proxy.$doRequest(WebApiVolleyHelper.sharedRequestQueue(), new IOnProxyDoneListener<ProductOutput>() {
            @Override
            public boolean error(int code, String message) {
                return false;
            }

            @Override
            public void success(ProductOutput output) {
                String s = GsonHelper.createGson().toJson(output);
                ProxyLog.e("eleven", s);
            }
        });

        JBlockUI.newInstance()
                .$dialog(this,
                        $q);
    }
    private void testBlockUIError(){

        ProductErrorProxyApi proxy = new ProductErrorProxyApi(MyPContext.newInstance());
        /**
         * 自定义response检查
         */
        proxy.getPContext().setIHttpStateCheck((result, me, pContext, onHttpCustCheckMessage) -> {

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

                if(state == 0 || state == 40001){
                    return true;
                }
                onHttpCustCheckMessage.onCustCheckMessage(state, errMsg);
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                onHttpCustCheckMessage.onCustCheckMessage(-1, e.getMessage());
                return false;
            }
        });

        Promiser task1 = proxy.$doRequest(WebApiVolleyHelper.sharedRequestQueue(), new IOnProxyDoneListener<ProductErrorOutput>() {
            @Override
            public boolean error(int code, String message) {
                Toast.makeText(getActivity(), "code:" + code + " errMsg:" + message, Toast.LENGTH_SHORT)
                        .show();
                return true;
            }

            @Override
            public void success(ProductErrorOutput output) {
                String s = GsonHelper.createGson().toJson(output);
                ProxyLog.e("eleven", s);

            }
        });
        Promiser task2 = new Promiser() {
            @Override
            protected void aync(Deffered<String, String> d) {
                ProxyLog.e("eleven", "测试是否继续by error return true");
                d.resolve();
            }
        };

        JBlockUI.newInstance().$dialog(this,
                task1,task2);
    }
    @Override
    protected TestFuncs buildTestFuncs() {

        return new TestFuncs() {
            @Override
            public void testItem() {

                addd("测试Toast", () -> {
                    Intent intent = new Intent(getActivity(), Main2Activity.class);
                    startActivity(intent);
                });

                addd("BlockUI-Product-API", () -> {
                    testBlockUI();
                });

                addd("BlockUI-ProductError-API", () -> {
                    testBlockUIError();
                });

            }
        };
    }
}