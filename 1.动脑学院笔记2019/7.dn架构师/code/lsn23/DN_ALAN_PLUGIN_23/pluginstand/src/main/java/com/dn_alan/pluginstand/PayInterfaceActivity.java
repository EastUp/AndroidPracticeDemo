package com.dn_alan.pluginstand;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 *  代理插件apk中Activity的生命周期这些。在宿主的坑位Activity中调用用这些方法，也就在调用插件Activity的这些方法
 */
public interface PayInterfaceActivity {
    public void attach(Activity proxyActivity);

    /**
     *生命周期
     */
    public void onCreate(Bundle saveInstanceState);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onSaveInstanceState(Bundle outState);
    public boolean onTouchEvent(MotionEvent event);
    public void onBackPressed();

}
