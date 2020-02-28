package com.east.architect_zenghui.architect_21_designmode14.simple3;

/**
 * Created by hcDarren on 2017/11/4.
 */

public class MiddleCoffee extends Coffee{
    public MiddleCoffee(CoffeeAdditives additives) {
        super(additives);
    }

    @Override
    public void makeCoffee() {
        System.out.println("中杯的"+mAdditives+"咖啡");
    }
}
