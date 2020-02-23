package com.east.architect_zenghui.architect17_designmode10_prototype.simple2;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 出货的箱子接口
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IBox {

    void setNumber(int number);// 设置箱子的数量

    int getNumber();// 有多少货

    // 新增一个方法 - 拷贝
    IBox copy();
}
