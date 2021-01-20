package com.dn_alan.skin_core2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  在每个Activity.setContentView之前设置view创建的工厂
 *  @author: jamin
 *  @date: 2021/1/20 15:51
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);


        //添加自定义创建View 工厂
        SkinLayoutFactory factory = new SkinLayoutFactory();
        layoutInflater.setFactory2(factory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
