package com.east.architect_zenghui.architect_18_designmode11_iteration.simple2_iteration.iterator;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 迭代器的接口
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface Iterator<T> {

    /**
     * 获取下一个
     */
    T next();

    /**
     * 是否还有下一个
     */
    Boolean hasNext();
}
