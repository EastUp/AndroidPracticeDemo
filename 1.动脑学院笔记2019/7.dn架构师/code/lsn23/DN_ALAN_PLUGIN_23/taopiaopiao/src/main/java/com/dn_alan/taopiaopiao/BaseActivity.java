package com.dn_alan.taopiaopiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dn_alan.pluginstand.PayInterfaceActivity;

public class BaseActivity extends Activity implements PayInterfaceActivity {
    protected Activity that;

    // 所有的 findViewById 和 Context 这些都需要通过坑位的 proxyActivity
    @Override
    public void attach(Activity proxyActivity) {
        this.that = proxyActivity;
    }

    @Override
    public void setContentView(View view) {
        if(that != null){
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if(that != null){
            that.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        Intent m  = new Intent();
        m.putExtra("className",intent.getComponent().getClassName());
        that.startActivity(m);
    }

    @Override
    public View findViewById(int id) {
        return that.findViewById(id);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
