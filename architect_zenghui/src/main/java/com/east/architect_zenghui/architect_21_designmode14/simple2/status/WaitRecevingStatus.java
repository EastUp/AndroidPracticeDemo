package com.east.architect_zenghui.architect_21_designmode14.simple2.status;

/**
 * Created by hcDarren on 2017/11/4.
 */
// 待收货状态的下的操作
public class WaitRecevingStatus implements OrderOperateStatus {
    @Override
    public void pay() {
        System.out.println("不在状态");
    }

    @Override
    public void deliverGoods() {
        System.out.println("不在状态");
    }
}
