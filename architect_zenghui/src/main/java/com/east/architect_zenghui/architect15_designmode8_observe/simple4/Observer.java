package com.east.architect_zenghui.architect15_designmode8_observe.simple4;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 观察者
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface Observer<T> {
    /**
     * 被观察者推送过来的数据交由观察者更新
     * @param observable
     * @param data
     */
    public void update(Observable observable,T data);
}
