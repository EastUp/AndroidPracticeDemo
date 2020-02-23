package com.east.architect_zenghui.architect17_designmode10_prototype.simple1;

import java.util.ArrayList;
import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  拆分
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class SplitService {

    /**
     * 把箱子进行一下拆分
     * 这么写的问题，就是这个代码有点多，而且不便于扩展，比如我要新增一种货箱（尾盖）
     * @param iBox
     * @return
     */
    public static List<TruckCar> split(IBox iBox){
        List<TruckCar> list = new ArrayList<>();

        while(iBox.getNumber() > 200){
            // 要进行拆分
            if(iBox instanceof PlasticClampBox){
                // 每辆车的箱子
                PlasticClampBox newBox = new PlasticClampBox();
                PlasticClampBox olderBox = (PlasticClampBox) iBox;
                newBox.setName(olderBox.getName());
                newBox.setNumber(200);

                TruckCar car = new TruckCar(newBox);
                list.add(car);

                olderBox.setNumber(olderBox.getNumber() - 200);

            }else if(iBox instanceof CarPartBox){
                // 每辆车的箱子
                CarPartBox newBox = new CarPartBox();
                CarPartBox olderBox = (CarPartBox) iBox;

                newBox.setName(olderBox.getName());
                newBox.setNumber(200);
                newBox.setCarBrand(olderBox.getCarBrand());

                TruckCar car = new TruckCar(newBox);
                list.add(car);

                olderBox.setNumber(olderBox.getNumber() - 200);
            }
        }

        list.add(new TruckCar(iBox));
        return list;
    }
}
