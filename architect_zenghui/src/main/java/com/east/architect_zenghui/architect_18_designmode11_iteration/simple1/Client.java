package com.east.architect_zenghui.architect_18_designmode11_iteration.simple1;

import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 测试
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Client {
    public static void main(String[] args) {

        // 根据用户名和密码去查询用户信息，
        // 如果没有查询到那么代表登录失败，如果查询到了代表登录成功

        UserInfo loginUserInfo = queryWXUserInfo("高岩","240");

        if(loginUserInfo == null){
            // 从QQ的系统里面去获取
            loginUserInfo = queryQQUserInfo("高岩","240");
        }

        // 很有可能会接第三个系统，或者说还有第四个系统


        if(loginUserInfo == null){
            // 登录失败，用户名和密码错误
        }

    }


    /**
     * 从QQ中查询用户信息
     * @param userName
     * @param userPwd
     * @return
     */
    private static UserInfo queryQQUserInfo(String userName, String userPwd) {
        QQUserInfoSystem qqUserSystem = new QQUserInfoSystem();
        List<UserInfo> userInfos =  qqUserSystem.getUserInfos();
        for (UserInfo userInfo : userInfos) {
            if(userInfo.userName.equals(userName)&&userInfo.userPwd.equals(userPwd)){
                return userInfo;
            }
        }
        return null;
    }

    /**
     * 从微信中查询用户信息
     * @param userName
     * @param userPwd
     * @return
     */
    private static UserInfo queryWXUserInfo(String userName, String userPwd) {
        WXUserInfoSystem wxUserSystem = new WXUserInfoSystem();
        UserInfo[] userInfos =  wxUserSystem.getUserInfos();
        for (UserInfo userInfo : userInfos) {
            if(userInfo.userName.equals(userName)&&userInfo.userPwd.equals(userPwd)){
                return userInfo;
            }
        }
        return null;
    }

}
