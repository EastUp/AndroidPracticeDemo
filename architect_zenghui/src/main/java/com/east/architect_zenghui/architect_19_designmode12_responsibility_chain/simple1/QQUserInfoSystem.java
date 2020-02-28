package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator.Iterator;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator.QQUserIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  QQ用户中心
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class QQUserInfoSystem implements Aggregate<UserInfo> {

    private List<UserInfo> mUserInfos = new ArrayList<>();

    public QQUserInfoSystem() {
        mUserInfos.add(new UserInfo("Darren","240336124","001","男"));
        mUserInfos.add(new UserInfo("夕决","240336124","002","男"));
        mUserInfos.add(new UserInfo("yjy239","240336124","003","男"));
    }

    //不再对外暴露内部的实现,只需要返回其需要的元素即可
//    public List<UserInfo> getUserInfos() {
//        return mUserInfos;
//    }

    @Override
    public Iterator<UserInfo> iterator() {
        return new QQUserIterator(mUserInfos);
    }
}
