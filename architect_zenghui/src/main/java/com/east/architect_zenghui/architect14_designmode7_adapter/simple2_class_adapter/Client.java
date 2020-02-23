package com.east.architect_zenghui.architect14_designmode7_adapter.simple2_class_adapter;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Client {
    public static void main(String[] args) {
        // 第一个版本只是显示人民币
        RmbAdapter adapter = new RmbAdapter(1000);
        float rmb = adapter.getRmb();
        System.out.println("人民币:"+rmb);

        // 第二个版本要去兼容美元 ，这么写可以设计模式只是一种思想
        Adapter usdTarget = new Adapter(1000);
        rmb = usdTarget.getRmb();
        System.out.println("人民币:"+rmb);
        float usd = usdTarget.getUsd();
        System.out.println("美元:"+usd);
    }
}
