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
public class StudentEat implements Eat {

    private PersonEat mPerson;

    public StudentEat(PersonEat person) {
        this.mPerson = person;
    }

    @Override
    public void eat() {
        Log.e("TAG","加个菜");
        mPerson.eat();
        Log.e("TAG","吃完了还盘子");
    }
}
