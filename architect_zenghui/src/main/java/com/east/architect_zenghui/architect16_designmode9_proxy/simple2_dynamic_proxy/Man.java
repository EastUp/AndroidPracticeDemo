package com.east.architect_zenghui.architect16_designmode9_proxy.simple2_dynamic_proxy;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 被代理对象 - 客户
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Man implements IBank {

    private String name;

    public Man(String name) {
        this.name = name;
    }

    /**
     * 自己的一些操作
     */
    @Override
    public void applyBank() {
        System.out.println(name +  "申请办卡");
    }

    @Override
    public void lostBank() {
        System.out.println( name + "申请挂失");
    }

    @Override
    public void extraBank() {
        System.out.println( name + "额外业务");
    }
}
