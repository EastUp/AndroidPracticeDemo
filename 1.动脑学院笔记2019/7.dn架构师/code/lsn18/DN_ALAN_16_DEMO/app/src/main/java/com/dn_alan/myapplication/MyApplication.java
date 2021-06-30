package com.dn_alan.myapplication;

import android.app.Application;

import com.dn_alan.router_core.DNRouter;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            DNRouter.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
