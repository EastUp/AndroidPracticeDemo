package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple4;

import java.util.HashMap;
import java.util.Map;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 单例设计模式 - 容器管理 系统的服务就是用的这种
 *  @author: East
 *  @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Singleton {
    private static Map<String,Singleton> mSingleMap = new HashMap<>();
    private static final String ACTIVITY_MANAGER = "activity_manager";
    static {
        mSingleMap.put(ACTIVITY_MANAGER,new Singleton());
    }

    private Singleton(){}

    public static Singleton getInstance(){
        return mSingleMap.get(ACTIVITY_MANAGER);
    }
}
