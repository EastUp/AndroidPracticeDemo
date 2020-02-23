package com.east.architect_zenghui.architect14_designmode7_adapter.simple3_object_adapter;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 适配器对象 - 把人民币转成美元(对象适配 ,把对象传递了进来)
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Adapter implements UsdTarget {

    private RmbAdapter mRmbAdapter;

    public Adapter(RmbAdapter rmbAdapter) {
        this.mRmbAdapter = rmbAdapter;
    }

    @Override
    public float getUsd() {
        return mRmbAdapter.getRmb()/5.6f;
    }
}
