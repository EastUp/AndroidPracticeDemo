package com.east.architect_zenghui.architect17_designmode10_prototype.simple1;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 具体的出货的物品 - 汽车的零件
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class CarPartBox implements IBox {

    private int number;

    private String name;

    private String carBrand;//汽车品牌

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
}
