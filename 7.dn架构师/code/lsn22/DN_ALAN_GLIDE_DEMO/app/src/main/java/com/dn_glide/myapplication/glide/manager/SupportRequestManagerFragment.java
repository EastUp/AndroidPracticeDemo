package com.dn_glide.myapplication.glide.manager;

import android.support.v4.app.Fragment;

import com.dn_glide.myapplication.glide.RequestManager;

public class SupportRequestManagerFragment extends Fragment {

    //生命周期回调管理类
    ActivityFragmentLifecycle lifecycle;

    public SupportRequestManagerFragment() {
        lifecycle = new ActivityFragmentLifecycle();
    }

    RequestManager requestManager;

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public ActivityFragmentLifecycle getGlideLifecycle() {
        return lifecycle;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }
}
