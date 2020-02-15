package com.east.architect_zenghui.architect8_designmode1_singleton.kotlin.simple3

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 单例模式-静态内部类 （比较常用）
 * @author: East
 * @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Singleton private constructor() {

    private object SingletonHolder {
        @Volatile
        var mSingleton = Singleton()
    }

    companion object {

        private val mInstance: Singleton? = null

        fun getInstance() = SingletonHolder.mSingleton

    }
}