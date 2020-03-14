package com.east.architect_zenghui.architect_30_rxjava2.rxjava


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2020/3/8
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface ObservableSource<T>{
    fun subscribe(observer: Observer<in T>)
}