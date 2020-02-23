package com.east.architect_zenghui.architect16_designmode9_proxy.simple2_dynamic_proxy;

import java.lang.reflect.Proxy;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Client {
    public static void main(String[] args) {
        IBank man = new Man("east");

        IBank iBank =
                // 返回的是 IBank 的一个实例对象，这个对象是由 Java 给我们创建的 ,调用的是 jni
                (IBank) Proxy.newProxyInstance(
                IBank.class.getClassLoader(),   //classLoader
                /*new Class<?>[]{IBank.class}*/man.getClass().getInterfaces(),    //目标接口
                new BankInvocationHandler(man)  //InvocationHandler
        );

        // 当调用这个方法的时候会来到 BankInvocationHandler 的 invoke 方法
        iBank.applyBank();
        iBank.lostBank();
        iBank.extraBank();
    }
}
