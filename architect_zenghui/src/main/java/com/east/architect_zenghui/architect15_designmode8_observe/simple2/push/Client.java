package com.east.architect_zenghui.architect15_designmode8_observe.simple2.push;

import com.east.architect_zenghui.architect15_designmode8_observe.simple2.push.observable.PushWXAdvanceObservable;
import com.east.architect_zenghui.architect15_designmode8_observe.simple2.push.observer.PushWXUser;

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
        PushWXAdvanceObservable observable = new PushWXAdvanceObservable();

        // 微信公众号 - 具体的观察者 - Darren，高岩
        PushWXUser eastRise = new PushWXUser("EastRise");
        PushWXUser gaoyan = new PushWXUser("高岩");

        // 微信公众号 - 用户订阅公众号
        observable.registe(eastRise);
        observable.registe(gaoyan);

        // 微信公众号 - 推送文章
        observable.setArticle("《代理设计模式 - 实现 Retrofit 的 create》");

        // 微信公众号 - 用户取消订阅公众号
        observable.unregiste(gaoyan);

        // 微信公众号 - 推送文章
        observable.setArticle("第三方框架 - EventBus 源码分析和手写");
    }
}
