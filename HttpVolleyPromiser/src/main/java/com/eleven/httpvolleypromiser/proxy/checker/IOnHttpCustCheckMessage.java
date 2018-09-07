package com.eleven.httpvolleypromiser.proxy.checker;

public interface IOnHttpCustCheckMessage {
    void onCustCheckMessage(int state, String errorMessage);
}
