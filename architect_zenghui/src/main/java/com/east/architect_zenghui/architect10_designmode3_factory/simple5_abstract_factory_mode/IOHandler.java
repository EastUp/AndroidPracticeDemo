package com.east.architect_zenghui.architect10_designmode3_factory.simple5_abstract_factory_mode;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 数据存储的一些规范
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IOHandler {

    /************ save data start **************/

    void saveString(String key, String value);
    void saveInt(String key, int value);
    void saveDouble(String key, double value);
    void saveLong(String key, long value);
    void saveBoolean(String key, boolean value);
    void saveObject(String key, Object value);

    /************ save data end **************/


    /************ get ata start **************/

    String getString(String key);
    int getInt(String key, int defaultValue);
    double getDouble(String key, double defaultValue);
    long getLong(String key, long defaultValue);
    boolean getBoolean(String key, boolean defaultValue);
    Object getObject(String key);

    /************ get data end **************/
}
