package com.east.architect_zenghui.architect10_designmode3_factory.simple5_abstract_factory_mode;

import android.util.LruCache;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 运行内存存储
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class MemoryIOHandler implements IOHandler {

    // 存在运行内存里面，原理是什么？其实就是 Map 集合
    private static LruCache<String,Object> mCache = new LruCache<>(10*1024*1024); // 一般占 运行内存的  1/8
    @Override
    public void saveString(String key, String value) {
        mCache.put(key,value);
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
        return (String) mCache.get(key);
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
