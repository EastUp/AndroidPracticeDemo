package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.UserInfo;

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
