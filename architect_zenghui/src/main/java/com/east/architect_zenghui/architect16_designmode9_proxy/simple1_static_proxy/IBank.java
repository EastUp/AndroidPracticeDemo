package com.east.architect_zenghui.architect16_designmode9_proxy.simple1_static_proxy;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 银行办理业务 - 目标接口（业务）
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IBank {

    /**
     *  申请办卡
     */
    void applyBank();

    /**
     *  挂失
     */
    void lostBank();
}
