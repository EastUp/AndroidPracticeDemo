package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple2.sync;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 单例设计模式 - 懒汉式
 *  @author: East
 *  @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Singleton3 {
    // 只有使用的时候才会去 new 对象 ，更加高效

    // 加上 volatile 的用处是什么？
    // 1.防止重排序
    //      原本(开辟一块内存空间-->初始化变量-->给变量赋值(指向内存地址))
    //      可能(开辟一块内存空间-->给变量赋值(指向内存地址)-->初始化变量)
    // 2.线程可见性:某一个线程改了公用对象（变量），短时间内另一个线程可能是不可见的，因为每一个线程都有自己的缓存区（线程工作区）
    private volatile static Singleton3 mInstance;

    private Singleton3() {
    }

    // 即保证线程的安全同是效率也是比较高的
    private static Singleton3 getInstance(){
        if(mInstance == null){  //如果不为null则不会进入同步锁,所以效率要高些
            synchronized (Singleton3.class){
                if(mInstance == null){
                    mInstance = new Singleton3();
                }
            }
        }
        return mInstance;
    }
}
