package com.east.architect_zenghui.architect11_Designmode4_decorator.simple2_decorator;

import android.util.Log;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class PersonEat implements Eat {
    @Override
    public void eat() {
        Log.e("TAG","吃饭吃菜");
    }
}
