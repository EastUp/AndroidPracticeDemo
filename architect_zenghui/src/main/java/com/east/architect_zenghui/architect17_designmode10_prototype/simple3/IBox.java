package com.east.architect_zenghui.architect17_designmode10_prototype.simple3;

import androidx.annotation.NonNull;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 出货的箱子接口
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public abstract class IBox implements Cloneable{

    abstract void setNumber(int number);// 设置箱子的数量

    abstract int getNumber();// 有多少货

    @NonNull
    @Override
    protected IBox clone() throws CloneNotSupportedException {
        return (IBox) super.clone();
    }
}
