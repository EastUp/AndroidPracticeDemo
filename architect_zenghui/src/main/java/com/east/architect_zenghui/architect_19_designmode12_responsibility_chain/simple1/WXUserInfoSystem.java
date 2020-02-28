package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator.Iterator;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator.WXUserIterator;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  微信用户中心
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WXUserInfoSystem implements Aggregate<UserInfo> {

    private UserInfo[] mUserInfos = new UserInfo[3];

    public WXUserInfoSystem() {
        mUserInfos[0] = new UserInfo("大弟子","240336124","001","男");
        mUserInfos[1] = new UserInfo("AlvinMoon","240336124","002","男");
        mUserInfos[2] = new UserInfo("高岩","240336124","003","男");
    }

    //不再对外暴露内部的实现,只需要返回其需要的元素即可
//    public UserInfo[] getUserInfos() {
//        return mUserInfos;
//    }

    @Override
    public Iterator<UserInfo> iterator() {
        return new WXUserIterator(mUserInfos);
    }
}
