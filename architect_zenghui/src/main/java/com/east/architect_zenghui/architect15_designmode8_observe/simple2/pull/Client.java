package com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull;

import com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observable.PullWXAdvanceObservable;
import com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observer.PullWXUser;

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
        PullWXAdvanceObservable observable = new PullWXAdvanceObservable();

        // 微信公众号 - 具体的观察者 - Darren，高岩
        PullWXUser eastRise = new PullWXUser("EastRise");
        PullWXUser gaoyan = new PullWXUser("高岩");

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
