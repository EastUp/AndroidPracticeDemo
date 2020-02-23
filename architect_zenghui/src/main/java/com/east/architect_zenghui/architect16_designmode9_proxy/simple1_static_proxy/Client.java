package com.east.architect_zenghui.architect16_designmode9_proxy.simple1_static_proxy;

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
        Man man = new Man("east");
        BankWorker bankWorker = new BankWorker(man);

        bankWorker.applyBank();
        bankWorker.lostBank();
    }
}
