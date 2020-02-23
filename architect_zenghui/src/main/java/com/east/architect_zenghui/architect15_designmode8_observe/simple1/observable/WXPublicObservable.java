package com.east.architect_zenghui.architect15_designmode8_observe.simple1.observable;

import com.east.architect_zenghui.architect15_designmode8_observe.simple1.observer.IWXUser;

import java.util.ArrayList;
import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信公众号 微信公众号： 多个人去订阅我们的公众号
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public  class WXPublicObservable {

    private List<IWXUser> mUsers = new ArrayList<>();

    /**
     * 订阅
     * @param user
     */
    public void registe(IWXUser user){
        mUsers.add(user);
    }

    /**
     * 取消订阅
     * @param user
     */
    public void unregiste(IWXUser user){
        mUsers.remove(user);
    }


    /**
     * 更新了文章通知到观察者
     * @param article
     */
    public void update(String article){
        for (IWXUser user : mUsers) {
            user.push(article);
        }
    }
}
