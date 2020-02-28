package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.handler.AbsUserSystemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  微信用户中心
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WXUserInfoSystem extends AbsUserSystemHandler {

    private UserInfo[] mUserInfos = new UserInfo[3];

    public WXUserInfoSystem() {
        mUserInfos[0] = new UserInfo("大弟子","240336124","001","男");
        mUserInfos[1] = new UserInfo("AlvinMoon","240336124","002","男");
        mUserInfos[2] = new UserInfo("高岩","240336124","003","男");
    }

    @Nullable
    @Override
    public UserInfo queryUserInfoInOwnCollections(@NotNull String userName, @NotNull String userPwd) {
        for (UserInfo userInfo : mUserInfos) {
            if(userInfo.userName.equals(userName) && userPwd.equals(userPwd)){
                return userInfo;
            }
        }
        return null;
    }
}
