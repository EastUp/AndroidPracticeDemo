package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple2_responsiblity;

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
        NYUserInfoSystem nyUserSystem = new NYUserInfoSystem();

        wxUserSystem.nextHandler(qqUserSystem);
        qqUserSystem.nextHandler(nyUserSystem);

        UserInfo userInfo = wxUserSystem.queryUserInfo("夕决","240336124");
        System.out.println(userInfo);

        // 发现代码有问题，想想有没有设计模式可以解决

        // 再打一个比如，四个人做开发 ，有一哥们要去显示列表 ，
        // 但是数据已经被存入到数据库，而且存在三个库里面

    }



}
