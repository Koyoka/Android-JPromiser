package com.eleven.httpvolleypromiser.proxy.checker;


import android.support.annotation.NonNull;

import com.eleven.httpvolleypromiser.proxy.ProviderContext;
import com.eleven.jpromiser.core.JPromiser;

/**
 * Created by 正 on 2017/2/16.
 */

public interface IHttpResultCheck {

    boolean httpResultCheck(String result, JPromiser me, ProviderContext pContext, @NonNull IOnHttpCustCheckMessage onHttpCustCheckMessage);

//    /**
//     * 代理中，检查state代码是否成功
//     * 成功：提供代理直接返回body
//     * 失败：处理{message}信息
//     * @param state
//     * @return
//     */
//    @Deprecated
//    boolean proxyServiceStateCheck(int state);
}
