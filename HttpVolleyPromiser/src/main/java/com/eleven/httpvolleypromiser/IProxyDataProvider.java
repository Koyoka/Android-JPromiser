package com.eleven.httpvolleypromiser;


import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.httpvolleypromiser.proxy.base.BaseHttpGetModelDto;
import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;
import com.eleven.httpvolleypromiser.proxy.event.IHttpHelperListener;
import com.eleven.jpromiser.core.Promiser;

/**
 * Created by Eleven on 2016/4/13.
 */
public interface IProxyDataProvider {
    Promiser post(ProviderContext pContext, BaseHttpGetModelDto gInput, BaseModelDto pInput, final IHttpHelperListener l);
    Promiser get(ProviderContext pContext, BaseHttpGetModelDto gInput, BaseModelDto pInput, final IHttpHelperListener l);
}
