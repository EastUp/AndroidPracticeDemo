package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple2;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 单例模式-懒汉式
 *  @author: East
 *  @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Singleton {
    // 只有使用的时候才会去 new 对象 ，可能更加高效
    // 但是有问题? 多线程并发的问题,如果多线程调用还是会存在多个实例
    private static Singleton mInstance;

    private Singleton(){}

    private static Singleton getInstance(){
        if(mInstance == null){
            mInstance = new Singleton();
        }
        return mInstance;
    }
}
