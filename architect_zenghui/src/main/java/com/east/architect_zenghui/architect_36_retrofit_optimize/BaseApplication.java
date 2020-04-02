package com.east.architect_zenghui.architect_36_retrofit_optimize;

import android.app.Application;

import com.east.architect_zenghui.BuildConfig;
import com.east.architect_zenghui.architect_36_retrofit_optimize.simple5.PreferencesUtil;
import com.east.architect_zenghui.architect_36_retrofit_optimize.simple6.RetrofitRequest;
import com.east.architect_zenghui.architect_36_retrofit_optimize.simple7.EngineConfig;
import com.east.architect_zenghui.architect_36_retrofit_optimize.simple7.HttpUtils;

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

        // 配置上层 配置
        EngineConfig config = new EngineConfig.Builder()
                .engineRequest(new RetrofitRequest())
                // 添加一个解析工厂 Gson Xml
                .converter(new GsonConvert())
                // 添加默认参数 platform = android
                .builder();
        HttpUtils.initConfig(config);
    }
}
