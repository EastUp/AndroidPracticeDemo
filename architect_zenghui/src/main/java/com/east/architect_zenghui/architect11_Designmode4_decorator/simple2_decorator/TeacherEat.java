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
public class TeacherEat implements Eat {

    private PersonEat mPerson;

    public TeacherEat(PersonEat person){
        this.mPerson = person;
    }
    @Override
    public void eat() {
        Log.e("TAG","加个两个菜");
        mPerson.eat();
        Log.e("TAG","吃完了撤退");
    }
}
