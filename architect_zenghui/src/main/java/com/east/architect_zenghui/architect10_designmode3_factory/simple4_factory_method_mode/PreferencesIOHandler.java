package com.east.architect_zenghui.architect10_designmode3_factory.simple4_factory_method_mode;

import com.east.architect_zenghui.architect10_designmode3_factory.simple2_preferenceutil.PreferencesUtils;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  SharePreferences 存储
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class PreferencesIOHandler implements IOHandler {

    @Override
    public void saveString(String key, String value) {
        PreferencesUtils.getInstance().saveString(key, value);
    }

    @Override
    public void saveInt(String key, int value) {

    }

    @Override
    public void saveDouble(String key, double value) {

    }

    @Override
    public void saveLong(String key, long value) {

    }

    @Override
    public void saveBoolean(String key, boolean value) {

    }

    @Override
    public void saveObject(String key, Object value) {

    }

    @Override
    public String getString(String key) {
        return PreferencesUtils.getInstance().getString(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return 0;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return false;
    }

    @Override
    public Object getObject(String key) {
        return null;
    }
}
