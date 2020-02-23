package com.east.architect_zenghui.architect15_designmode8_observe.simple3_java_observable;

import com.east.architect_zenghui.architect15_designmode8_observe.simple3_java_observable.observable.WXAdvanceObservable;
import com.east.architect_zenghui.architect15_designmode8_observe.simple3_java_observable.observer.WXUser;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Client {
    public static void main(String[] args) {
        // 微信公众号 - 具体的被观察者 - Android进阶之旅
        WXAdvanceObservable observable = new WXAdvanceObservable();

        // 微信公众号 - 具体的观察者 - Darren，高岩
        WXUser eastRise = new WXUser("EastRise");
        WXUser gaoyan = new WXUser("高岩");

        // 微信公众号 - 用户订阅公众号
        observable.addObserver(eastRise);
        observable.addObserver(gaoyan);

        // 微信公众号 - 推送文章
        observable.setArticle("《代理设计模式 - 实现 Retrofit 的 create》");

        // 微信公众号 - 用户取消订阅公众号
        observable.deleteObserver(gaoyan);

        // 微信公众号 - 推送文章
        observable.setArticle("第三方框架 - EventBus 源码分析和手写");
    }
}
