package com.east.architect_zenghui.architect_36_retrofit_optimize.simple5;

/**
 * Created by hcDarren on 2017/8/26.
 */

public class SPHttpCache {
    public void saveCache(String finalUrl,String resultJson){
        PreferencesUtil.getInstance().saveParam(finalUrl,resultJson);
    }

    public String getCache(String finalUrl){
        return (String) PreferencesUtil.getInstance().getObject(finalUrl);
    }
}
