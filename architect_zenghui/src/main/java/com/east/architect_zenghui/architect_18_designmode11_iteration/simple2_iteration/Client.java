package com.east.architect_zenghui.architect_18_designmode11_iteration.simple2_iteration;

import com.east.architect_zenghui.architect_18_designmode11_iteration.simple2_iteration.iterator.Iterator;

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
        WXUserInfoSystem wxUserSystem = new WXUserInfoSystem();
        QQUserInfoSystem qqUserSystem = new QQUserInfoSystem();

        UserInfo loginUserInfo = queryUserInfo("高岩","240",wxUserSystem.iterator());

        // 这个地方做了 if else 判断
        if(loginUserInfo == null) {
            // 从QQ的系统里面去获取
            loginUserInfo = queryUserInfo("高岩", "240", qqUserSystem.iterator());
        }

        // 很有可能会接第三个系统，或者说还有第四个系统

        if(loginUserInfo == null){
            // 登录失败，用户名和密码错误
        }

        // 再打一个比如，四个人做开发 ，有一哥们要去显示列表 ，
        // 但是数据已经被存入到数据库，而且存在三个库里面

    }


    /**
     * 查询用户信息
     * @param userName
     * @param userPwd
     * @return
     */
    private static UserInfo queryUserInfo(String userName, String userPwd, Iterator<UserInfo> iterator) {

        while (iterator.hasNext()){
            UserInfo userInfo = iterator.next();
            if(userInfo.userName.equals(userName)&&userInfo.userPwd.equals(userPwd)){
                return userInfo;
            }
        }
        return null;
    }


}
