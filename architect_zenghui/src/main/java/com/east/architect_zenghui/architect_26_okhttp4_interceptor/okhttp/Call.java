package com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp;

/**
 * Created by hcDarren on 2017/11/18.
 * Call
 */

public interface Call {
    void enqueue(Callback callback);

    Response execute();
}
