package com.east.architect_zenghui.architect11_Designmode4_decorator.simple1_normal;

import android.util.Log;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class TeacherEat implements Eat {
    @Override
    public void eat() {
        Log.e("TAG","加个两个菜");
        Log.e("TAG","吃饭吃菜");
        Log.e("TAG","吃完了撤退");
    }
}
