package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.facade;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.NYUserInfoSystem;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.QQUserInfoSystem;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.UserInfo;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.WXUserInfoSystem;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.handler.AbsUserSystemHandler;
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.handler.IUserSystem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 门面设计模式 - 易于使用的高层次
 *  @author: East
 *  @date: 2020/2/27
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class UserSystemFacade implements IUserSystem {

    // 第一应该检查的系统
    private AbsUserSystemHandler mFirstHandler;

    public UserSystemFacade() {
        // 根据用户名和密码去查询用户信息，
        // 如果没有查询到那么代表登录失败，如果查询到了代表登录成功
        mFirstHandler = new WXUserInfoSystem();
        QQUserInfoSystem qqUserSystem = new QQUserInfoSystem();
        NYUserInfoSystem nyUserSystem = new NYUserInfoSystem();

        mFirstHandler.nextHandler(qqUserSystem);
        qqUserSystem.nextHandler(nyUserSystem);
    }

    @Nullable
    @Override
    public UserInfo queryUserInfo(@NotNull String userName, @NotNull String userPwd) {
        return mFirstHandler.queryUserInfo(userName,userPwd);
    }
}
