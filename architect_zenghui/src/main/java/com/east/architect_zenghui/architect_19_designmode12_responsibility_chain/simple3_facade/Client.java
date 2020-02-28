package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade;


import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.facade.UserSystemFacade;

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

        // 门面设计模式
        UserSystemFacade userSystemFacade = new UserSystemFacade();

        UserInfo userInfo = userSystemFacade.queryUserInfo("夕决","240336124");
        System.out.println(userInfo);

        // 发现代码有问题，想想有没有设计模式可以解决

        // 再打一个比如，四个人做开发 ，有一哥们要去显示列表 ，
        // 但是数据已经被存入到数据库，而且存在三个库里面

    }



}
