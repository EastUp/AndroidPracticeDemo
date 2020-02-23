package com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observable;

import com.east.architect_zenghui.architect15_designmode8_observe.simple2.pull.observer.IPullWXUser;

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
public  class PullWXPublicObservable {

    private List<IPullWXUser> mUsers = new ArrayList<>();

    /**
     * 订阅
     * @param user
     */
    public void registe(IPullWXUser user){
        mUsers.add(user);
    }

    /**
     * 取消订阅
     * @param user
     */
    public void unregiste(IPullWXUser user){
        mUsers.remove(user);
    }


    /**
     * 更新了文章通知到观察者
     * @param article
     */
    public void update(String article){
        for (IPullWXUser user : mUsers) {
            user.pull(this);
        }
    }
}
