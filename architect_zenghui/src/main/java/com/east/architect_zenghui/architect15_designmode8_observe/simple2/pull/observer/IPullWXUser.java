package com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observer;

import com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observable.PullWXPublicObservable;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信用户
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IPullWXUser {

    /**
     * 观察者主动查询
     */
    void pull(PullWXPublicObservable wxPublicObservable);
}
