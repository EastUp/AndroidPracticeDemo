package com.east.architect_zenghui.architect15_designmode8_observe.simple3_java_observable.observer;

import com.east.architect_zenghui.architect15_designmode8_observe.simple3_java_observable.observable.WXAdvanceObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信公众号 - 具体订阅用户（EastRise，高岩）
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WXUser implements Observer {

    private String name;

    public WXUser(String name) {
        this.name = name;
    }


    // 推拉模式 = 既可以拉又可以推
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name + "接收到一篇文章" + arg);

        WXAdvanceObservable wxAdvanceObservable = (WXAdvanceObservable) o;
        System.out.println(name + "主动拉取一篇文章：" + wxAdvanceObservable.getArticle());
    }
}
