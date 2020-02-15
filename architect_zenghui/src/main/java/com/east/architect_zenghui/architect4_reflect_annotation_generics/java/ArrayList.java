package com.east.architect_zenghui.architect4_reflect_annotation_generics.java;

/**
 * Created by hcDarren on 2017/9/4.
 * Object
 */
public class ArrayList {
    private Object[] items = new Object[10];

    public <T> void add(T t){
        items[0] = t;
    }
}
