package com.dn_alan.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.dn_alan.pluginstand.PayInterfaceActivity;

/**
 *  占坑的Activity
 */
public class ProxyActivity extends Activity {
    //需要加载插件的全类名
    private String className;
    PayInterfaceActivity payInterfaceActivity;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");

        try {
            //TaoMainActivity
            Class<?> aClass = getClassLoader().loadClass(className);
            payInterfaceActivity  = (PayInterfaceActivity) aClass.newInstance();
            payInterfaceActivity.attach(this);

            //如果需要参数，可以使用Bundle
            Bundle bundle = new Bundle();
            payInterfaceActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className);
        super.startActivity(intent1);
    }

    //重写加载类
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    //重写加载资源
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        payInterfaceActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        payInterfaceActivity.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        payInterfaceActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payInterfaceActivity.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        payInterfaceActivity.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return payInterfaceActivity.onTouchEvent(event);
    }
}
