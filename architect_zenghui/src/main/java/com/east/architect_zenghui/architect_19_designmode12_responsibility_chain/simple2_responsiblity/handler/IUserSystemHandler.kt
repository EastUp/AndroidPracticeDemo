package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple2_responsiblity.handler


/**
 * |---------------------------------------------------------------------------------------------------------------|
 * @description:  责任链设计模式 - 抽象处理者接口
 * @author: East
 * @date: 2020/2/27 12:42 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface IUserSystemHandler<T: IUserSystemHandler<T>> {
    fun nextHandler(systemHandler: T)
}