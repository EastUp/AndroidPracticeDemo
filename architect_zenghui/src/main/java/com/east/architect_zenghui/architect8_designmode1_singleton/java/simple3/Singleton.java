package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple3;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 单例模式-静态内部类 （比较常用）
 *  @author: East
 *  @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Singleton {
    private static Singleton mInstance;

    private Singleton(){}

    public static Singleton getInstance(){
        return SingletonHolder.mSingleton;
    }

    private static class SingletonHolder{
        private static volatile Singleton mSingleton = new Singleton();
    }
}
