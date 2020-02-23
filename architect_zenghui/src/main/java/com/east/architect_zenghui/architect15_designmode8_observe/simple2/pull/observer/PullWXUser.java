package com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observer;

import com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observable.PullWXAdvanceObservable;
import com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observable.PullWXPublicObservable;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信公众号 - 具体订阅用户（EastRise，高岩）
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class PullWXUser implements IPullWXUser {

    private String name;

    public PullWXUser(String name) {
        this.name = name;
    }

    @Override
    public void pull(PullWXPublicObservable wxPublicObservable) {
        PullWXAdvanceObservable pullWXAdvanceObservable = (PullWXAdvanceObservable) wxPublicObservable;
        System.out.println(name + " 主动拉取一篇文章 " + pullWXAdvanceObservable.getArticle());
    }
}
