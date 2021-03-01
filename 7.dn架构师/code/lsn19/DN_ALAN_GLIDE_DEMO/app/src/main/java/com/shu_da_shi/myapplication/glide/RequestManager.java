package com.shu_da_shi.myapplication.glide;


import android.content.Context;

import com.shu_da_shi.myapplication.glide.manager.Lifecycle;
import com.shu_da_shi.myapplication.glide.manager.LifecycleListener;
import com.shu_da_shi.myapplication.glide.manager.RequestTracker;
import com.shu_da_shi.myapplication.glide.request.Request;

import java.io.File;

public class RequestManager implements LifecycleListener, ModelTypes<RequestBuilder> {

    protected final Context context;
    final Lifecycle lifecycle;
    private final RequestTracker requestTracker;

    public RequestManager(Lifecycle lifecycle, Context context) {
        this.lifecycle = lifecycle;
        this.requestTracker = new RequestTracker();
        this.context = context;
        lifecycle.addListener(this);
    }


    @Override
    public void onStart() {
        requestTracker.resumeRequests();
    }

    @Override
    public void onStop() {
        requestTracker.pauseRequests();
    }

    @Override
    public void onDestroy() {
        requestTracker.clearRequests();
        lifecycle.removeListener(this);
    }


    public RequestBuilder asBitmap() {
        return new RequestBuilder(this, context);
    }


    void track(Request request) {
        requestTracker.runRequest(request);
    }


    @Override
    public RequestBuilder load(String string) {
        return asBitmap().load(string);
    }

    @Override
    public RequestBuilder load(File file) {
        return asBitmap().load(file);
    }

}
