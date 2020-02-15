package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple2.sync;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 单例设计模式 - 懒汉式
 *  @author: East
 *  @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Singleton2 {
    // 只有使用的时候才会去 new 对象 ，更加高效
    private static Singleton2 mInstance;

    private Singleton2() {
    }

    // 即保证线程的安全同是效率也是比较高的
    // 这种方式其实还是会有问题？
    private static Singleton2 getInstance(){
        if(mInstance == null){  //如果不为null则不会进入同步锁,所以效率要高些
            synchronized (Singleton2.class){
                if(mInstance == null){
                    mInstance = new Singleton2();
                }
            }
        }
        return mInstance;
    }
}
