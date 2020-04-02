package com.east.architect_zenghui.architect_36_retrofit_optimize.simple5;

import android.content.Context;

import com.east.architect_zenghui.architect_36_retrofit_optimize.simple7.EngineCallback;

import java.util.Map;

/**
 * Created by hcDarren on 2017/8/26.
 */

public interface IHttpRequest {
    void get(Context context, String url, Map<String, Object> params,
             final EngineCallback callback, final boolean cache);

    void post(Context context, String url, Map<String, Object> params,
              final EngineCallback callback, final boolean cache);

    void download(Context context, String url, Map<String, Object> params,
                  final EngineCallback callback);

    void upload(Context context, String url, Map<String, Object> params,
                final EngineCallback callback);
}
