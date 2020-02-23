package com.east.architect_zenghui.architect15_designmode8_observe.simple2.push.observer;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信用户
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IPushWXUser {

    /**
     * 接收到推送的文章
     * @param article
     */
    void push(String article);
}
