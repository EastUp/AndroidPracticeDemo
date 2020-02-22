package com.east.architect_zenghui.architect14_designmode7_adapter.simple1;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 代表第一个版本的人民币
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class RmbAdapter {
    private float mRmb;

    public RmbAdapter(float rmb) {
        this.mRmb = rmb;
    }

    /**
     * 获取人民币
     * @return
     */
    public float getRmb(){
        return mRmb;
    }

    public float getUsd(){
        return mRmb/5.6f;
    }
}
