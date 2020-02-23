package com.east.architect_zenghui.architect15_designmode8_observe.simple4;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  保险用户
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Member {
    private String name;
    private String age;

    public Member(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
