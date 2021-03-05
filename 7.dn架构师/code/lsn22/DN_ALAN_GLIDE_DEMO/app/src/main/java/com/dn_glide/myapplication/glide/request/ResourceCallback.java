package com.dn_glide.myapplication.glide.request;


import com.dn_glide.myapplication.glide.cache.recycle.Resource;

public interface ResourceCallback {
    void onResourceReady(Resource reference);
}
