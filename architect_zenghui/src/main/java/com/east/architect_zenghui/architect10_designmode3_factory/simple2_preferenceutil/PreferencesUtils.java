package com.east.architect_zenghui.architect10_designmode3_factory.simple2_preferenceutil;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:   SharePreference工具类
 *  @author: East
 *  @date: 2020-02-17
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class PreferencesUtils {

    private volatile static PreferencesUtils mInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferencesUtils(){}

    public static PreferencesUtils getInstance(){
        if(mInstance == null){
            synchronized (PreferencesUtils.class){
                if(mInstance == null){
                    mInstance = new PreferencesUtils();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        mPreferences = context.getApplicationContext().getSharedPreferences("cache", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public PreferencesUtils saveString(String key,String value){
        mEditor.putString(key, value);
        return this;
    }

    public String getString(String key){
        return mPreferences.getString(key,"");
    }

    public void commit(){
        mEditor.commit();
    }


}
