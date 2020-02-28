package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.UserInfo;

import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: QQ的具体的迭代器
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class QQUserIterator implements Iterator<UserInfo> {

    private List<UserInfo> mUserInfos;

    private int index;

    public QQUserIterator(List<UserInfo> mUserInfos) {
        this.mUserInfos = mUserInfos;
    }

    @Override
    public UserInfo next() {
        return mUserInfos.get(index++);
    }

    @Override
    public Boolean hasNext() {
        return index < mUserInfos.size();
    }
}
