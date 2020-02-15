package com.east.architect_zenghui.architect8_designmode1_singleton.kotlin.simple2.sync

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 单例设计模式 - 懒汉式
 * @author: East
 * @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Singleton1 private constructor(){

    companion object{
        // 只有使用的时候才会去 new 对象 ，可能更加高效
        private var mInstance: Singleton1? = null

        //解决了多线程的问题,但是又存在效率低的问题,每次获取都要经过同步锁的判断
        @Synchronized
        fun getInstance() :Singleton1{
            if (mInstance == null) {
                mInstance =
                    Singleton1()
            }
            return mInstance!!
        }
    }
}