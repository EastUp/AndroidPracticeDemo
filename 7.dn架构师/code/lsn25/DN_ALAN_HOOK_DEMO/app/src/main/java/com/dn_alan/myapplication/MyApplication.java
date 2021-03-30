package com.dn_alan.myapplication;

import android.app.Application;

import com.dn_alan.myapplication.os.DnAMSCheckEngine;
import com.dn_alan.myapplication.os.DnActivityThread;

import java.lang.reflect.InvocationTargetException;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            DnAMSCheckEngine.mHookAMS(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            DnActivityThread dnActivityThread = new DnActivityThread(this);
            dnActivityThread.mActivityThreadmHAction(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
