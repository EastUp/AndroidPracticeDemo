package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple5;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 单例设计模式 - 自成一派
 *  @author: East
 *  @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Singleton {
    private static Singleton mInstance;

    static {
        mInstance = new Singleton();
    }

    private Singleton(){}

    public static Singleton getInstance(){
        return mInstance;
    }
}
