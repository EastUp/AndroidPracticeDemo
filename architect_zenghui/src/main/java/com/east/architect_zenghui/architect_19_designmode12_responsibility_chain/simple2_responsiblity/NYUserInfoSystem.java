package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple2_responsiblity;


import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple2_responsiblity.handler.AbsUserSystemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class NYUserInfoSystem extends AbsUserSystemHandler {

    private List<UserInfo> mUserInfos = new ArrayList<>();

    public NYUserInfoSystem() {
        mUserInfos.add(new UserInfo("Wenld","240336124","001","男"));
        mUserInfos.add(new UserInfo("yuFrank","240336124","002","男"));
        mUserInfos.add(new UserInfo("葡萄我爱吃","240336124","003","男"));
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
