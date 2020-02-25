package com.east.architect_zenghui.architect_18_designmode11_iteration.simple2_iteration.iterator;

import com.east.architect_zenghui.architect_18_designmode11_iteration.simple2_iteration.UserInfo;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 微信的具体的迭代器
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WXUserIterator implements Iterator<UserInfo> {

    private UserInfo[] mUserInfos;

    private int index;

    public WXUserIterator(UserInfo[] mUserInfos) {
        this.mUserInfos = mUserInfos;
    }

    @Override
    public UserInfo next() {
        return mUserInfos[index++];
    }

    @Override
    public Boolean hasNext() {
        return index < mUserInfos.length;
    }
}
