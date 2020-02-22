package com.east.architect_zenghui.architect14_designmode7_adapter.simple3;

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
        RmbAdapter adapter = new RmbAdapter(1000);
        Adapter adapter1 = new Adapter(adapter);
        float usd = adapter1.getUsd();
        System.out.println("美元:"+usd);
    }
}
