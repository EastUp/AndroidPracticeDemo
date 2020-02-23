package com.east.architect_zenghui.architect17_designmode10_prototype.simple1;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 装箱子的卡车
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class TruckCar {

    private IBox iBox;

    public TruckCar(IBox iBox) {
        this.iBox = iBox;
    }

    public IBox getiBox() {
        return iBox;
    }

    public void setiBox(IBox iBox) {
        this.iBox = iBox;
    }
}
