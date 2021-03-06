package com.eleven.httpvolleypromiser.proxy.user.loginTwoProxy;

import java.util.ArrayList;

import com.eleven.httpvolleypromiser.proxy.event.IHttpHelperListener;
import com.eleven.httpvolleypromiser.proxy.event.IOnProxyDoneForStringListener;
import com.eleven.httpvolleypromiser.proxy.event.IOnProxyDoneListener;
import com.eleven.jpromiser.core.Promiser;
import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.httpvolleypromiser.proxy.base.BaseProxy;
import com.eleven.httpvolleypromiser.IProxyDataProvider;



public class LoginTwoProxyApi extends BaseProxy {

    private String apiUrl = "api/v1/user/1/Sys/Login/{sUserID}/{sPassword}";
    private String method = "GET";

    public LoginTwoProxyApi(){
        initProxy();
    }

    public LoginTwoProxyApi(ProviderContext pContext){
        initProxy(pContext);
    }

    //region public void doRequest
    public void doRequest(
        IProxyDataProvider provider,
        LoginTwoGInput gInput,
        LoginTwoPInput pInput,
        final IOnProxyDoneListener<LoginTwoOutput> onListener){

        Promiser $q = $doRequest(provider,
        gInput,
        pInput,
        onListener);
        if($q != null)
            $q.run();
    }

    //endregion


    //region public void $doRequestForString
    public Promiser $doRequestForString(
        IProxyDataProvider provider,
        LoginTwoGInput gInput,
        LoginTwoPInput pInput,
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
                                    gInput,
                                    pInput,
                                    l);
            }else{
                $q = provider.post(pContext,
                                    gInput,
                                    pInput,
                                    l);
            }

            onListener.setPromiser($q);
            return $q;

        }

        //endregion

        //region public Promiser $doRequest
        public Promiser $doRequest(
            IProxyDataProvider provider,
            LoginTwoGInput gInput,
            LoginTwoPInput pInput,
            final IOnProxyDoneListener<LoginTwoOutput> onListener){

            String url = getApiURL();
            ProviderContext pContext = getPContext();
            pContext.setHost(url);

            IHttpHelperListener l = new IHttpHelperListener() {

                @Override
                public boolean onSuccess(String result) {

                    LoginTwoOutput output = resovleJson(result, LoginTwoOutput.class);
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
                                    gInput,
                                    pInput,
                                    l);
            }else{
                $q = provider.post(pContext,
                                  gInput,
                                  pInput,
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