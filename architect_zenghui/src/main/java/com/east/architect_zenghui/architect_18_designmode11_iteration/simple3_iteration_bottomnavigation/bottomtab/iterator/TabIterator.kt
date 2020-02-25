package com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.iterator

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 迭代器
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface TabIterator<T> {

    fun next():T

    fun hasNext():Boolean
}