package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:   用户信息
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class UserInfo {
    public String userName;
    public String userPwd;
    public String userId;
    public String userSex;

    public UserInfo(String userName, String userPwd, String userId, String userSex) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.userId = userId;
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName  +
                ", userPwd='" + userPwd  +
                ", userId='" + userId  +
                ", userSex='" + userSex  +
                '}';
    }
}
