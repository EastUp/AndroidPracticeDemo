package com.east.architect_zenghui.change_network_engine;

import android.app.Application;

import com.east.architect_zenghui.BuildConfig;
import com.east.architect_zenghui.change_network_engine.simple5.HttpUtils;
import com.east.architect_zenghui.change_network_engine.simple5.OKHttpRequest;
import com.east.architect_zenghui.change_network_engine.simple5.PreferencesUtil;
import org.xutils.x;

/**
 * description:
 * author: Darren on 2017/8/21 15:05
 * email: 240336124@qq.com
 * version: 1.0
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtil.getInstance().init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        HttpUtils.initHttpRequest(new OKHttpRequest());
    }
}
