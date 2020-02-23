package com.east.architect_zenghui.architect17_designmode10_prototype.simple3;

import java.util.ArrayList;
import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 拆分
 * @author: East
 * @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class SplitService {

    /**
     * 把箱子进行一下拆分
     * 这么写的问题，就是这个代码有点多，而且不便于扩展，比如我要新增一种货箱（尾盖）
     *
     * @param iBox
     * @return
     */
    public static List<TruckCar> split(IBox iBox) {
        List<TruckCar> list = new ArrayList<>();

        while (iBox.getNumber() > 200) {

            IBox copyBox = null;
            try {
                copyBox = iBox.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            copyBox.setNumber(200);

            TruckCar car = new TruckCar(copyBox);
            list.add(car);

            iBox.setNumber(iBox.getNumber() - 200);
        }

        list.add(new TruckCar(iBox));
        return list;
    }
}
