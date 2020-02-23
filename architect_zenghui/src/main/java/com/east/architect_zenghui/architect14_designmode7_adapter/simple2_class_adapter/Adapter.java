package com.east.architect_zenghui.architect14_designmode7_adapter.simple2_class_adapter;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 适配器对象 - 把人民币转成美元(类适配 ,继承)
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Adapter extends RmbAdapter implements UsdTarget {
    public Adapter(float rmb) {
        super(rmb);
    }

    @Override
    public float getUsd() {
        return getRmb()/5.6f;
    }
}
