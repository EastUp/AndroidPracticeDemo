package com.east.databinding.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 * 继承Observable  不推荐
 * @description:  BaseObservable提供了notifyChange（）和notifyPropertyChanged（）两个方法，前者会刷新所有的值域，
 *                后者则只更新对应BR的旗帜，该BR的生成通过注释@Bindable生成，可以通过BR notify特定属性关联的视图
 * @author: East
 * @date: 2019-07-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Goods extends BaseObservable {

    //public 修饰符可以直接在成员变量上 加@Bindable 注解
    @Bindable
    public String name;

    private String details;

    //如果是private 则需要在get方法上加@Bindable 注解
    private float price;

    public Goods(String name, String details, float price) {
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public Goods() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
