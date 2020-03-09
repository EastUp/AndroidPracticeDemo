package com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor;


import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.Request;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.Response;

import java.io.IOException;

/**
 * Created by hcDarren on 2017/11/19.
 */

public interface Interceptor {
    Response intercept(Chain chain) throws IOException;
    interface Chain {
        Request request();

        Response proceed(Request request) throws IOException;
    }
}
