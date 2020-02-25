package com.east.architect_zenghui.architect_18_designmode11_iteration.simple1;

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
public class QQUserInfoSystem {

    private List<UserInfo> mUserInfos = new ArrayList<>();

    public QQUserInfoSystem() {
        mUserInfos.add(new UserInfo("Darren","240336124","001","男"));
        mUserInfos.add(new UserInfo("夕决","240336124","002","男"));
        mUserInfos.add(new UserInfo("yjy239","240336124","003","男"));
    }

    public List<UserInfo> getUserInfos() {
        return mUserInfos;
    }
}
