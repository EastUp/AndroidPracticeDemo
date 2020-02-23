package com.east.architect_zenghui.architect17_designmode10_prototype.simple1;

import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 测试
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Client {
    public static void main(String[] args) {
        CarPartBox box = new CarPartBox();
        box.setNumber(500);
        box.setName("尾灯灯罩");
        box.setCarBrand("奥迪");

        List<TruckCar> truckCars = SplitService.split(box);

        for (TruckCar truckCar : truckCars) {
            System.out.println("数量:"+truckCar.getiBox().getNumber());
        }
    }
}
