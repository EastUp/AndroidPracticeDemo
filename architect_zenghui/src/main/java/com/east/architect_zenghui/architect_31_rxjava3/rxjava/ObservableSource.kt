package com.east.architect_zenghui.architect_31_rxjava3.rxjava


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