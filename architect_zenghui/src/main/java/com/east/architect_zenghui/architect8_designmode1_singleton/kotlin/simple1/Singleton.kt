package com.east.architect_zenghui.architect8_designmode1_singleton.kotlin.simple1

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 单例模式-饿汉式
 * @author: East
 * @date: 2020-02-15
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Singleton private constructor() {

    companion object {
        //随着类的加载已经new出来了的对象
        private val instance =
            Singleton()
    }
}