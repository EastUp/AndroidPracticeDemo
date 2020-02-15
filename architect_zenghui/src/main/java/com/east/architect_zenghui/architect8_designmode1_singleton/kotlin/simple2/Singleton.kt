package com.east.architect_zenghui.architect8_designmode1_singleton.kotlin.simple2

import com.east.architect_zenghui.architect8_designmode1_singleton.kotlin.simple2.sync.Singleton1

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 单例模式-懒汉式
 * @author: East
 * @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Singleton private constructor() {

    companion object {
        // 只有使用的时候才会去 new 对象 ，可能更加高效
        // 但是有问题? 多线程并发的问题,如果多线程调用还是会存在多个实例
        private var mInstance: Singleton? = null

        fun getInstance(): Singleton {
            if (mInstance == null) {
                mInstance =
                    Singleton()
            }
            return mInstance!!
        }
    }
}