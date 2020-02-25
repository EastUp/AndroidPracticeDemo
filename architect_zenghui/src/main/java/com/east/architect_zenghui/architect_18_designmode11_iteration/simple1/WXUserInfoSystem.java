package com.east.architect_zenghui.architect_18_designmode11_iteration.simple1;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  微信用户中心
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WXUserInfoSystem {

    private UserInfo[] mUserInfos = new UserInfo[3];

    public WXUserInfoSystem() {
        mUserInfos[0] = new UserInfo("大弟子","240336124","001","男");
        mUserInfos[1] = new UserInfo("AlvinMoon","240336124","002","男");
        mUserInfos[2] = new UserInfo("高岩","240336124","003","男");
    }

    public UserInfo[] getUserInfos() {
        return mUserInfos;
    }
}
