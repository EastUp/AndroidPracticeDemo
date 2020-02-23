package com.east.architect_zenghui.architect17_designmode10_prototype.simple2;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 具体的出货的物品 - 塑料夹子
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class PlasticClampBox implements IBox {

    private int number;

    private String name;

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public IBox copy() {
        PlasticClampBox plasticClampBox = new PlasticClampBox();
        plasticClampBox.name = this.name;
        plasticClampBox.number = this.number;
        return plasticClampBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
