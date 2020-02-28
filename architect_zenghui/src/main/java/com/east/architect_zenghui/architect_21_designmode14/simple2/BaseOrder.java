package com.east.architect_zenghui.architect_21_designmode14.simple2;


import com.east.architect_zenghui.architect_21_designmode14.simple2.status.OrderOperateStatus;

/**
 * Created by hcDarren on 2017/11/4.
 */

public class BaseOrder {
    protected OrderOperateStatus mStatus;

    public void setStatus(OrderOperateStatus status){
        this.mStatus = status;
    }
}
