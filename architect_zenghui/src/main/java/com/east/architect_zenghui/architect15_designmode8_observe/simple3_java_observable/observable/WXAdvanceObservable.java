package com.east.architect_zenghui.architect15_designmode8_observe.simple3_java_observable.observable;


import java.util.Observable;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  微信公众号 - Android进阶之旅公众号
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WXAdvanceObservable extends Observable {

    private String article;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
        // 通知更新,推送给微信用户
        setChanged();
        notifyObservers(article);
    }
}
