package com.east.architect_zenghui.architect_25_okhttp3.okhttp;

/**
 * Created by hcDarren on 2017/11/18.
 * Call
 */

public interface Call {
    void enqueue(Callback callback);

    Response execute();
}
