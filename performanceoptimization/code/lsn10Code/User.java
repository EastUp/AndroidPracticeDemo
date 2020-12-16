package com.example.administrator.lsn_8_demo;

import android.support.annotation.Keep;

// 混淆测试的
public class User {
    String name;
    Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void method() {
        System.out.println(1/0);
    }
}
