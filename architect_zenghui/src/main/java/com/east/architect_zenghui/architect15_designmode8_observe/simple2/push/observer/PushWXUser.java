package com.east.architect_zenghui.architect15_designmode8_observe.simple2.push.observer;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信公众号 - 具体订阅用户（EastRise，高岩）
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class PushWXUser implements IPushWXUser {

    private String name;

    public PushWXUser(String name) {
        this.name = name;
    }

    @Override
    public void push(String article) {
        System.out.println(name + "接收到一篇文章" +article);
    }
}
