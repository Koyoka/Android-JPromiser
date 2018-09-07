package com.eleven.jpromiserdemo.proxy.rawJsonTest.productProxy;

import java.util.ArrayList;

import com.eleven.httpvolleypromiser.proxy.event.IHttpHelperListener;
import com.eleven.httpvolleypromiser.proxy.event.IOnProxyDoneForStringListener;
import com.eleven.httpvolleypromiser.proxy.event.IOnProxyDoneListener;
import com.eleven.jpromiser.core.Promiser;
import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.httpvolleypromiser.proxy.base.BaseProxy;
import com.eleven.httpvolleypromiser.IProxyDataProvider;



public class ProductProxyApi extends BaseProxy {

    private String apiUrl = "Android-JPromiser/master/rawJson/product.json";
    private String method = "GET";

    public ProductProxyApi(){
        initProxy();
    }

    public ProductProxyApi(ProviderContext pContext){
        initProxy(pContext);
    }

    //region public void doRequest
    public void doRequest(
        IProxyDataProvider provider,
        final IOnProxyDoneListener<ProductOutput> onListener){

        Promiser $q = $doRequest(provider,
        onListener);
        if($q != null)
            $q.run();
    }

    //endregion


    //region public void $doRequestForString
    public Promiser $doRequestForString(
        IProxyDataProvider provider,
        final IOnProxyDoneForStringListener onListener){
            String url = getApiURL();
            ProviderContext pContext = getPContext();
            pContext.setHost(url);

            IHttpHelperListener l = new IHttpHelperListener() {

                @Override
                public boolean onSuccess(String result) {

                    onListener.success(result);
                    onListener.done();
                    return true;
                }

                @Override
                public boolean onError(int stateCode, String message) {
                    boolean exResolve = onListener.error(stateCode, message);
                    onListener.done();
                    return exResolve;
                }

            };

            Promiser $q;
            if(method.trim().toUpperCase().equals("GET")){
                $q = provider.get(pContext,
                                    null,
                                    null,
                                    l);
            }else{
                $q = provider.post(pContext,
                                    null,
                                    null,
                                    l);
            }

            onListener.setPromiser($q);
            return $q;

        }

        //endregion

        //region public Promiser $doRequest
        public Promiser $doRequest(
            IProxyDataProvider provider,
            final IOnProxyDoneListener<ProductOutput> onListener){

            String url = getApiURL();
            ProviderContext pContext = getPContext();
            pContext.setHost(url);

            IHttpHelperListener l = new IHttpHelperListener() {

                @Override
                public boolean onSuccess(String result) {

                    ProductOutput output = resovleJson(result, ProductOutput.class);
                    onListener.success(output);
                    onListener.done();
                    return true;
                }

                @Override
                public boolean onError(int stateCode, String message) {
                    boolean exResolve = onListener.error(stateCode, message);
                    onListener.done();
                    return exResolve;
                }
            };


            Promiser $q;
            if(method.trim().toUpperCase().equals("GET")){
                $q = provider.get(pContext,
                                    null,
                                    null,
                                    l);
            }else{
                $q = provider.post(pContext,
                                  null,
                                  null,
                                  l);
            }

            onListener.setPromiser($q);
            return $q;

        }

        //endregion

        //region public String getApiURL
        public String getApiURL(){
            return getHost() + apiUrl;
        }
        //endregion
}