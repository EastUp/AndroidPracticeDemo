package com.east.architect_zenghui.architect_21_designmode14.simple2;


import com.east.architect_zenghui.architect_21_designmode14.simple2.status.ObligationStatus;
import com.east.architect_zenghui.architect_21_designmode14.simple2.status.OrderOperateStatus;
import com.east.architect_zenghui.architect_21_designmode14.simple2.status.PaidStatus;
import com.east.architect_zenghui.architect_21_designmode14.simple2.status.WaitRecevingStatus;

/**
 * Created by hcDarren on 2017/11/4.
 */

public class Order extends BaseOrder implements OrderOperateStatus {

    public Order(){
        // 默认的状态
        mStatus = new ObligationStatus();
    }

    @Override
    public void pay() {
        mStatus.pay();
        // 状态设置已支付的状态
        setStatus(new PaidStatus());
    }

    @Override
    public void deliverGoods() {
        mStatus.deliverGoods();
        // 代发货的状态
        setStatus(new WaitRecevingStatus());
    }
}
